package com.jesse.contractai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jesse.contractai.navigation.ContractAINavHost
import com.jesse.contractai.navigation.ContractNatureDestination
import com.jesse.contractai.navigation.HomeDestination
import com.jesse.contractai.navigation.SignInDestination
import com.jesse.contractai.ui.components.ContractAITabRow
import com.jesse.contractai.ui.screens.splash.SplashScreen
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.Dimens
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
            val navController = rememberNavController()
            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStack?.destination
            val contractAIBottomNavScreens = listOf(
                HomeDestination,
                ContractNatureDestination,
                ContractNatureDestination,
                ContractNatureDestination
            )
            val currentScreen = contractAIBottomNavScreens.find { it.route == currentDestination?.route } ?: SignInDestination
            var bottomBarState by rememberSaveable{
                mutableStateOf(false)
            }
            Scaffold(
                bottomBar = {
                    AnimatedVisibility(
                        visible = bottomBarState,
                        enter = slideInHorizontally(
                            animationSpec = tween(300, easing = LinearEasing),
                            initialOffsetX = {
                                -it
                            }
                        ),
                        exit = slideOutHorizontally(
                            animationSpec = tween(300, easing = LinearEasing),
                            targetOffsetX = {-it}
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = Dimens.screenHorizontalPadding)
                        ) {
                            ContractAITabRow(
                                allScreens = contractAIBottomNavScreens,
                                onTabSelected = {},
                                currentScreen = currentScreen
                            )
                            Spacer(modifier = Modifier.height(height = 30.dp))
                        }
                    }
                }
            ) {
                padding ->
                ContractAINavHost(
                    navHostController = navController,
                    modifier = Modifier.padding(padding),
                    updateBottomBarState = {
                    showBottomBar ->
                    bottomBarState = showBottomBar
                })
            }
        }
    }

}