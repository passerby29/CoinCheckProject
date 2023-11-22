package dev.passerby.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.passerby.data.models.db.CoinDbModel

@Dao
interface CoinDao {

    @Query("select * from coins where id = :coinId")
    fun getCoinInfo(coinId: String): LiveData<CoinDbModel>

    @Query("select * from coins")
    fun getCoinsList(): LiveData<List<CoinDbModel>>

    @Query("select * from coins")
    fun getCoinsListWorker(): List<CoinDbModel>

    @Query("select * from coins limit 5")
    fun getTopCoins(): LiveData<List<CoinDbModel>>

    @Query("select * from coins where id like :filter order by rank")
    fun searchCoins(filter: String): LiveData<List<CoinDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coinsList: List<CoinDbModel>)
}