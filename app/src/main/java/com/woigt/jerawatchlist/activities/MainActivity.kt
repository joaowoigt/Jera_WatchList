package com.woigt.jerawatchlist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.woigt.jerawatchlist.activities.movies.MovieActivity
import com.woigt.jerawatchlist.activities.perfil.PerfilAdapter
import com.woigt.jerawatchlist.activities.register.LoginActivity
import com.woigt.jerawatchlist.activities.register.NewPerfilActivity
import com.woigt.jerawatchlist.databinding.ActivityMainBinding
import com.woigt.jerawatchlist.model.Perfil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var usuarioId: String

    var perfilAdapter: PerfilAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        usuarioId = FirebaseAuth.getInstance().currentUser?.uid ?: "0"

        setUpRecyclerView()
        insertListeners()
    }

    private fun setUpRecyclerView() {
        val query: Query = FirebaseFirestore.getInstance()
            .collection("Usuarios").document(usuarioId).collection("perfis")

        val options = FirestoreRecyclerOptions.Builder<Perfil>()
            .setQuery(query, Perfil::class.java).build()

        perfilAdapter = PerfilAdapter(options) {
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("perfilName", it.name)
            startActivity(intent)
        }

        rv_perfil.layoutManager = LinearLayoutManager(this)
        rv_perfil.adapter = perfilAdapter

    }


    override fun onStart() {
        super.onStart()
        bindUserData()

        perfilAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        perfilAdapter!!.stopListening()
    }

    private fun insertListeners() {
        binding.btDeslogar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btPefilRegister.setOnClickListener {
            val intent = Intent(this, NewPerfilActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bindUserData() {
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