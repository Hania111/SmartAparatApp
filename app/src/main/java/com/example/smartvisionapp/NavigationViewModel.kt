package com.example.smartvisionapp

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class NavigationViewModel : ViewModel() {

    private val _selectedPhoto = MutableStateFlow<Bitmap?>(null)
    val selectedPhoto: StateFlow<Bitmap?> = _selectedPhoto.asStateFlow()

    private val _detectedObjects = MutableStateFlow<List<String>>(emptyList())
    val detectedObjects: StateFlow<List<String>> = _detectedObjects.asStateFlow()


    fun onEvent(event: UserInputEvents){
        when(event){
            is UserInputEvents.SelectedPhotoItem ->{
                _selectedPhoto.value = event.photoItem
            }
        }
    }

    /*fun detectObjectsInImage(bitmap: Bitmap) {
        // Przygotuj obraz do analizy
        val inputImage = InputImage.fromBitmap(bitmap, 0)

        // Skonfiguruj detektor obiektów
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification() // jeśli chcesz klasyfikować wykryte obiekty
            .build()
        val objectDetector = ObjectDetection.getClient(options)

        // Asynchronicznie wykrywaj obiekty na obrazie
        objectDetector.process(inputImage)
            .addOnSuccessListener { detectedObjects ->
                // Użyj wykrytych obiektów
                // Na przykład zaktualizuj stan ViewModelu, aby wyświetlić informacje o obiektach
            }
            .addOnFailureListener { e ->
                // Obsługa błędów
            }
    }*/
    fun detectObjectsInImage(bitmap: Bitmap) {
        val inputImage = InputImage.fromBitmap(bitmap, 0)

        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()
            .build()

        val objectDetector = ObjectDetection.getClient(options)

        objectDetector.process(inputImage)
            .addOnSuccessListener { detectedObjects ->
                // Przetwórz wyniki i zaktualizuj stan ViewModelu
                val objectNames = detectedObjects.mapNotNull { obj ->
                    // Dla uproszczenia, zakładamy, że interesuje nas tylko pierwsza etykieta
                    obj.labels.firstOrNull()?.text
                }
                viewModelScope.launch {
                    _detectedObjects.value = objectNames
                }
            }
            .addOnFailureListener { e ->
                // Możesz zalogować błąd lub zaktualizować stan, aby odzwierciedlić problem
                Log.e("MLKitError", "Error detecting objects", e)
                viewModelScope.launch {
                    _detectedObjects.value = listOf("Error detecting objects: ${e.message}")
                }
            }
    }
}

sealed class UserInputEvents{
    data class SelectedPhotoItem(val photoItem : Bitmap) : UserInputEvents()
}