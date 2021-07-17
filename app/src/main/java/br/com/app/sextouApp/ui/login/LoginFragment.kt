package br.com.app.sextouApp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.app.sextouApp.MainActivity
import br.com.app.sextouApp.R
import br.com.app.sextouApp.databinding.FragmentLoginBinding
import br.com.app.sextouApp.utils.RC_SIGN_IN_GOOGLE
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private val auth = Firebase.auth

    val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }


    private lateinit var dataBinding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = FragmentLoginBinding.inflate(inflater, container, false)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this
        dataBinding.signIn.setOnClickListener { loginWithEmailAndPassword() }
        dataBinding.signUp.setOnClickListener { signUp() }
        dataBinding.socialButtonGoogle.setOnClickListener { loginWithGoogle() }
        return dataBinding.root
    }

    fun loginWithEmailAndPassword(){
        if (viewModel.formIsValid()){
            auth.signInWithEmailAndPassword(viewModel.login.value!!, viewModel.password.value!!)
                .addOnSuccessListener {
                    login()
                }
                .addOnFailureListener {
                    if (it is FirebaseAuthInvalidCredentialsException){
                        dataBinding.login.error = getString(R.string.login_error_auth)
                        dataBinding.password.error = getString(R.string.login_error_auth)
                    }
                    showErrorToast()
                }
        }else{
            dataBinding.login.error = getString(R.string.error_form_invalid)
            dataBinding.password.error = getString(R.string.error_form_invalid)
        }
    }

    fun loginWithGoogle(){
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            RC_SIGN_IN_GOOGLE -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    showErrorToast()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    login()
                }else{
                    showErrorToast()
                }
            }
    }

    private fun showErrorToast(@StringRes stringId: Int = R.string.error_generic){
        Toast.makeText(requireContext(), getString(stringId), Toast.LENGTH_LONG).show()
    }

    private fun login(){
        if (auth.currentUser != null){
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun signUp(){
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
    }
}