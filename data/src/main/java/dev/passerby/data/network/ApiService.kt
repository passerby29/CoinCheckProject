package dev.passerby.data.network

import dev.passerby.data.models.dto.history.CoinHistoryDto
import dev.passerby.data.models.dto.info.CoinsListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers(
        "Accept: application/json",
        "X-API-KEY: xU9LlWkpwxmAIexrMCuRxx3a7qFeqUEc71R5PnU5j8o="
    )
    @GET("coins")
    suspend fun loadCoinsList(
        @Query(QUERY_PARAM_LIMIT) limit: Int = 50,
        @Query(QUERY_PARAM_CURRENCY) currency: String = CURRENCY
    ): Response<CoinsListDto>

    @Headers(
        "Accept: application/json",
        "X-API-KEY: xU9LlWkpwxmAIexrMCuRxx3a7qFeqUEc71R5PnU5j8o="
    )
    @GET("coins/{coinId}/charts")
    suspend fun loadCoinHistory(
        @Path("coinId") coinId: String,
        @Query(QUERY_PARAM_PERIOD) period: String
    ): Response<CoinHistoryDto>

    companion object {
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_CURRENCY = "currency"
        private const val QUERY_PARAM_PERIOD = "period"
        private const val CURRENCY = "USD"
    }
}