package com.helloqzh.android.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey var id: Int,
    val city: String,
    val city_ascii: String,
    val lat: String,
    val lng: String,
    val country: String,
    val iso2: String,
    val iso3: String,
    val admin_name: String,
    val capital: String,
    val population: String,
    val origin_id: Int)
