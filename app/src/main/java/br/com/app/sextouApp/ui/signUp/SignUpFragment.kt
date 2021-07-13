package br.com.app.sextouApp.ui.signUp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.app.sextouApp.MainActivity
import br.com.app.sextouApp.databinding.FragmentSignUpBinding
import br.com.app.sextouApp.utils.REQUEST_IMAGE_CAPTURE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    private lateinit var signUpBinding: FragmentSignUpBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }
    private val auth = Firebase.auth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        signUpBinding.viewModel = viewModel
        signUpBinding.lifecycleOwner = this
        signUpBinding.signupAddPic.setOnClickListener { addPic() }
        signUpBinding.signupButton.setOnClickListener { register() }
        return signUpBinding.root
    }

    fun register(){
        if(viewModel.formIsValid()){
            auth.createUserWithEmailAndPassword(viewModel.email.value!!, viewModel.password.value!!)
                .addOnSuccessListener {
                    auth.currentUser!!.updateProfile(userProfileChangeRequest{
                        displayName = viewModel.name.value
                    }).addOnCompleteListener {
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    }
                }
        }

    }

    fun addPic(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    viewModel.profilePic.value = imageBitmap
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}