package com.example.ucp2.ui.viewmodel.dosen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.KrsApp
import com.ucp2.ui.viewmodel.dosen.DetailDosenViewModel
import com.ucp2.ui.viewmodel.dosen.DosenViewModel
import com.ucp2.ui.viewmodel.dosen.HomeDosenViewModel

object PenyediaDosenViewModel {

    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                KrsApp().AppContainer.repositoryDosen
            )
        }
        initializer {
            HomeDosenViewModel(
                KrsApp().AppContainer.repositoryDosen,
            )
        }
        initializer {
            DetailDosenViewModel(
                createSavedStateHandle(),
                KrsApp().AppContainer.repositoryDosen,
            )
        }
    }
}

fun CreationExtras.KrsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)

