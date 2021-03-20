package com.helloqzh.android.ui.city

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.helloqzh.android.MainActivity
import com.helloqzh.android.R
import com.helloqzh.android.databinding.FragmentCityBinding
import com.helloqzh.android.logic.model.City
import com.helloqzh.android.ui.weather.WeatherActivity
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import java.util.concurrent.TimeUnit

class CityFragment : Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(CityViewModel::class.java) }

    private lateinit var adapter: CityAdapter

    private var _binding: FragmentCityBinding? = null;
    private val binding get() = _binding!!
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(activity is MainActivity && viewModel.isCitySaved()) {
            val city = viewModel.getSavedCity()
            startWeatherActivity(city)
            return
        }
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        adapter = CityAdapter(this, viewModel.cityList)
        binding.recyclerView.adapter = adapter
        binding.searchCityEdit
            .afterTextChangeEvents()
            .throttleLast(2, TimeUnit.SECONDS)
            .subscribe {
                activity?.runOnUiThread {
                    if (it.editable != null && it.editable!!.isNotEmpty()) {
                        viewModel.searchCities(it.editable.toString())
                    } else {
                        binding.recyclerView.visibility = View.GONE
                        binding.bgImageView.visibility = View.VISIBLE
                        viewModel.cityList.clear()
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        viewModel.cityLiveData.observe(viewLifecycleOwner) { result ->
            if (result != null && result.isNotEmpty()) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.bgImageView.visibility = View.GONE
                viewModel.cityList.clear()
                viewModel.cityList.addAll(result)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, R.string.city_not_found, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun startWeatherActivity(city: City) {
        val intent = Intent(context, WeatherActivity::class.java)
                .putExtra(WeatherActivity.INTENT_CITY, gson.toJson(city))
        startActivity(intent)
        activity?.finish()
    }
}