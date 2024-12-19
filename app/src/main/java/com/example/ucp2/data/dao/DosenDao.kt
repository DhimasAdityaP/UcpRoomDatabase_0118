package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {
    @Query("SELECT * FROM dosen")
    fun getAllDosen(): Flow<List<Dosen>>
    //getDosen
    @Query("SELECT * FROM dosen WHERE nidn = :nidn")
    fun getDosen(nidn: String): Flow<Dosen>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDosen(dosen: Dosen)

}
