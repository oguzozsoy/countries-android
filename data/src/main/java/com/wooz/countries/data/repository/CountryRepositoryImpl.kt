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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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

    companion object{
        private const val TAG = "CountryRepositoryImpl"
    }
    
    override fun getAllCountries(): Flow<ResultData<List<Country>>> = flow {
        object : NetworkBoundResource<List<CountryDto>>() {
            override suspend fun saveCallResult(item: List<CountryDto>) {
                localDataSource.insertAllCountries(item)
            }

            override fun shouldFetch(data: List<CountryDto>?): Boolean {
                return data.isNullOrEmpty() || System.currentTimeMillis() - data[0].updatedAt > TimeUnit.DAYS.toMillis(360)
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
                return data == null || System.currentTimeMillis() - data.updatedAt > TimeUnit.DAYS.toMillis(30)
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
}