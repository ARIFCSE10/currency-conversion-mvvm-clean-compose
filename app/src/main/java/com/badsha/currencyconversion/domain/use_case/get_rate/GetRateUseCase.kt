package com.badsha.currencyconversion.domain.use_case.get_rate

import com.badsha.currencyconversion.common.Constants
import com.badsha.currencyconversion.common.Resource
import com.badsha.currencyconversion.data.remote.dto.toRates
import com.badsha.currencyconversion.domain.model.Rate
import com.badsha.currencyconversion.domain.repository.RateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRateUseCase @Inject constructor(
    private val repository: RateRepository
) {
    operator fun invoke(baseCurrency: String = Constants.BASE_CURRENCY): Flow<Resource<List<Rate>>> =
        flow {
            try {
                emit(Resource.Loading())
                val rates = repository.getRate(baseCurrency = baseCurrency).toRates()
                emit(Resource.Success(data = rates))
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Error getting data"))
            } catch (e: IOException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Internet connection error"))
            }
        }
}