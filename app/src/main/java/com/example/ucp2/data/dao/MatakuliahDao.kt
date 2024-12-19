package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MatakuliahDao {
    @Query("SELECT * FROM matakuliah ORDER BY nama ASC")
    fun getAllMatakuliah(): Flow<List<MataKuliah>>
    @Insert
    suspend fun insertMatakuliah(
        matakuliah: MataKuliah
    )
    @Query("SELECT * FROM matakuliah WHERE kode = :kode")
    fun getMatakuliah(kode: String): Flow<MataKuliah>
    @Delete
    suspend fun deleteMatakuliah(
        matakuliah: MataKuliah
    )
    @Update
    suspend fun updateMatakuliah(
        matakuliah: MataKuliah
    )
}