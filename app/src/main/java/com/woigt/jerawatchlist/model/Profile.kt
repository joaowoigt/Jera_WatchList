package com.woigt.jerawatchlist.model

/**
 *  Model class for a Profile
 *
 *  @param name name of the profile
 */
class Profile(
    var name: String,
 ){

    /**
     * The empty constructor is necessary to the Firestore Database
     */
    constructor() : this("")
}
