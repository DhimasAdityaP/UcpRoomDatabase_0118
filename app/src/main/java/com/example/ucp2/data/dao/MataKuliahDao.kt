package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {
    @Insert
    suspend fun insertMataKuliah(
        mataKuliah: MataKuliah
    )
    //getAllMahasiswa
    @Query("SELECT * FROM matakuliah ORDER BY nama ASC")
    fun getAllMataKuliah() : Flow<List<MataKuliah>>
    //getMahasiswa
    @Query("SELECT * FROM matakuliah WHERE kode = kode")
    fun getMataKuliah(kode: String): Flow<MataKuliah>
    //deleteMataKuliah
    @Delete
    suspend fun deleteMataKuliah (mataKuliah: MataKuliah)
    //updateMataKuliah
    @Update
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)
}
