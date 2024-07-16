package com.jesse.contractai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jesse.contractai.navigation.ContractAINavHost
import com.jesse.contractai.ui.screens.splash.SplashScreen
import com.jesse.contractai.ui.theme.ContractAITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContractAITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    var showSplashScreen by remember { mutableStateOf(true) }
    AnimatedContent(
        targetState = showSplashScreen,
        label = "splash screen navigation animation"
    ) {
        targetState ->
        if(targetState){
            SplashScreen {
                showSplashScreen = false
            }
        } else {
            ContractAINavHost(navHostController = rememberNavController())
        }
    }


}