package com.example.carservice

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View

class LoadingFragment : Fragment(R.layout.fragment_loading) {

    companion object {
        fun newInstance(query: String): LoadingFragment {
            val fragment = LoadingFragment()
            val args = Bundle()
            args.putString("search_query", query)
            fragment.arguments = args
            return fragment
        }
    }

    private var searchQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchQuery = arguments?.getString("search_query")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Допустим, показываем "загрузку" 2 секунды, потом переходим на фрагмент результатов
        view.postDelayed({
            goToResults()
        }, 2000L)
    }

    private fun goToResults() {
        val resultsFragment = SearchResultsFragment.newInstance(searchQuery ?: "")
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, resultsFragment)
            .addToBackStack(null)
            .commit()
    }
}
