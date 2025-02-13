package com.teclast_korea.payhere_entry.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teclast_korea.payhere_entry.data.data_source.local.db.selected_app.SelectedAppEntity
import com.teclast_korea.payhere_entry.data.repository.SelectedAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SelectedAppRepository
) : ViewModel() {

    // Collect the flow as a StateFlow with an initial null
    val selectedAppFlow: StateFlow<SelectedAppEntity?> = repository
        .observeSelectedApp()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun selectApp(packageName: String) {
        viewModelScope.launch {
            repository.setSelectedApp(packageName)
        }
    }

    fun clearSelectedApp() {
        viewModelScope.launch {
            repository.clearSelectedApp()
        }
    }
}