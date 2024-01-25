@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.smartvisionapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smartvisionapp.ui.theme.SmartVisionAppTheme
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvisionapp.ui.screen.SmartVisionAppNaviagationGraph
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }
        setContent{
            SmartVisionAppp()
        }

    }

    @Composable
    fun SmartVisionAppp(){
        SmartVisionAppNaviagationGraph()
    }

        private fun hasRequiredPermissions(): Boolean {
            return CAMERAX_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }
        }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
        )
    }
}



