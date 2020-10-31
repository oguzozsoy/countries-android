package com.wooz.countries.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.wooz.countries.R
import com.wooz.countries.databinding.FragmentCountryDetailsBinding
import com.wooz.countries.domain.entity.Country
import com.wooz.countries.domain.entity.ResultData
import com.wooz.countries.ui.common.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat

/**
 * @author wooz
 * @since 09/10/2020
 */
@AndroidEntryPoint
class CountryDetailsFragment :
    BaseDialogFragment<CountryDetailsViewModel, FragmentCountryDetailsBinding>() {

    override val layoutRes: Int = R.layout.fragment_countries
    override val viewModel: CountryDetailsViewModel by viewModels()

    private lateinit var mFavoriteCheckbox: CheckBox

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

    private fun setFlag(flag: String) {
        val requestBuilder = GlideToVectorYou
            .init()
            .with(context)
            .requestBuilder

        requestBuilder
            .load(Uri.parse(flag))
            .into(binding.imageViewFlag)
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener { dismiss() }
        binding.toolbar.inflateMenu(R.menu.menu_country_details)

        val startMenuItem = binding.toolbar.menu.findItem(R.id.action_favorite)
        mFavoriteCheckbox = startMenuItem.actionView as CheckBox

        arguments?.getString(COUNTRY_CODE)?.let {
            viewModel.fetchCountryDetails(it)
            viewModel.fetchCountry(it)
        }
    }

    override fun observeViewModel() {
        viewModel.countryDetails.observe(viewLifecycleOwner, {
            when (it) {
                is ResultData.Success -> {
                    val country = it.data!!
                    binding.toolbar.title = country.name
                    setFlag(country.flag)
                    binding.textViewNativeName.text =
                        getString(R.string.native_name, country.nativeName)
                    binding.textViewCapital.text = getString(R.string.capital, country.capital)
                    binding.textViewPopulation.text = getString(
                        R.string.population,
                        NumberFormat.getNumberInstance().format(country.population)
                    )
                    binding.textViewRegion.text =
                        getString(R.string.region, country.region, country.subRegion)
                    binding.textViewArea.text = getString(
                        R.string.area,
                        NumberFormat.getNumberInstance().format(country.area)
                    )
                    binding.textViewCallingCodes.text =
                        getString(R.string.calling_codes, country.callingCodes.toString())
                    binding.textViewTimeZones.text =
                        getString(R.string.time_zones, country.timeZones.toString())
                    binding.textViewBorders.text =
                        getString(R.string.borders, country.borders.toString())
                    binding.textViewLanguages.text = getString(
                        R.string.languages,
                        country.languages[0].name,
                        country.languages[0].nativeName
                    )
                    binding.textViewCurrencies.text = getString(
                        R.string.currencies,
                        country.currencies[0].code,
                        country.currencies[0].name,
                        country.currencies[0].symbol
                    )
                }
                is ResultData.Loading -> {
                }
                is ResultData.Failed -> {
                }
            }
        })

        viewModel.country.observe(viewLifecycleOwner, {
            when (it) {
                is ResultData.Success -> {
                    setFavoriteToggle(mFavoriteCheckbox, it.data!!)
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

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCountryDetailsBinding {
        return FragmentCountryDetailsBinding.inflate(inflater, container, false)
    }
}