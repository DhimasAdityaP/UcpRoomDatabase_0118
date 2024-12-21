package com.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.repository.LocalRepositoryDosen
import com.example.ucp2.repository.LocalRepositoryMatakuliah
import com.example.ucp2.repository.RepositoryMatakuliah
import com.pam.pam_ucp2.data.database.KrsDatabase
import com.ucp2.repository.RepositoryDosen


// Interface container app
interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen
    val repositoryMatakuliah: RepositoryMatakuliah
}
// Implementasi InterfaceContainerApp
class AppContainer(private val context: Context) : InterfaceContainerApp {
    // Inisialisasi RepositoryDosen menggunakan DAO yang sesuai
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoryDosen(KrsDatabase.getDatabase(context).dosenDao())
    }

    // Inisialisasi RepositoryMatakuliah menggunakan DAO yang sesuai
    override val repositoryMatakuliah: RepositoryMatakuliah by lazy {
        LocalRepositoryMatakuliah(KrsDatabase.getDatabase(context).matakuliahDao())
    }
}

