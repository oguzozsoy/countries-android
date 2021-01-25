package com.wooz.countries.ui.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import com.wooz.countries.R
import com.wooz.countries.databinding.FragmentCountryDetailsBinding
import com.wooz.countries.domain.entity.Country
import com.wooz.countries.domain.entity.ResultData
import com.wooz.countries.ui.common.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author wooz
 * @since 09/10/2020
 */
@AndroidEntryPoint
class CountryDetailsFragment :
    BaseDialogFragment<CountryDetailsViewModel, FragmentCountryDetailsBinding>(),
    Toolbar.OnMenuItemClickListener {

    override val layoutRes: Int = R.layout.fragment_country_details
    override val viewModel: CountryDetailsViewModel by viewModels()

    private lateinit var mFavoriteCheckbox: CheckBox
    private lateinit var mCountry: Country

    companion object {
        const val TAG = "CountryDetailsFragment"
        private const val COUNTRY_CODE = "country_code"

        fun newInstance(countryCode: String): CountryDetailsFragment {
            val fragment = CountryDetailsFragment()
            val args = Bundle().apply {
                putString(COUNTRY_CODE, countryCode)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener { dismiss() }
        binding.toolbar.inflateMenu(R.menu.menu_country_details)
        binding.toolbar.setOnMenuItemClickListener(this)

        val startMenuItem = binding.toolbar.menu.findItem(R.id.action_favorite)
        mFavoriteCheckbox = startMenuItem.actionView as CheckBox

        arguments?.getString(COUNTRY_CODE)?.let {
            viewModel.fetchCountryDetails(it)
            viewModel.fetchCountry(it)
        }
    }

    override fun observeViewModel() {
        viewModel.country.observe(viewLifecycleOwner, {
            when (it) {
                is ResultData.Success -> {
                    mCountry = it.data!!
                    setFavoriteToggle(mFavoriteCheckbox, mCountry)
                }
                is ResultData.Loading -> {
                }
                is ResultData.Failed -> {
                }
            }
        })
    }

    private fun setFavoriteToggle(checkBox: CheckBox, country: Country) {
        checkBox.setOnCheckedChangeListener { _, b ->
            country.favorite = b
            viewModel.updateCountry(country)
        }
        checkBox.isChecked = country.favorite
    }

    private fun deleteCountry() {
        viewModel.deleteCountry(mCountry)
        dismiss()
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        return when (p0?.itemId) {
            R.id.action_delete -> {
                deleteCountry()
                true
            }
            else -> false
        }
    }

    override fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}