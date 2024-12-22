package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMatakuliah
import com.ucp2.ui.navigation.DestinasiMatakuliahUpdate
import com.ucp2.ui.viewmodel.matakuliah.toDetailMatakuliahUiEvent
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMatakuliah
) : ViewModel() {

    var updateUIState by mutableStateOf(MatakuliahUIState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiMatakuliahUpdate.KODE])

    init {
        viewModelScope.launch {
            updateUIState = repositoryMatakuliah.getMatakuliah(_kode)
                .filterNotNull()
                .first()
                .toUiStateMatakuliah()
        }
    }

    fun updateState (matakuliahEvent: MatakuliahEvent) {
        updateUIState = updateUIState.copy(
            matakuliahEvent = matakuliahEvent,
        )
    }

    fun validateFields() : Boolean {
        val event = updateUIState.matakuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "Sks tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenpengampu = if (event.dosenpengampu.isNotEmpty()) null else "Dosen pengampu tidak boleh kosong",
        )

        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.matakuliahEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMatakuliah.updateMatakuliah(currentEvent.toMatakuliahEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        matakuliahEvent = MatakuliahEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.
                    snackBarMessage}")
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun MataKuliah.toUiStateMatakuliah () : MatakuliahUIState = MatakuliahUIState(
    matakuliahEvent = this.toDetailMatakuliahUiEvent(),
)
