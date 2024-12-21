package com.example.ucp2.ui.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.ucp2.repository.RepositoryDosen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class HomeDosenViewModel(
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = repositoryDosen.getAllDosen()
        .filterNotNull() // Menghapus data null dari flow
        .map {
            HomeUiState(
                listDsn = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            // Emit state loading sebelum data mulai diterima
            emit(HomeUiState(isLoading = true))
            delay(900) // Simulasi delay
        }
        .catch {
            // Menangkap error dan mengupdate UI state dengan pesan error
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(isLoading = true)
        )
}

// Data class untuk merepresentasikan UI State
data class HomeUiState(
    val listDsn: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
