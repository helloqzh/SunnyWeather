package com.helloqzh.android.ui.city

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.helloqzh.android.R
import com.helloqzh.android.logic.model.City
import com.helloqzh.android.ui.weather.WeatherActivity

class CityAdapter(private val fragment: CityFragment, private val cityList: List<City>) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private val gson = Gson()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityName: TextView = view.findViewById(R.id.cityName)
        val cityAddress: TextView = view.findViewById(R.id.cityAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position = holder.adapterPosition
            val city = cityList[position]
            fragment.startWeatherActivity(city, true)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cityList[position]
        val addressText = "${city.country} ${city.admin_name} ${city.city} (${city.lng},${city.lat})"
        holder.cityAddress.text = addressText
        holder.cityName.text = city.city_ascii
    }

    override fun getItemCount() = cityList.size
}