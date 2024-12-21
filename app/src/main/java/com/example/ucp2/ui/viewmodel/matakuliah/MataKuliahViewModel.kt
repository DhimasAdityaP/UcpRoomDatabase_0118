package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMatakuliah
import kotlinx.coroutines.launch

class MatakuliahViewModel(private val repositoryMatakuliah: RepositoryMatakuliah): ViewModel() {
    var uiState by mutableStateOf(MatakuliahUIState())

    // memperbarui state berdasarkan input pengguna
    fun updateState(matakuliahEvent: MatakuliahEvent) {
        uiState = uiState.copy(
            matakuliahEvent = matakuliahEvent
        )
    }

    //validasi data inout pengguna
    private fun validateField(): Boolean {
        val event = uiState.matakuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "sks tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "jenis tidak boleh kosong",
            dosenpengampu = if (event.dosenpengampu.isNotEmpty()) null else "dosenpengampu tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    //menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.matakuliahEvent
        if(validateField()){
            viewModelScope.launch {
                try{
                    repositoryMatakuliah.insertMatakuliah(currentEvent.toMatakuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "data berhasil disimpan",
                        matakuliahEvent = MatakuliahEvent(),
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

data class MatakuliahUIState(
    val matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    val isEntryValid:FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)
data class FormErrorState(
    val kode:String? =null,
    val nama:String?= null,
    val sks:String?=null,
    val semester:String?=null,
    val jenis: String?=null,
    val dosenpengampu:String?=null
){
    fun isValid():Boolean{
        return kode ==null && nama ==null &&sks==null&&semester==null&&jenis==null&&dosenpengampu==null
    }
}

//menyimpan input form ke entity
fun MatakuliahEvent.toMatakuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenpengampu = dosenpengampu
)
//data class variable yang ,menyimpan data input form
data class MatakuliahEvent(
    val kode:String ="",
    val nama:String="",
    val sks:String="",
    val semester:String="",
    val jenis: String="",
    val dosenpengampu:String=""
