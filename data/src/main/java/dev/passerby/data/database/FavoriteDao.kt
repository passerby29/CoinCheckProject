package dev.passerby.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.passerby.data.models.db.CoinDbModel
import dev.passerby.data.models.db.FavoriteDbModel

@Dao
interface FavoriteDao {

    @Query("select * from favorites order by rank")
    fun getFavoritesList(): LiveData<List<FavoriteDbModel>>

    @Query("select count( * ) from favorites")
    fun getFavoritesCount(): Int

    @Query("delete from favorites where id = :coinId")
    suspend fun deleteFavorite(coinId: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(coinModel: FavoriteDbModel)
}