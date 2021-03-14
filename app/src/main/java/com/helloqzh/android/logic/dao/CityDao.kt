package com.helloqzh.android.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.helloqzh.android.logic.model.City

@Dao
interface CityDao {

    @Query("select * from City where city like '%' || :query || '%' collate nocase or country like '%' || :query || '%' collate nocase or admin_name like '%' || :query || '%' collate nocase")
    fun searchCities(query: String): LiveData<List<City>>

}