package com.ucp2.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import com.example.ucp2.ui.viewmodel.matakuliah.FormErrorState
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahEvent
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahUIState
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.PenyediaMatakuliahViewModel
import com.ucp2.ui.costumwidget.TopAppBar
import com.ucp2.ui.navigation.AlamatNavigasi
import kotlinx.coroutines.launch


object DestinasiMatakuliahInsert : AlamatNavigasi {
    override val route = "matakuliahinsert"
}
@Composable
fun InsertMatakuliahView(
    onBack:()->Unit,
    onNavigate:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: MatakuliahViewModel = viewModel(factory = PenyediaMatakuliahViewModel.Factory)
){
    val uiState = viewModel.uiState
    val snackbarHostState = remember{ SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold (
        modifier=modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Matakuliah",
                modifier = Modifier
            )
            InsertBodyMatakuliah(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMatakuliah(
    modifier: Modifier = Modifier,
    onValueChange:(MatakuliahEvent)->Unit,
    onClick:() -> Unit,
    uiState: MatakuliahUIState
){
    Column (
        modifier= modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatakuliah(
            matakuliahEvent = uiState.matakuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FormMatakuliah(
    matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    onValueChange:(MatakuliahEvent)->Unit={},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
){
    val sks = listOf("1","2","3","4","5","6")
    val jenis = listOf("Wajib","Peminatan")

    Column (
        modifier = modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value =matakuliahEvent.kode,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(kode = it))
            },
            label= { Text("Kode") },
            isError = errorState.kode !=null,
            placeholder = { Text("Masukkan Kode") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.kode ?:"",
            color = Color.Red
        )
        Column (
            modifier = modifier.fillMaxWidth()
        ){
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value =matakuliahEvent.nama,
                onValueChange = {
                    onValueChange(matakuliahEvent.copy(nama = it))
                },
                label= { Text("Nama") },
                isError = errorState.nama !=null,
                placeholder = { Text("Masukkan Nama") },
            )
            Text(
                text = errorState.nama ?:"",
                color = Color.Red
            )
            Spacer(modifier= Modifier.height(16.dp))
            Text(text ="SKS")
            Row(
                modifier= Modifier.fillMaxWidth()

            ){
                sks.forEach{sks ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        RadioButton(
                            selected = matakuliahEvent.sks == sks,
                            onClick = {
                                onValueChange(matakuliahEvent.copy(sks = sks))
                            },
                        )
                        Text(
                            text = sks,
                        )
                    }
                }
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value =matakuliahEvent.semester,
                onValueChange = {
                    onValueChange(matakuliahEvent.copy(semester = it))
                },
                label= { Text("Semester") },
                isError = errorState.semester !=null,
                placeholder = { Text("Masukkan Semester") },
            )
            Text(
                text = errorState.semester ?:"",
                color = Color.Red
            )
            Spacer(modifier= Modifier.height(16.dp))
            Text(text ="Jenis")
            Row(
                modifier= Modifier.fillMaxWidth()

            ){
                jenis.forEach{jenis ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        RadioButton(
                            selected = matakuliahEvent.jenis  == jenis,
                            onClick = {
                                onValueChange(matakuliahEvent.copy(jenis = jenis))
                            },
                        )
                        Text(
                            text = jenis,
                        )
                    }
                }
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value =matakuliahEvent.dosenpengampu,
                onValueChange = {
                    onValueChange(matakuliahEvent.copy(dosenpengampu = it))
                },
                label= { Text("DosenPengampu") },
                isError = errorState.dosenpengampu !=null,
                placeholder = { Text("Masukkan DosenPengampu") },

                )
            Text(
                text = errorState.dosenpengampu ?:"",
                color = Color.Red
            )
        }
    }
}