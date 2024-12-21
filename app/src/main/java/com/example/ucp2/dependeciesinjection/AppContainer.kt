package com.ucp2.dependenciesinjection

import com.example.ucp2.repository.RepositoryMatakuliah
import com.ucp2.repository.RepositoryDosen


// Interface container app
interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen
    val repositoryMatakuliah: RepositoryMatakuliah
}
