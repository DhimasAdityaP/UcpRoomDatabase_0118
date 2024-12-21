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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.customwidget.DynamicSelectedTextField
import com.example.ucp2.ui.viewmodel.dosen.PenyediaDosenViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.FormErrorState
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahEvent
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahUIState
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.PenyediaMatakuliahViewModel
import com.ucp2.ui.costumwidget.TopAppBar
import com.ucp2.ui.navigation.AlamatNavigasi
import com.ucp2.ui.viewmodel.dosen.HomeDosenViewModel
import com.ucp2.ui.viewmodel.dosen.HomeUiState
import kotlinx.coroutines.launch


object DestinasiMatakuliahInsert : AlamatNavigasi {
    override val route = "matakuliahinsert"
}
@Composable
fun InsertMatakuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatakuliahViewModel = viewModel(factory = PenyediaMatakuliahViewModel.Factory),
    viewModelDosen: HomeDosenViewModel = viewModel(factory = PenyediaDosenViewModel.Factory),
) {
    val dosenList by viewModelDosen.homeUiState.collectAsState()
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Show snackbar on message
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Matakuliah"
            )
            InsertBodyMatakuliah(
                uiState = uiState,
                listDosen = dosenList,
                onValueChange = viewModel::updateState,
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
    uiState: MatakuliahUIState,
    listDosen: HomeUiState,
    onValueChange: (MatakuliahEvent) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatakuliah(
            matakuliahEvent = uiState.matakuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            listDosen = listDosen.listDsn,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormMatakuliah(
    matakuliahEvent: MatakuliahEvent,
    onValueChange: (MatakuliahEvent) -> Unit,
    errorState: FormErrorState,
    listDosen: List<Dosen>,
    modifier: Modifier = Modifier
) {
    val sksOptions = listOf("1", "2", "3", "4", "5", "6")
    val jenisOptions = listOf("Wajib", "Peminatan")
    val namaDosenList = listDosen.map { it.nama }
    var selectedDosen by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        InputField(
            value = matakuliahEvent.kode,
            onValueChange = { onValueChange(matakuliahEvent.copy(kode = it)) },
            label = "Kode",
            isError = errorState.kode != null,
            errorText = errorState.kode,
            keyboardType = KeyboardType.Number
        )
        InputField(
            value = matakuliahEvent.nama,
            onValueChange = { onValueChange(matakuliahEvent.copy(nama = it)) },
            label = "Nama",
            isError = errorState.nama != null,
            errorText = errorState.nama
        )
        Spacer(modifier = Modifier.height(16.dp))

        RadioGroup(
            label = "SKS",
            options = sksOptions,
            selectedOption = matakuliahEvent.sks,
            onOptionSelected = { onValueChange(matakuliahEvent.copy(sks = it)) }
        )
        InputField(
            value = matakuliahEvent.semester,
            onValueChange = { onValueChange(matakuliahEvent.copy(semester = it)) },
            label = "Semester",
            isError = errorState.semester != null,
            errorText = errorState.semester
        )
        Spacer(modifier = Modifier.height(16.dp))

        RadioGroup(
            label = "Jenis",
            options = jenisOptions,
            selectedOption = matakuliahEvent.jenis,
            onOptionSelected = { onValueChange(matakuliahEvent.copy(jenis = it)) }
        )
        DynamicSelectedTextField(
            selectedValue = selectedDosen,
            options = namaDosenList,
            label = "Dosen Pengampu",
            onValueChangedEvent = {
                selectedDosen = it
                onValueChange(matakuliahEvent.copy(dosenpengampu = it))
            }
        )
        Text(
            text = errorState.dosenpengampu ?: "",
            color = Color.Red
        )
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    errorText: String?,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = isError,
        placeholder = { Text("Masukkan $label") },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.fillMaxWidth()
    )
    if (isError) {
        Text(
            text = errorText ?: "",
            color = Color.Red
        )
    }
}

@Composable
fun RadioGroup(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(text = label)
    Row(modifier = modifier.fillMaxWidth()) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) }
                )
                Text(text = option)
            }
        }
    }
}
