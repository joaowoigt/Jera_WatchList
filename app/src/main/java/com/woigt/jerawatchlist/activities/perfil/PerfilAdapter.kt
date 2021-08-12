package com.woigt.jerawatchlist.activities.perfil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.woigt.jerawatchlist.R
import com.woigt.jerawatchlist.model.Perfil

class PerfilAdapter(options: FirestoreRecyclerOptions<Perfil>,
                    val onItemClicked: (Perfil) -> Unit) :
    FirestoreRecyclerAdapter<Perfil, PerfilAdapter.PerfilHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_perfil, parent, false)

        return PerfilHolder(view)
    }

    override fun onBindViewHolder(holder: PerfilHolder, position: Int, model: Perfil) {
        holder.login.setOnClickListener {
            onItemClicked(model)
        }
        holder.name.text = model.name

    }


    class PerfilHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.tv_perfil_name)
        var login = itemView.findViewById<ImageView>(R.id.iv_perfil_login)


    }



}




