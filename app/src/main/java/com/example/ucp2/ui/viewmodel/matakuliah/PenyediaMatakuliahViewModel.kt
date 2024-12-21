package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.KrsApp
import com.ucp2.ui.viewmodel.matakuliah.DetailMatakuliahViewModel

object PenyediaMatakuliahViewModel {

    val Factory = viewModelFactory {
        initializer {
            MatakuliahViewModel(
                KrsApp().AppContainer.repositoryMatakuliah
            )
        }
        initializer {
            HomeMatakuliahViewModel(
                KrsApp().AppContainer.repositoryMatakuliah,
            )
        }
        initializer {
            DetailMatakuliahViewModel(
                createSavedStateHandle(),
                KrsApp().AppContainer.repositoryMatakuliah,
            )
        }
        initializer {
            UpdateMatakuliahViewModel(
                createSavedStateHandle(),
                KrsApp().AppContainer.repositoryMatakuliah,
            )
        }
    }
}

fun CreationExtras.KrsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)


