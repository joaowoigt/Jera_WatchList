package com.woigt.jerawatchlist.activities.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.woigt.jerawatchlist.R
import com.woigt.jerawatchlist.api.MoviesRepository
import com.woigt.jerawatchlist.model.Movie
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity() {

    private lateinit var usuarioId: String
    private lateinit var perfilName: String
    private lateinit var documentReference: DocumentReference

    private var watchListAdapter: WatchListAdapter? = null
    private var watchedListAdapter: WatchedAdapter? = null

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularPopularMoviesAdapter: PopularMoviesAdapter
    private lateinit var popularMoviesLayoutManager: LinearLayoutManager

    private var popularMoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        supportActionBar?.hide()

        usuarioId = FirebaseAuth.getInstance().currentUser?.uid ?: "0"
        perfilName = intent.getStringExtra("perfilName").toString()

        documentReference = FirebaseFirestore.getInstance()
            .collection("Usuarios").document(usuarioId)
            .collection("perfis").document(perfilName)


        getPopularMovies()
        setPopularMoviesRecyclerView()
        setWatchListRecyclerView()
        setWatchedListRecyclerView()
        insertListeners()
    }




    override fun onStart() {
        super.onStart()
        watchListAdapter!!.startListening()
        watchedListAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        watchListAdapter!!.stopListening()
        watchedListAdapter!!.stopListening()
    }

    private fun insertListeners() {
        bt_searchpage.setOnClickListener {
            val intent = Intent(this, SearchMovieActivity::class.java)
            intent.putExtra("perfilName", perfilName)
            startActivity(intent)
        }
    }

    private fun setWatchListRecyclerView() {
        val query  = documentReference.collection("watchlist")

        val options = FirestoreRecyclerOptions.Builder<Movie>()
            .setQuery(query, Movie::class.java).build()

        watchListAdapter =  WatchListAdapter(options) {movie -> removeAndAddToWatchedList(movie)  }

        rv_watchlist.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)
        rv_watchlist.adapter = watchListAdapter
    }

    private fun setPopularMoviesRecyclerView() {
        popularMovies = findViewById(R.id.rv_popular_movies)
        popularMoviesLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)
        popularMovies.layoutManager = popularMoviesLayoutManager

        popularPopularMoviesAdapter = PopularMoviesAdapter(mutableListOf()) { movie -> addWatchList(movie)  }
        popularMovies.adapter = popularPopularMoviesAdapter
    }

    private fun setWatchedListRecyclerView() {
        val query = documentReference.collection("watchedlist")

        val options = FirestoreRecyclerOptions.Builder<Movie>()
            .setQuery(query, Movie::class.java).build()

        watchedListAdapter = WatchedAdapter(options) {movie -> deleteMovie(movie)}

        rv_watched.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)
        rv_watched.adapter = watchedListAdapter

    }

    private fun getPopularMovies() {
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularPopularMoviesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, "Please Check your internet connection",
            Toast.LENGTH_LONG).show()
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutManager.itemCount
                val visibleItemCount = popularMoviesLayoutManager.childCount
                val firstVisibleItem = popularMoviesLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount /2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    private fun addWatchList(movie: Movie) {
            documentReference.collection("watchlist")
                .document(movie.title + "WatchList")
                .set(movie)

    }

    private fun removeAndAddToWatchedList(movie: Movie) {
        documentReference.collection("watchlist")
            .document(movie.title + "WatchList")
            .delete()

        documentReference.collection("watchedlist")
            .document(movie.title + "WatchedList")
            .set(movie)
    }

    private fun deleteMovie(movie: Movie) {
        documentReference.collection("watchedlist")
            .document(movie.title + "WatchedList")
            .delete()

    }


}