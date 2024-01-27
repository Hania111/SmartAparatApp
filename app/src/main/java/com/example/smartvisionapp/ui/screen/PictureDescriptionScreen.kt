package com.example.smartvisionapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartvisionapp.MainViewModel
import com.example.smartvisionapp.NavigationViewModel
//import java.lang.reflect.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PictureDescriptionScreen(navigationViewModel: NavigationViewModel){
    //val selectedPhoto by navigationViewModel.selectedPhoto.collectAsState()
    val selectedPhoto by navigationViewModel.selectedPhoto.collectAsState()
    val detectedObjects by navigationViewModel.detectedObjects.collectAsState()

    // Jeśli zdjęcie jest wybrane (nie jest null), wyświetl je
    selectedPhoto?.let { bitmap ->
        LaunchedEffect(bitmap) {
            navigationViewModel.detectObjectsInImage(bitmap)
        }
        val maxImageHeight = 0.6f
        Box(Modifier.fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(), // Konwertuj Bitmap na ImageBitmap
                contentDescription = "Selected Photo", // Opis dla dostępności
                contentScale = ContentScale.Fit, // Skalowanie zawartości obrazu
                modifier = Modifier
                    .align(Alignment.Center)
            )

            if (detectedObjects.isNotEmpty()) {
                // Tutaj możesz użyć na przykład LazyColumn, aby wyświetlić listę wykrytych obiektów
                LazyColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                ){
                    items(detectedObjects) { detectedObject ->
                        Text(text = detectedObject,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            textAlign = TextAlign.Center,
                                    modifier = Modifier
                                .align(Alignment.BottomCenter))

                                //.padding(horizontal = 16.dp))

                    }
                }
            }
        }
    }
}

