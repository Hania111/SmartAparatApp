package com.example.smartvisionapp

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class NavigationViewModel : ViewModel() {
    private val _selectedBitmap = MutableStateFlow<Bitmap?>(null)
    val selectedBitmap: StateFlow<Bitmap?> = _selectedBitmap.asStateFlow()

    private val _selectedPhoto = MutableStateFlow<Bitmap?>(null)
    val selectedPhoto: StateFlow<Bitmap?> = _selectedPhoto.asStateFlow()

    // Funkcja do ustawiania wybranego zdjęcia.
    fun onSelectPhoto(bitmap: Bitmap) {
        _selectedBitmap.value = bitmap
    }

    // Funkcja do czyszczenia wybranego zdjęcia.
    fun onClearSelection() {
        _selectedBitmap.value = null
    }

    fun onEvent(event: UserInputEvents){
        when(event){
            is UserInputEvents.SelectedPhotoItem ->{
                _selectedPhoto.value = event.photoItem
            }
        }
    }
}

sealed class UserInputEvents{
    data class SelectedPhotoItem(val photoItem : Bitmap) : UserInputEvents()
}