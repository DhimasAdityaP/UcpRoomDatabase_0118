package com.ucp2.ui.viewmodel.dosen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.ucp2.repository.RepositoryDosen
import kotlinx.coroutines.launch

class DosenViewModel(private val repositoryDosen: RepositoryDosen) : ViewModel() {
    var uiState by mutableStateOf(DosenUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateState(dosenEvent: DosenEvent) {
        uiState = uiState.copy(
            dosenEvent = dosenEvent
        )
    }

    // Validasi data input pengguna
    private fun validateField(): Boolean {
        val event = uiState.dosenEvent
        val errorState = FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jeniskelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis kelamin tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    //menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.dosenEvent
        if(validateField()){
            viewModelScope.launch {
                try{
                    repositoryDosen.insertDosen(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "data berhasil disimpan",
                        dosenEvent = DosenEvent(),
                        isEntryValid = FormErrorState()
                    )
                }catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "data gagal disimpan"
                    )
                }
            }
        }else{
            uiState=uiState.copy(
                snackBarMessage = "Input tidak valid periksa kembali data anda"
            )
        }
    }
    //reset pesan snackbar
    fun resetSnackBarMessage(){
        uiState=uiState.copy(snackBarMessage = null)
    }
}

data class DosenUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid:FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)
data class FormErrorState(
    val nidn:String? =null,
    val nama:String?= null,
    val jeniskelamin:String?=null
){
    fun isValid():Boolean{
        return nidn ==null && nama ==null &&jeniskelamin==null
    }
}

//menyimpan input form ke entity
fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jeniskelamin = jenisKelamin
)
//data class variable yang ,menyimpan data input form
data class DosenEvent(
    val nidn:String ="",
    val nama:String="",
    val jenisKelamin:String=""
)