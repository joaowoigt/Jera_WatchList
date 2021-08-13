package com.woigt.jerawatchlist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.woigt.jerawatchlist.activities.movies.MovieActivity
import com.woigt.jerawatchlist.activities.perfil.PerfilAdapter
import com.woigt.jerawatchlist.activities.register.LoginActivity
import com.woigt.jerawatchlist.activities.register.NewProfileActivity
import com.woigt.jerawatchlist.databinding.ActivityMainBinding
import com.woigt.jerawatchlist.model.Profile
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var userID: String

    var profileAdapter: PerfilAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        userID = FirebaseAuth.getInstance().currentUser?.uid ?: "0"

        if (rv_profile.layoutManager?.itemCount == 4) {
            binding.btProfileRegister.visibility = View.GONE
        }

        setUpRecyclerView()
        insertListeners()
    }

    private fun setUpRecyclerView() {
        val query: Query = FirebaseFirestore.getInstance()
            .collection("Usuarios").document(userID).collection("perfis")

        val options = FirestoreRecyclerOptions.Builder<Profile>()
            .setQuery(query, Profile::class.java).build()

        profileAdapter = PerfilAdapter(options) {
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("perfilName", it.name)
            startActivity(intent)
        }

        rv_profile.layoutManager = LinearLayoutManager(this)
        rv_profile.adapter = profileAdapter

    }


    override fun onStart() {
        super.onStart()
        bindUserData()

        profileAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        profileAdapter!!.stopListening()
    }

    private fun insertListeners() {
        binding.btLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.btProfileRegister.setOnClickListener {
            val intent = Intent(this, NewProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bindUserData() {
        val email = FirebaseAuth.getInstance().currentUser?.email
        userID = FirebaseAuth.getInstance().currentUser?.uid ?: "0"


        val documentReference = db.collection("Usuarios").document(userID)
        documentReference.addSnapshotListener { documentSnapshot, error ->
            if (documentSnapshot != null) {
                binding.tvUsername.text = documentSnapshot.getString("nome")
                binding.tvEmail.text = email

            }
        }
    }

}