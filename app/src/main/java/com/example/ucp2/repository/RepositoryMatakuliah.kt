package com.example.ucp2.repository

import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMatakuliah {
    fun getAllMatakuliah(): Flow<List<MataKuliah>>
    suspend fun insertMatakuliah(matakuliah: MataKuliah)
    fun getMatakuliah(kode: String): Flow<MataKuliah>
    suspend fun deleteMatakuliah(matakuliah: MataKuliah)
    suspend fun updateMatakuliah(matakuliah: MataKuliah)

}