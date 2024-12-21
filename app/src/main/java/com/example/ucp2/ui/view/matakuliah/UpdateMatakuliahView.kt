package com.ucp2.ui.view.matakuliah

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucp2.ui.costumwidget.TopAppBar
import com.ucp2.ui.viewmodel.dosen.HomeDosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.PenyediaDosenViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahEvent
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahUIState
import com.example.ucp2.ui.viewmodel.matakuliah.PenyediaMatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.UpdateMatakuliahViewModel
import com.ucp2.ui.viewmodel.dosen.HomeUiState
import kotlinx.coroutines.launch

@Composable
fun UpdateMatakuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMatakuliahViewModel = viewModel(factory = PenyediaMatakuliahViewModel.Factory),
    viewModelDsn: HomeDosenViewModel = viewModel(factory = PenyediaDosenViewModel.Factory),
) {
    val uiState = viewModel.updateUIState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val DosenList by viewModelDsn.homeUiState.collectAsState()

    // SnackBar Handling
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                judul = "Edit Matakuliah",
                showBackButton = true,
                onBack = onBack,
                modifier = Modifier
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            UpdateBodyMatakuliah(
                uiState = uiState,
                listdosen = DosenList,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            onNavigate()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun UpdateBodyMatakuliah(
    modifier: Modifier = Modifier,
    onValueChange: (MatakuliahEvent) -> Unit,
    onClick: () -> Unit,
    uiState: MatakuliahUIState,
    listdosen: HomeUiState
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        FormMatakuliah(
            matakuliahEvent = uiState.matakuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.padding(vertical = 8.dp),
            listDosen = listdosen.listDsn
        )
        androidx.compose.material3.Button(
            onClick = onClick,
            modifier = Modifier.fillMaxSize().padding(top = 16.dp)
        ) {
            androidx.compose.material3.Text("Update Data")
        }
    }
}
