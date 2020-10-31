package com.wooz.countries.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wooz.countries.domain.entity.Country
import com.wooz.countries.domain.entity.CountryDetails
import com.wooz.countries.domain.entity.ResultData
import com.wooz.countries.domain.usercase.DeleteCountryUseCase
import com.wooz.countries.domain.usercase.GetCountryByCodeUseCase
import com.wooz.countries.domain.usercase.GetCountryDetailsByCodeUseCase
import com.wooz.countries.domain.usercase.UpdateCountryUseCase
import com.wooz.countries.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author wooz
 * @since 09/10/2020
 */
class CountryDetailsViewModel @ViewModelInject constructor(
    private val getCountryDetailsByCodeUseCase: GetCountryDetailsByCodeUseCase,
    private val getCountryByCodeUseCase: GetCountryByCodeUseCase,
    private val updateCountryUseCase: UpdateCountryUseCase,
    private val deleteCountryUseCase: DeleteCountryUseCase
) :
    BaseViewModel() {
    private val _countryDetails = MutableLiveData<ResultData<CountryDetails>>()
    val countryDetails: LiveData<ResultData<CountryDetails>>
        get() = _countryDetails

    fun fetchCountryDetails(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getCountryDetailsByCodeUseCase.invoke(code).collect {
                handleTask(it) {
                    _countryDetails.postValue(it)
                }
            }
        }
    }

    private val _country = MutableLiveData<ResultData<Country>>()
    val country: LiveData<ResultData<Country>>
        get() = _country

    fun fetchCountry(code: String) {
        viewModelScope.launch {
            getCountryByCodeUseCase.invoke(code).collect{
                _country.postValue(it)
            }
        }
    }

    fun updateCountry(country: Country) = viewModelScope.launch {
        updateCountryUseCase.invoke(country)
    }

    fun deleteCountry(country: Country) = viewModelScope.launch {
        deleteCountryUseCase.invoke(country)
    }
}