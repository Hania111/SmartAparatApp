package com.example.smartvisionapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartvisionapp.NavigationViewModel


@Composable
fun SmartVisionAppNaviagationGraph(navigationViewModel: NavigationViewModel = viewModel())
{
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN){
        composable(Routes.HOME_SCREEN){
            HomeScreen(navController, navigationViewModel )
        }
        composable(Routes.PICTURE_DESCRIPTION_SCREEN){
            PictureDescriptionScreen(navigationViewModel )
        }
    }

}