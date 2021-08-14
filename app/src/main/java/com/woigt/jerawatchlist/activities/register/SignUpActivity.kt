package com.woigt.jerawatchlist.activities.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.*
import com.woigt.jerawatchlist.viewmodels.RegisterViewModel
import com.woigt.jerawatchlist.databinding.ActivitySignUpBinding
import com.woigt.jerawatchlist.utils.format
import com.woigt.jerawatchlist.utils.text
import java.util.*

/**
 * Activity to register a new user on the Firestore Database and FireStore Authentication.
 * Users need to enter a username, password, valid email anda birthdate.
 */
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var userID: String
    private val registerViewModel: RegisterViewModel = RegisterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        insertListeners()
    }

    private fun insertListeners() {
        /**
         * Register the new user
         */
        binding.btRegisterSignup.setOnClickListener {
            val usuario = binding.inputEditUser.text.toString()
            val senha = binding.inputEditPassword.text.toString()
            val email = binding.inputEditEmail.text.toString()
            val data = binding.inputEditBirthdate.text.toString()

            if (usuario.isEmpty() || senha.isEmpty() || email.isEmpty() || data.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG)
                    .show()
            } else {
                cadastraUsuario()
                finish()

            }
        }
        /**
         * Date picker based on material design
         */
        binding.inputBirthdate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.inputBirthdate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, DATE_PICKER_TAG)
        }
    }
    /**
     * Function to register the user on the Firebase Authentication
     */
    private fun cadastraUsuario() {
        val senha = binding.inputEditPassword.text.toString()
        val email = binding.inputEditEmail.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    saveUserData()
                    Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val erro: String
                    try {
                        throw it.exception!!
                        } catch(e: FirebaseAuthWeakPasswordException) {
                            erro = "Digite uma senha com no mínimo 6 caracteres"
                        }catch (e: FirebaseAuthUserCollisionException) {
                        erro = "Este email ja foi cadastrado"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        erro = "E-mail inválido"
                    } catch (e: Exception) {
                        erro = "Erro ao cadastrar o usuário"
                    }
                   Toast.makeText(this, erro, Toast.LENGTH_LONG).show()
                }
            }
    }
    /**
     * Function to save the user data on the Firestore Database
     */
    private fun saveUserData() {
        val nome = binding.inputEditUser.text.toString()
        val brithDate = binding.inputEditBirthdate.text.toString()
        userID = FirebaseAuth.getInstance().currentUser?.uid ?: "0"
        registerViewModel.saveUserData(nome,brithDate, userID)
    }
    companion object {
        const val DATE_PICKER_TAG = "DATE_PICKER_TAG"
    }
}
