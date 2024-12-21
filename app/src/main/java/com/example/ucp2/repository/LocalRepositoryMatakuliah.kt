package com.example.ucp2.repository
import com.example.ucp2.data.dao.MatakuliahDao
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMatakuliah (
    private val matakuliahDao: MatakuliahDao
) : RepositoryMatakuliah {
    override fun getAllMatakuliah(): Flow<List<MataKuliah>> {
        return matakuliahDao.getAllMatakuliah()
    }
    override fun getMatakuliah(kode: String): Flow<MataKuliah> {
        return matakuliahDao.getMatakuliah(kode)
    }
    override suspend fun insertMatakuliah(matakuliah: MataKuliah) {
        matakuliahDao.insertMatakuliah(matakuliah)
    }
    override suspend fun deleteMatakuliah(matakuliah: MataKuliah) {
        matakuliahDao.deleteMatakuliah(matakuliah)
    }
    override suspend fun updateMatakuliah(matakuliah: MataKuliah) {
        matakuliahDao.updateMatakuliah(matakuliah)
    }
}