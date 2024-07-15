package com.jesse.contractai.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jesse.contractai.ui.screens.contract_nature.ContractNatureRoute
import com.jesse.contractai.ui.screens.create_contract.CreateContractRoute
import com.jesse.contractai.ui.screens.home.HomeRoute
import com.jesse.contractai.ui.screens.questions_screen.QuestionsRoute
import com.jesse.contractai.ui.screens.sign_in.SignInScreenRoute
import com.jesse.contractai.ui.shared.SharedViewModel

@Composable
fun ContractAINavHost(
    navHostController: NavHostController
) {

    val sharedViewModel: SharedViewModel = hiltViewModel()

    NavHost(navController = navHostController, startDestination = SignInDestination.route){
        composable(route = SignInDestination.route){
            SignInScreenRoute(
                onNavigateToForgotPassword = {},
                onNavigateToHomeScreen = {}
            )
        }
        composable(route = HomeDestination.route){
            HomeRoute(
                "Jesse",
                onNavigateToCreateContract = {navHostController.navigate(CreateContractDestination.route)}
            )
        }
        composable(route = CreateContractDestination.route){
            CreateContractRoute(
                onNavigateBack = {navHostController.navigateUp()},
                onContinue = {
                    contractType ->
                    sharedViewModel.contractType = contractType
                    navHostController.navigate(ContractNatureDestination.route)
                }
            )
        }
        composable(route = ContractNatureDestination.route){
            ContractNatureRoute(
                onNavigateBack = {navHostController.navigateUp()},
                onContinue = {
                    contractNature ->
                    sharedViewModel.natureOfContract = contractNature
                    navHostController.navigate(QuestionsDestination.route)
                }
            )
        }
        composable(
            route = QuestionsDestination.route
        ){
            QuestionsRoute(
                contractType = sharedViewModel.contractType,
                contractNature = sharedViewModel.natureOfContract,
                onNavigateToGenerateContract = {
                    answers ->
                    sharedViewModel.answers = answers
                }
            )
        }
    }
}