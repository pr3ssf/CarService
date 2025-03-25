package com.example.carservice

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

class SearchResultsFragment : Fragment(R.layout.fragment_search_result) {

    companion object {
        fun newInstance(query: String): SearchResultsFragment {
            val fragment = SearchResultsFragment()
            val args = Bundle()
            args.putString("search_query", query)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var carAdapter: CarAdapter
    private val fullCarList = listOf(
        Car("S 500 Sedan", "Mercedes-Benz", 2500, "Бензин"),
        Car("Camry", "Toyota", 1500, "Бензин"),
        Car("A6", "Audi", 2200, "Дизель"),
        Car("X5", "BMW", 3000, "Бензин"),
        // ... можно использовать те же данные
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvResults = view.findViewById<RecyclerView>(R.id.rvSearchResults)

        val query = arguments?.getString("search_query", "") ?: ""

        // Фильтруем по названию или бренду
        val filteredCars = fullCarList.filter {
            it.title.contains(query, ignoreCase = true)
                    || it.brand.contains(query, ignoreCase = true)
        }

        carAdapter = CarAdapter(filteredCars)
        rvResults.layoutManager = LinearLayoutManager(requireContext())
        rvResults.adapter = carAdapter
    }
}
