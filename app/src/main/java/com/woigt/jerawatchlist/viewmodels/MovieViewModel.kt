package com.woigt.jerawatchlist.viewmodels


import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.woigt.jerawatchlist.model.Movie

/**
 *  ViewModel used on all movies Activities
 */
class MovieViewModel: ViewModel() {
    /**
     * Add the movie to the Watchlist on the FireStore
     */
    fun addWatchList(documentReference: DocumentReference, movie: Movie) {
        documentReference.collection("watchlist")
            .document(movie.title + "WatchList")
            .set(movie)

    }
    /**
     * Remove the clicked movie from the Watchlist and add to the WatchedList on the Firestore
     */
   fun removeAndAddToWatchedList(documentReference: DocumentReference, movie: Movie) {
        documentReference.collection("watchlist")
            .document(movie.title + "WatchList").delete()

        documentReference.collection("watchedlist")
            .document(movie.title + "WatchedList")
            .set(movie)
    }

   /**
    * Delete the clicked movie from the WatcheList
    */
   fun deleteMovie(documentReference: DocumentReference, movie: Movie) {
        documentReference.collection("watchedlist")
            .document(movie.title + "WatchedList")
            .delete()

    }

}
