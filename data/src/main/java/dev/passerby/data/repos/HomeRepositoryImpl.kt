package dev.passerby.data.repos

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import dev.passerby.data.database.AppDatabase
import dev.passerby.data.mappers.CoinHistoryMapper
import dev.passerby.data.mappers.CoinMapper
import dev.passerby.data.models.db.CoinDbModel
import dev.passerby.data.models.dto.history.CoinHistoryDto
import dev.passerby.data.models.dto.info.CoinsListDto
import dev.passerby.data.network.ApiFactory
import dev.passerby.data.network.BaseResponse
import dev.passerby.domain.models.CoinModel
import dev.passerby.domain.repos.HomeRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeRepositoryImpl(application: Application) : HomeRepository {

    private val db = AppDatabase.getInstance(application)
    private val coinDao = db.coinDao()
    private val favoriteDao = db.favoriteDao()
    private val coinHistoryDao = db.coinHistoryDao()
    private val apiService = ApiFactory.apiService
    private val coinMapper = CoinMapper()
    private val coinHistoryMapper = CoinHistoryMapper()
    private var coinsListResult: MutableLiveData<BaseResponse<CoinsListDto>> = MutableLiveData()
    private var coinHistoryResult: MutableLiveData<BaseResponse<CoinHistoryDto>> = MutableLiveData()

    override suspend fun getCoinsList(): LiveData<List<CoinModel>> {
        val coinsList = coinDao.getCoinsList()
        return getEntityList(coinsList)
    }

    override suspend fun getDate(): LiveData<String> {
        val dateLiveData = MutableLiveData("")
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN)
        dateLiveData.value = formatter.format(time)
        return dateLiveData
    }

    override suspend fun getFavCoinsList(): LiveData<List<CoinModel>> {
        val favoriteList = favoriteDao.getFavoritesList()
        return getEntityList(favoriteList)
    }

    override suspend fun getTopCoinsList(): LiveData<List<CoinModel>> {
        val coinsList = coinDao.getTopCoins()
        return getEntityList(coinsList)
    }

    override suspend fun loadCoinsHistory(rank: Int, coinId: String) {
        coinHistoryResult.value = BaseResponse.Loading()
        try {
            val response = apiService.loadCoinHistory(coinId, PERIOD)
            if (response.code() == 200) {
                coinHistoryResult.value = BaseResponse.Success(response.body())
                coinHistoryDao.insertCoinHistory(
                    coinHistoryMapper.mapDtoToDbModel(
                        rank, coinId, response.body()!!
                    )
                )
                Log.d(TAG, "loadCoinsHistoryTry: ${response.body()?.get(0)}")
            } else {
                coinHistoryResult.value = BaseResponse.Error(response.message())
                Log.d(TAG, "loadCoinsHistoryElse: ${response.message()}")
            }
        } catch (ex: Exception) {
            Log.d(TAG, "loadCoinsHistoryCatch: $ex")
            coinHistoryResult.value = BaseResponse.Error(ex.message)
        }
    }

    override suspend fun loadCoinsList() {
        coinsListResult.value = BaseResponse.Loading()
        try {
            val response = apiService.loadCoinsList()
            if (response.code() == 200) {
                coinsListResult.value = BaseResponse.Success(response.body())
                val dbModelList = response.body()?.coinsList?.map {
                    coinMapper.mapDtoToDbModel(it)
                }
                coinDao.insertCoin(dbModelList ?: emptyList())
                Log.d(TAG, "loadCoinsTry: ${response.isSuccessful}")
            } else {
                coinsListResult.value = BaseResponse.Error(response.message())
                Log.d(TAG, "loadCoinsElse: ${response.message()}")
            }
        } catch (ex: Exception) {
            Log.d(TAG, "loadCoinsCatch: $ex")
            coinsListResult.value = BaseResponse.Error(ex.message)
        }
    }

    override suspend fun searchCoins(coinFilter: String): LiveData<List<CoinModel>> {
        val coinsList = coinDao.searchCoins(coinFilter)
        return getEntityList(coinsList)
    }

    private fun getEntityList(coinsList: LiveData<List<CoinDbModel>>): LiveData<List<CoinModel>> {
        return coinsList.map { list ->
            list.map {
                coinMapper.mapDbModelToEntity(it)
            }
        }
    }

    companion object {
        private const val TAG = "HomeRepositoryImpl"
        private const val PERIOD = "1w"
    }
}