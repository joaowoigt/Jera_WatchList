package com.woigt.jerawatchlist.activities.perfil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.woigt.jerawatchlist.R
import com.woigt.jerawatchlist.model.Profile

class PerfilAdapter(options: FirestoreRecyclerOptions<Profile>,
                    val profileLogin: (Profile) -> Unit) :
    FirestoreRecyclerAdapter<Profile, PerfilAdapter.PerfilHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_perfil, parent, false)

        return PerfilHolder(view)
    }

    override fun onBindViewHolder(holder: PerfilHolder, position: Int, model: Profile) {
        holder.login.setOnClickListener {
            profileLogin.invoke(model)
        }

        holder.name.text = model.name
    }

    class PerfilHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.tv_profile_name)
        var login: ImageButton = itemView.findViewById(R.id.ib_profile_login)

    }
}
