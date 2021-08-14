package com.woigt.jerawatchlist.viewmodels

import android.util.Log

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.woigt.jerawatchlist.model.Profile

/**
 * ViewModel used in all Register Activities
 */
class RegisterViewModel: ViewModel() {
    /**
     * Save the user on the FireStore Database
     */
    fun saveUserData(user: FirebaseUser) {
        val db = FirebaseFirestore.getInstance()

        val usuarios: HashMap<String, String> = HashMap()
        usuarios["nome"] = user.displayName.toString()


        val documentReference: DocumentReference = db.collection("Usuarios")
            .document(user.uid)

        documentReference.set(usuarios).addOnSuccessListener {
            Log.d("db","Sucesso ao salvar os dados")
        }.addOnFailureListener {
            Log.d("db_error", "Erro ao salvar os dados")

        }
    }

    fun registerProfile(name :String, userID: String) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val Profile = Profile(name)

        val documentReference: DocumentReference = db.collection("Usuarios")
            .document(userID).collection("perfis").document(Profile.name)

        documentReference.set(Profile).addOnSuccessListener {
            Log.d("db", "Sucesso ao salvar os dados")
        }.addOnFailureListener {
            Log.d("db_error", "Erro ao salvar os dados")
        }
    }
    /**
     * Function to save the user data on the Firestore Database
     */
    fun saveUserData(name: String, birthDate: String, userID: String) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val usuarios: HashMap<String, String> = HashMap()
        usuarios["nome"] = name
        usuarios["dataNascimento"] = birthDate

        val documentReference: DocumentReference = db.collection("Usuarios")
            .document(userID)

        documentReference.set(usuarios).addOnSuccessListener {
            Log.d("db","Sucesso ao salvar os dados")
        }.addOnFailureListener {
            Log.d("db_error", "Erro ao salvar os dados")
        }
    }
}
