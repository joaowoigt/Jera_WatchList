package com.woigt.jerawatchlist.activities.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.woigt.jerawatchlist.R
import com.woigt.jerawatchlist.model.Movie


class WatchListAdapter(options: FirestoreRecyclerOptions<Movie>,
                        val onItemCliked: (Movie) -> Unit) :
    FirestoreRecyclerAdapter<Movie, WatchListAdapter.WatchlistMovieViewHolder>(options){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistMovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return WatchlistMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchlistMovieViewHolder, position: Int, model: Movie) {
            holder.bind(model)

        }

    inner class WatchlistMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.iv_movie_poster)
        private val action: ImageView = itemView.findViewById(R.id.iv_action)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)

            action.setImageResource(R.drawable.ic_baseline_check_box_24)

            action.setOnClickListener { onItemCliked.invoke(movie) }

        }
    }
}
