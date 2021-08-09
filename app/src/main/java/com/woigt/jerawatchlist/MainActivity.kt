package com.woigt.jerawatchlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.woigt.jerawatchlist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var usuarioId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        insertListeners()
    }

    private fun insertListeners() {
        binding.btDeslogar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        val email = FirebaseAuth.getInstance().currentUser?.email
        usuarioId = FirebaseAuth.getInstance().currentUser?.uid ?: "0"


        val documentReference = db.collection("Usuarios").document(usuarioId)
        documentReference.addSnapshotListener { documentSnapshot, error ->
            if (documentSnapshot != null) {
                binding.tvUsername.text = documentSnapshot.getString("nome")
                binding.tvEmail.text = email

            }
        }

    }

}