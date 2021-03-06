package com.wooz.countries.data.repository

import com.wooz.countries.data.dto.CountryDetailsDto
import com.wooz.countries.data.dto.CountryDto
import com.wooz.countries.data.framework.remote.common.ApiResponse
import com.wooz.countries.data.repository.common.NetworkBoundResource
import com.wooz.countries.domain.common.Mapper
import com.wooz.countries.domain.entity.Country
import com.wooz.countries.domain.entity.CountryDetails
import com.wooz.countries.domain.entity.ResultData
import com.wooz.countries.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author wooz
 * @since 09/10/2020
 */
class CountryRepositoryImpl @Inject constructor(
    private val remoteDataSource: CountryRemoteDataSource,
    private val localDataSource: CountryLocalDataSource,
    private val countryMapper: Mapper<Country, CountryDto>,
    private val countryDetailsMapper: Mapper<CountryDetails, CountryDetailsDto>
) : CountryRepository {

    companion object {
        private const val TAG = "CountryRepositoryImpl"
    }

    override fun getAllCountries(): Flow<ResultData<List<Country>>> = flow {
        object : NetworkBoundResource<List<CountryDto>>() {
            override suspend fun saveCallResult(item: List<CountryDto>) {
                localDataSource.insertAllCountries(item)
            }

            override fun shouldFetch(data: List<CountryDto>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun loadFromDb(): Flow<List<CountryDto>> {
                return localDataSource.getAllCountries()
            }

            override suspend fun createCall(): ApiResponse<List<CountryDto>> {
                return remoteDataSource.getAllCountries()
            }

        }.asFlow().collect {
            when (it) {
                is ResultData.Loading -> {
                    emit(ResultData.Loading())
                }
                is ResultData.Success -> {
                    emit(ResultData.Success(countryMapper.mapToEntityList(it.data!!)))
                }
                is ResultData.Failed -> {
                    emit(ResultData.Failed(it.error))
                }
            }
        }
    }

    override fun getCountryDetailsByCode(code: String): Flow<ResultData<CountryDetails>> = flow {
        object : NetworkBoundResource<CountryDetailsDto>() {
            override suspend fun saveCallResult(item: CountryDetailsDto) {
                localDataSource.insertCountryDetails(item)
            }

            override fun shouldFetch(data: CountryDetailsDto?): Boolean {
                return data == null || System.currentTimeMillis() - data.updatedAt > TimeUnit.DAYS.toMillis(
                    30
                )
            }

            override fun loadFromDb(): Flow<CountryDetailsDto> {
                return localDataSource.getCountryDetailsByCode(code)
            }

            override suspend fun createCall(): ApiResponse<CountryDetailsDto> {
                return remoteDataSource.getCountryDetailsByCode(code)
            }

        }.asFlow().collect {
            when (it) {
                is ResultData.Loading -> {
                    emit(ResultData.Loading())
                }
                is ResultData.Success -> {
                    emit(ResultData.Success(countryDetailsMapper.mapToEntity(it.data!!)))
                }
                is ResultData.Failed -> {
                    emit(ResultData.Failed(it.error))
                }
            }
        }
    }

    override fun getCountryByCode(code: String): Flow<ResultData<Country>> = flow {
        emit(ResultData.Loading())
        val country = localDataSource.getCountryByCode(code)
        country.collect {
            if (it == null) {
                emit(ResultData.Failed("Country not found"))
            } else {
                emit(ResultData.Success(countryMapper.mapToEntity(it)))
            }
        }
    }

    override suspend fun updateCountry(country: Country): ResultData<Unit> {
        val numberOfRowsUpdated = localDataSource.updateCountry(countryMapper.mapToDto(country))
        return if (numberOfRowsUpdated == 0) ResultData.Failed("Country Not Found") else ResultData.Success()
    }

    override suspend fun deleteCountry(country: Country): ResultData<Unit> {
        val numberOfRowsDeleted = localDataSource.deleteCountry(countryMapper.mapToDto(country))
        return if (numberOfRowsDeleted == 0) ResultData.Failed("Country Not Found") else ResultData.Success()
    }
}