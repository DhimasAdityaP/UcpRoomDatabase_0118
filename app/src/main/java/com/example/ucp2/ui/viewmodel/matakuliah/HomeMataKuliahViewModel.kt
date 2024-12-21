package com.example.ucp2.ui.viewmodel.matakuliah

import com.example.ucp2.repository.RepositoryMatakuliah
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMatakuliahViewModel (
    private val repositoryMatakuliah: RepositoryMatakuliah
) : ViewModel() {

    val homeMatakuliahUiState: StateFlow<HomeMatakuliahUiState> = repositoryMatakuliah.getAllMatakuliah()
        .filterNotNull()
        .map {
            HomeMatakuliahUiState (
                listMtk = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeMatakuliahUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeMatakuliahUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeMatakuliahUiState(
                isLoading = true,
            )
        )
}

data class HomeMatakuliahUiState (
    val listMtk: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)