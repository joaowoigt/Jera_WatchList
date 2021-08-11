package com.woigt.jerawatchlist.activities.register

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.woigt.jerawatchlist.databinding.ActivityNewPerfilBinding
import com.woigt.jerawatchlist.model.Perfil

class NewPerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPerfilBinding

    private lateinit var usuarioID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners() {
        binding.btNewPerfil.setOnClickListener {
            val name = binding.edtNewPerfil.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG)
                    .show()
            }else {
                cadastrarPerfil()
                finish()
            }
        }
    }

    private fun cadastrarPerfil() {
        val name = binding.edtNewPerfil.text.toString()

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val perfil = Perfil(name)


        usuarioID = FirebaseAuth.getInstance().currentUser?.uid ?: "0"


        val documentReference: DocumentReference = db.collection("Usuarios")
            .document(usuarioID).collection("perfis").document()


        documentReference.set(perfil).addOnSuccessListener {
            Log.d("db", "Sucesso ao salvar os dados")
        }.addOnFailureListener {
            Log.d("db_error", "Erro ao salvar os dados")
        }
    }


}




