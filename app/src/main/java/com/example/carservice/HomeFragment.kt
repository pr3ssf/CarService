package com.example.carservice

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.inputmethod.EditorInfo

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var carAdapter: CarAdapter
    private val carList = mutableListOf<Car>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carList.addAll(listOf(
            Car("S 500 Sedan", "Mercedes-Benz", 2500, "Бензин"),
            Car("Camry", "Toyota", 1500, "Бензин"),
            Car("A6", "Audi", 2200, "Дизель"),
            Car("X5", "BMW", 3000, "Бензин"),
        ))

        val rvCars = view.findViewById<RecyclerView>(R.id.rvCars)
        val etSearch = view.findViewById<EditText>(R.id.etSearch)

        carAdapter = CarAdapter(carList)
        rvCars.layoutManager = LinearLayoutManager(requireContext())
        rvCars.adapter = carAdapter

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || actionId == EditorInfo.IME_ACTION_GO
                || actionId == EditorInfo.IME_ACTION_UNSPECIFIED
            ) {
                val query = etSearch.text.toString().trim()
                if (query.isNotEmpty()) {
                    showLoading(query)
                }
                true
            } else {
                false
            }
        }
    }

    private fun showLoading(query: String) {
        val loadingFragment = LoadingFragment.newInstance(query)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, loadingFragment)
            .addToBackStack(null)
            .commit()
    }
}

