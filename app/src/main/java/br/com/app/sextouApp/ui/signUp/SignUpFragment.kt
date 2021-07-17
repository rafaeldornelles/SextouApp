package br.com.app.sextouApp.ui.signUp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.app.sextouApp.MainActivity
import br.com.app.sextouApp.R
import br.com.app.sextouApp.databinding.FragmentSignUpBinding
import br.com.app.sextouApp.utils.FILE_PROVIDER_AUTHORITY
import br.com.app.sextouApp.utils.REQUEST_IMAGE_CAPTURE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class SignUpFragment : Fragment() {

    private lateinit var signUpBinding: FragmentSignUpBinding
    private lateinit var currentPhotoPath: String
    var storageRef = Firebase.storage

    private val viewModel by lazy {
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }
    private val auth = Firebase.auth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        signUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        signUpBinding.viewModel = viewModel
        signUpBinding.lifecycleOwner = this
        signUpBinding.signupAddPic.setOnClickListener { addPic() }
        signUpBinding.signupButton.setOnClickListener { register() }
        return signUpBinding.root
    }

    private fun updateUser(name: String, photoUriU: Uri?){
        auth.currentUser!!.updateProfile(userProfileChangeRequest{
            displayName = name
            if(photoUriU != null){
                photoUri = photoUriU
            }
        }).addOnCompleteListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun register(){
        if(viewModel.formIsValid()){
            val name: String = viewModel.name.value!!
            auth.createUserWithEmailAndPassword(viewModel.email.value!!, viewModel.password.value!!)
                .addOnSuccessListener {
                    if (::currentPhotoPath.isInitialized) {
                        val fileStream = FileInputStream(File(currentPhotoPath))
                        val filePath = storageRef.getReference("profile_${auth.currentUser!!.uid}")
                        filePath.putStream(fileStream)
                            .addOnSuccessListener {
                                filePath.downloadUrl.addOnSuccessListener {
                                    this.updateUser(name, it)
                                }
                            }
                    }else{
                        updateUser(name, null)
                    }
                }
                .addOnFailureListener {
                    showErrorToast()
                }
        } else {
            signUpBinding.signupName.error = getString(R.string.error_form_invalid)
            signUpBinding.signupLogin.error = getString(R.string.error_form_invalid)
            signUpBinding.signupPassword.error = getString(R.string.error_form_invalid)
        }

    }

    private fun showErrorToast(@StringRes stringId: Int = R.string.error_generic){
        Toast.makeText(requireContext(), getString(stringId), Toast.LENGTH_LONG).show()
    }

    fun addPic(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(context, getString(R.string.error_camera), Toast.LENGTH_LONG).show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        FILE_PROVIDER_AUTHORITY,
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = BitmapFactory.decodeFile(currentPhotoPath)
                    viewModel.profilePic.value = imageBitmap
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("pt-br")).format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }


}