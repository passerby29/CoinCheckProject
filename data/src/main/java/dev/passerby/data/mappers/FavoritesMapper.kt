package dev.passerby.data.mappers

import dev.passerby.data.models.db.FavoriteDbModel
import dev.passerby.domain.models.FavoriteModel

class FavoritesMapper {

    fun mapDbModelToEntity(dbModel: FavoriteDbModel) = FavoriteModel(
        icon = dbModel.icon,
        id = dbModel.id,
        name = dbModel.name,
        price = dbModel.price,
        priceChange1h = dbModel.priceChange1h,
        rank = dbModel.rank,
        symbol = dbModel.symbol
    )

    fun mapEntityToDbModel(entity: FavoriteModel) = FavoriteDbModel(
        icon = entity.icon,
        id = entity.id,
        name = entity.name,
        price = entity.price,
        priceChange1h = entity.priceChange1h,
        rank = entity.rank,
        symbol = entity.symbol
    )
}