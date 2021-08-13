package com.woigt.jerawatchlist.activities.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.woigt.jerawatchlist.databinding.ActivitySignUpBinding
import com.woigt.jerawatchlist.utils.format
import com.woigt.jerawatchlist.utils.text
import java.util.*
import kotlin.collections.HashMap

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var usuarioID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        insertListeners()
    }

    private fun insertListeners() {
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

    private fun cadastraUsuario() {
        val senha = binding.inputEditPassword.text.toString()
        val email = binding.inputEditEmail.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener {

                if (it.isSuccessful) {

                    salvarDadosUsuario()

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

    private fun salvarDadosUsuario() {
        val nome = binding.inputEditUser.text.toString()
        val dataNascimento = binding.inputEditBirthdate.text.toString()

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()


        val usuarios: HashMap<String, String> = HashMap()
        usuarios["nome"] = nome
        usuarios["dataNascimento"] = dataNascimento

        usuarioID = FirebaseAuth.getInstance().currentUser?.uid ?: "0"

        val documentReference: DocumentReference = db.collection("Usuarios")
            .document(usuarioID)

        documentReference.set(usuarios).addOnSuccessListener {
            Log.d("db","Sucesso ao salvar os dados")
        }.addOnFailureListener {
            Log.d("db_error", "Erro ao salvar os dados")
        }
    }

    companion object {
        const val DATE_PICKER_TAG = "DATE_PICKER_TAG"
    }
}