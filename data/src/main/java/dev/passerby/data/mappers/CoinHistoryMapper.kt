package dev.passerby.data.mappers

import dev.passerby.data.models.db.CoinHistoryDbModel
import dev.passerby.data.models.dto.history.CoinHistoryDto
import dev.passerby.data.models.dto.history.CoinHistoryListDto
import dev.passerby.data.roundDouble
import dev.passerby.domain.models.CoinHistoryModel

class CoinHistoryMapper {

    fun mapDtoToDbModel(rank: Int, coinId: String, dto: CoinHistoryDto) = CoinHistoryDbModel(
        id = rank,
        coinId = coinId,
        prices = getListOfPrices(dto)
    )

    fun mapDbModelToEntity(dbModel: CoinHistoryDbModel) = CoinHistoryModel(
        id = dbModel.id,
        coinId = dbModel.coinId,
        prices = dbModel.prices
    )

    fun mapDtoToEntity(coinId: String, dto: CoinHistoryDto) = CoinHistoryModel(
        coinId = coinId,
        prices = getListOfPrices(dto)
    )

    private fun getListOfPrices(coinHistory: CoinHistoryDto): List<Double> {
        val sublist = arrayListOf<CoinHistoryListDto>()
        val list = arrayListOf<Double>()
        coinHistory.forEach {
            sublist.add(it)
        }
        sublist.forEach {
            list.add(roundDouble(it[1]))
        }
        return list.toList()
    }
}