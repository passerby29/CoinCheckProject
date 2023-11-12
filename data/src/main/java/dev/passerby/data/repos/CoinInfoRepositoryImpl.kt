package dev.passerby.data.repos

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import dev.passerby.data.database.AppDatabase
import dev.passerby.data.mappers.CoinHistoryMapper
import dev.passerby.data.mappers.CoinMapper
import dev.passerby.data.models.db.CoinHistoryDbModel
import dev.passerby.data.models.dto.history.CoinHistoryDto
import dev.passerby.data.network.ApiFactory
import dev.passerby.data.network.BaseResponse
import dev.passerby.domain.models.CoinHistoryModel
import dev.passerby.domain.models.CoinModel
import dev.passerby.domain.repos.CoinInfoRepository

class CoinInfoRepositoryImpl(application: Application) : CoinInfoRepository {

    private val db = AppDatabase.getInstance(application)
    private val coinDao = db.coinDao()
    private val favoriteDao = db.favoriteDao()
    private val coinHistoryDao = db.coinHistoryDao()
    private val apiService = ApiFactory.apiService
    private val coinMapper = CoinMapper()
    private val coinHistoryMapper = CoinHistoryMapper()
    private var coinHistoryResult: MutableLiveData<BaseResponse<CoinHistoryDto>> = MutableLiveData()

    override suspend fun getCoinHistory(coinId: String): LiveData<CoinHistoryModel> {
        val coinHistory = coinHistoryDao.getCoinHistory(coinId)
        return coinHistory.map {
            coinHistoryMapper.mapDbModelToEntity(it ?: CoinHistoryDbModel(0, "", emptyList()))
        }
    }

    override suspend fun getCoinInfo(coinId: String): LiveData<CoinModel> {
        val coinInfo = coinDao.getCoinInfo(coinId)
        return coinInfo.map { coinMapper.mapDbModelToEntity(it) }
    }

    override suspend fun loadCoinHistory(
        coinId: String,
        period: String
    ): LiveData<CoinHistoryModel>? {

        val coinHistoryLiveData = MutableLiveData<CoinHistoryModel>()

        coinHistoryResult.postValue(BaseResponse.Loading())
        try {
            val response = apiService.loadCoinHistory(coinId, period)
            if (response.code() == 200) {
                coinHistoryResult.postValue(BaseResponse.Success(response.body()))
                coinHistoryLiveData.postValue(
                    coinHistoryMapper.mapDtoToEntity(coinId, response.body()!!)
                )
                Log.d(TAG, "loadCoinHistoryTry: ${response.isSuccessful}")
                return coinHistoryLiveData
            } else {
                coinHistoryResult.postValue(BaseResponse.Error(response.message()))
                Log.d(TAG, "loadCoinHistoryElse: ${response.message()}")
            }
        } catch (ex: Exception) {
            coinHistoryResult.postValue(BaseResponse.Error(ex.message))
            Log.d(TAG, "loadCoinHistoryCatch: $ex")
        }
        return null
    }

    override suspend fun addCoinToFav(coinModel: CoinModel) {
        favoriteDao.insertFavorite(coinMapper.mapEntityToDbModel(coinModel))
    }

    override suspend fun removeCoinFromFav(coinId: String) {
        favoriteDao.deleteFavorite(coinId)
    }

    companion object {
        private const val TAG = "CoinInfoRepositoryImpl"
    }
}