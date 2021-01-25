package com.wooz.countries.ui.countries

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wooz.countries.MainActivity
import com.wooz.countries.R
import com.wooz.countries.databinding.FragmentCountriesBinding
import com.wooz.countries.domain.entity.Country
import com.wooz.countries.domain.entity.ResultData
import com.wooz.countries.ui.common.BaseFragment
import com.wooz.countries.ui.details.CountryDetailsFragment
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author wooz
 * @since 09/10/2020
 */
@AndroidEntryPoint
class CountriesFragment : BaseFragment<CountriesViewModel, FragmentCountriesBinding>() {
    override val layoutRes: Int = R.layout.fragment_countries
    override val viewModel: CountriesViewModel by viewModels()

    private lateinit var adapter: CountryAdapter
    private lateinit var searchView: SearchView
    private lateinit var navController: NavController

    companion object {
        private const val TAG = "CountriesFragment"
    }

    override fun observeViewModel() {
        viewModel.countries.observe(viewLifecycleOwner,  {
            when (it) {
                is ResultData.Success -> {
                    adapter.data = it.data!! as ArrayList<Country>
                }
                is ResultData.Failed -> {
                    Log.e(TAG, "observeViewModel: ${it.error}")
                }
                is ResultData.Loading -> {
                }
            }
        })
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        (activity as MainActivity?)!!.setSupportActionBar(binding.toolbar)

        navController = findNavController()

        binding.recyclerViewCountries.addItemDecoration(
            CountriesItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL,
                32
            )
        )

        adapter = CountryAdapter(CountryClickListener { code ->
            CountryDetailsFragment.newInstance(code)
                .show(requireActivity().supportFragmentManager, CountryDetailsFragment.TAG)
        })
        binding.recyclerViewCountries.adapter = adapter
        viewModel.fetchCountries()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_countries, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}