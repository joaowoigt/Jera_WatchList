package com.woigt.jerawatchlist.activities.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.woigt.jerawatchlist.viewmodels.RegisterViewModel
import com.woigt.jerawatchlist.databinding.ActivityNewPerfilBinding

/**
 * Activity for registration of a new Profile on the FireStore Database
 * User need to enter a name
 */
class NewProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPerfilBinding
    private lateinit var userID: String
    private val registerViewModel: RegisterViewModel = RegisterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userID = FirebaseAuth.getInstance().currentUser?.uid ?: "0"

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
            } else {
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
        registerViewModel.registerProfile(name, userID)

    }
}
