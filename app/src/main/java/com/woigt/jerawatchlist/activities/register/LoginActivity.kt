package com.woigt.jerawatchlist.activities.register

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.woigt.jerawatchlist.activities.MainActivity
import com.woigt.jerawatchlist.viewmodels.RegisterViewModel
import com.woigt.jerawatchlist.databinding.ActivityLoginBinding

/**
 * Activity to login an already existing user or to enter with Facebook.
 * Users need to enter a valid email and password.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var callbackManager : CallbackManager
    private lateinit var auth: FirebaseAuth
    private val registerViewModel : RegisterViewModel = RegisterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager =  CallbackManager.Factory.create()
        auth = Firebase.auth

        insertListeners()
    }

    /**
     * Move the user to the Main Activity if already logged previously
     */
    override fun onStart() {
        super.onStart()
        val usuarioAtual: FirebaseUser? = auth.currentUser
        if (usuarioAtual != null) {
            toMainActivity()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun insertListeners() {
        /**
         * Start the SingUp Activity
         */
        binding.btCadastar.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        /**
         * Login in the app
         */
        binding.btLogin.setOnClickListener {
            val email = binding.edtLoginEmail.text.toString()
            val senha =  binding.edtLoginSenha.text.toString()

            if(email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG)
                    .show()
            }else {
                authenticateUser()
            }
        }
        /**
         * Login with Facebook
         */
        val loginButton = binding.loginFacebook
        loginButton.setReadPermissions("public_profile", "email")

        loginButton.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                d("infos", "Success Login")
                handleFacebookAccessToken(result.accessToken)
            }
            override fun onCancel() {
                Toast.makeText(this@LoginActivity, "Login Cancelled", Toast.LENGTH_LONG)
                    .show()
            }
            override fun onError(error: FacebookException?) {
                Toast.makeText(this@LoginActivity, error?.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("infos", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Log.d("infos", "signInWithCredential:success")
                    val user = auth.currentUser
                    if (user != null) {
                        registerViewModel.saveUserData(user)
                        toMainActivity()
                    }

                } else {
                    Log.w("infos", "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun authenticateUser() {
        val email = binding.edtLoginEmail.text.toString()
        val password =  binding.edtLoginSenha.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.loginProgressbar.visibility = View.VISIBLE
                    Handler().postDelayed({ toMainActivity() }, 3000 )
                } else {
                    val erro: String
                    try {
                        throw it.exception!!
                    } catch (e: Exception) {
                       erro = "Falha ao fazer o Login"
                    }
                    Toast.makeText(this, erro, Toast.LENGTH_LONG)
                        .show()
                }
        }
    }

    private fun toMainActivity() {
            val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
