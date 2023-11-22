package dev.passerby.data.workers

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import dev.passerby.data.database.AppDatabase
import dev.passerby.data.mappers.CoinHistoryMapper
import dev.passerby.data.models.dto.history.CoinHistoryDto
import dev.passerby.data.network.ApiFactory
import dev.passerby.data.network.BaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RefreshHistoryWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val coinHistoryDao = AppDatabase.getInstance(context).coinHistoryDao()
    private val coinDao = AppDatabase.getInstance(context).coinDao()
    private val apiService = ApiFactory.apiService
    private val coinHistoryMapper = CoinHistoryMapper()
    private var coinHistoryResult: MutableLiveData<BaseResponse<CoinHistoryDto>> = MutableLiveData()

    override suspend fun doWork(): Result {
        while (true) {
            val coinsList = coinDao.getCoinsListWorker()
            CoroutineScope(Dispatchers.IO).launch {
                coinsList.forEach { item ->
                    coinHistoryResult.postValue(BaseResponse.Loading())
                    try {
                        val response = apiService.loadCoinHistory(item.id, PERIOD)
                        if (response.code() == 200) {
                            coinHistoryResult.postValue(BaseResponse.Success(response.body()))
                            coinHistoryDao.insertCoinHistory(
                                coinHistoryMapper.mapDtoToDbModel(
                                    item.rank, item.id, response.body()!!
                                )
                            )
                            Log.d(TAG, "doWorkTry: ${item.name} ${response.body()?.get(0)}")
                        } else {
                            coinHistoryResult.postValue(BaseResponse.Error(response.message()))
                            Log.d(TAG, "doWorkElse: ${response.message()}")
                        }
                    } catch (ex: Exception) {
                        Log.d(TAG, "doWorkCatch: $ex")
                        coinHistoryResult.postValue(BaseResponse.Error(ex.message))
                    }
                }
            }
            delay(600000)
        }
    }

    companion object {
        const val TAG = "RefreshHistoryWorker"
        private const val PERIOD = "24h"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshHistoryWorker>().build()
        }
    }
}