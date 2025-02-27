package com.subhranil.bluemoon.lite.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.subhranil.bluemoon.lite.navigation.MyNavHostController


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navHost = rememberNavController()
    MyNavHostController(
        navController = navHost
    )
}