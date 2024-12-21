package com.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.repository.RepositoryMatakuliah
import com.ucp2.repository.RepositoryDosen


// Interface container app
interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen
    val repositoryMatakuliah: RepositoryMatakuliah
}
// Implementasi InterfaceContainerApp
class AppContainer(private val context: Context) : InterfaceContainerApp {

}

