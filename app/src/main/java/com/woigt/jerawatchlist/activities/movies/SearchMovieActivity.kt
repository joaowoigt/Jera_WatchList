package com.woigt.jerawatchlist.activities.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woigt.jerawatchlist.R
import kotlinx.android.synthetic.main.activity_search_movie.*

class SearchMovieActivity : AppCompatActivity() {

    private lateinit var perfilName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        perfilName = intent.getStringExtra("perfilName").toString()


        insertListerners()
    }

    private fun insertListerners() {
        bt_backtomovies.setOnClickListener {
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("perfilName", perfilName)
            startActivity(intent)
            finish()
        }
    }
}