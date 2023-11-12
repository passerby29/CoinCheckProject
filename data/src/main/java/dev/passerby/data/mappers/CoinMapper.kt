package dev.passerby.data.mappers

import dev.passerby.data.models.db.CoinDbModel
import dev.passerby.data.models.dto.info.CoinDto
import dev.passerby.domain.models.CoinModel
import dev.passerby.data.roundDouble

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
        priceChange1d = dto.priceChange1d,
        priceChange1h = dto.priceChange1h,
        priceChange1w = dto.priceChange1w,
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

    fun mapEntityToDbModel(entity: CoinModel) = CoinDbModel(
        availableSupply = entity.availableSupply,
        contractAddress = entity.contractAddress,
        decimals = entity.decimals,
        exp = entity.exp,
        icon = entity.icon,
        id = entity.id,
        marketCap = entity.marketCap,
        name = entity.name,
        price = entity.price,
        priceBtc = entity.priceBtc,
        priceChange1d = entity.priceChange1d,
        priceChange1h = entity.priceChange1h,
        priceChange1w = entity.priceChange1w,
        rank = entity.rank,
        redditUrl = entity.redditUrl,
        symbol = entity.symbol,
        totalSupply = entity.totalSupply,
        twitterUrl = entity.twitterUrl,
        volume = entity.volume,
        websiteUrl = entity.websiteUrl
    )
}