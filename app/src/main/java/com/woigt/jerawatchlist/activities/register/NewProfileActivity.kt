package com.woigt.jerawatchlist.activities.register

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.woigt.jerawatchlist.databinding.ActivityNewPerfilBinding
import com.woigt.jerawatchlist.model.Profile

/**
 * Activity for registration of a new Profile on the FireStore Database
 * User need to enter a name
 */

class NewProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPerfilBinding
    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners() {
        /**
         * Register the new Profile
         */
        binding.btNewProfile.setOnClickListener {
            val name = binding.edtNewProfile.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG)
                    .show()
            }else {
                registerProfile()
                finish()
            }
        }
    }
    /**
     * Function to register the new Profile
     */
    private fun registerProfile() {
        val name = binding.edtNewProfile.text.toString()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val Profile = Profile(name)

        userID = FirebaseAuth.getInstance().currentUser?.uid ?: "0"

        val documentReference: DocumentReference = db.collection("Usuarios")
            .document(userID).collection("perfis").document(Profile.name)

        documentReference.set(Profile).addOnSuccessListener {
            Log.d("db", "Sucesso ao salvar os dados")
        }.addOnFailureListener {
            Log.d("db_error", "Erro ao salvar os dados")
        }
    }
}
