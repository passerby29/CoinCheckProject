package dev.passerby.data.mappers

import dev.passerby.data.models.db.CoinDbModel
import dev.passerby.data.models.dto.info.CoinDto
import dev.passerby.data.roundDouble
import dev.passerby.domain.models.CoinModel

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinDto) = CoinDbModel(
        availableSupply = dto.availableSupply,
        contractAddress = dto.contractAddress,
        decimals = dto.decimals,
        exp = dto.explorers,
        icon = dto.icon,
        id = dto.id,
        marketCap = dto.marketCap,
        name = dto.name,
        price = roundDouble(dto.price),
        priceBtc = dto.priceBtc,
        priceChange1d = roundDouble(dto.priceChange1d),
        priceChange1h = roundDouble(dto.priceChange1h),
        priceChange1w = roundDouble(dto.priceChange1w),
        rank = dto.rank,
        redditUrl = dto.redditUrl,
        symbol = dto.symbol,
        totalSupply = dto.totalSupply,
        twitterUrl = dto.twitterUrl,
        volume = dto.volume,
        websiteUrl = dto.websiteUrl,
    )

    fun mapDbModelToEntity(dbModel: CoinDbModel) = CoinModel(
        availableSupply = dbModel.availableSupply,
        contractAddress = dbModel.contractAddress,
        decimals = dbModel.decimals,
        exp = dbModel.exp,
        icon = dbModel.icon,
        id = dbModel.id,
        marketCap = dbModel.marketCap,
        name = dbModel.name,
        price = dbModel.price,
        priceBtc = dbModel.priceBtc,
        priceChange1d = dbModel.priceChange1d,
        priceChange1h = dbModel.priceChange1h,
        priceChange1w = dbModel.priceChange1w,
        rank = dbModel.rank,
        redditUrl = dbModel.redditUrl,
        symbol = dbModel.symbol,
        totalSupply = dbModel.totalSupply,
        twitterUrl = dbModel.twitterUrl,
        volume = dbModel.volume,
        websiteUrl = dbModel.websiteUrl,
    )

}