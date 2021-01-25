package com.wooz.countries.ui.countries

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wooz.countries.domain.entity.Country
import com.wooz.countries.domain.entity.ResultData
import com.wooz.countries.domain.usercase.GetAllCountriesUseCase
import com.wooz.countries.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author wooz
 * @since 09/10/2020
 */
class CountriesViewModel @ViewModelInject constructor(
    private val getAllCountriesUseCase: GetAllCountriesUseCase) : BaseViewModel() {

    companion object{
        private const val TAG = "CountriesViewModel"
    }

    private val _countries = MutableLiveData<ResultData<List<Country>>>()
    val countries: LiveData<ResultData<List<Country>>>
        get() = _countries

    fun fetchCountries(){
        viewModelScope.launch(Dispatchers.IO) {
            getAllCountriesUseCase.invoke().collect{
                handleTask(it){
                    _countries.postValue(it)
                }
            }
        }
    }

}