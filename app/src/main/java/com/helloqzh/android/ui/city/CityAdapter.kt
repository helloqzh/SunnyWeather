package com.helloqzh.android.ui.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.helloqzh.android.R
import com.helloqzh.android.logic.model.City

class CityAdapter(private val fragment: Fragment, private val cityList: List<City>) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityName: TextView = view.findViewById(R.id.cityName)
        val cityAddress: TextView = view.findViewById(R.id.cityAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = cityList[position]
        holder.cityAddress.text = "${place.country} ${place.admin_name} (${place.lat}, ${place.lng})"
        holder.cityName.text = place.city
    }

    override fun getItemCount() = cityList.size
}