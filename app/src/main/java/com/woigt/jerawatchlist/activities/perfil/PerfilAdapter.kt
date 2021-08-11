package com.woigt.jerawatchlist.activities.perfil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.woigt.jerawatchlist.R
import com.woigt.jerawatchlist.model.Perfil
import com.woigt.jerawatchlist.model.PerfilFireBaseUi
import kotlinx.android.synthetic.main.item_perfil.view.*

class PerfilAdapter(options: FirestoreRecyclerOptions<PerfilFireBaseUi>) :
    FirestoreRecyclerAdapter<PerfilFireBaseUi, PerfilAdapter.PerfilHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_perfil, parent, false)

        return PerfilHolder(view)
    }

    override fun onBindViewHolder(holder: PerfilHolder, position: Int, model: PerfilFireBaseUi) {
        holder.name.text = model.name

    }


    class PerfilHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.tv_perfil_name)


    }




}




