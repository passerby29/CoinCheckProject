package dev.passerby.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.passerby.data.models.db.CoinHistoryDbModel

@Dao
interface CoinHistoryDao {

    @Query("select * from coinsHistory where coinId = :coinId")
    fun getCoinHistory(coinId: String): LiveData<CoinHistoryDbModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinHistory(coinHistory: List<CoinHistoryDbModel>)
}