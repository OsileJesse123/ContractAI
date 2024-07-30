package com.jesse.contractai.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jesse.contractai.ui.screens.contract_history.ContractHistoryRoute
import com.jesse.contractai.ui.screens.contract_nature.ContractNatureRoute
import com.jesse.contractai.ui.screens.create_contract.CreateContractRoute
import com.jesse.contractai.ui.screens.forgot_password.ForgotPasswordRoute
import com.jesse.contractai.ui.screens.home.HomeRoute
import com.jesse.contractai.ui.screens.questions_screen.QuestionsRoute
import com.jesse.contractai.ui.screens.review_contract.ReviewContractRoute
import com.jesse.contractai.ui.screens.sign_in.SignInScreenRoute
import com.jesse.contractai.ui.screens.sign_up.SignUpScreen
import com.jesse.contractai.ui.screens.sign_up.SignUpScreenRoute
import com.jesse.contractai.ui.screens.verify_email.VerifyEmailRoute
import com.jesse.contractai.ui.shared.SharedViewModel

@Composable
fun ContractAINavHost(
    modifier: Modifier = Modifier,
    startDestination: Destinations,
    updateBottomBarState: (Boolean) -> Unit,
    navHostController: NavHostController
) {

    val sharedViewModel: SharedViewModel = hiltViewModel()

    NavHost(navController = navHostController, startDestination = startDestination.route){
        composable(route = SignInDestination.route){
            SignInScreenRoute(
                onNavigateToForgotPassword = {
                    navHostController.navigate(ForgotPasswordDestination.route)
                },
                onNavigateToHome = {
                    navHostController.navigate(HomeDestination.route){
                        popUpTo(SignInDestination.route){inclusive = true}
                    }
                },
                onNavigateToSignUp = {
                    navHostController.navigate(SignUpDestination.route){
                        popUpTo(SignInDestination.route){inclusive = true}
                    }
                },
                onNavigateToVerifyEmail = {
                    navHostController.navigate(VerifyEmailDestination.route)
                }
            )
        }
        composable(route = SignUpDestination.route){
            SignUpScreenRoute(
                onNavigateToSignIn = {
                    navHostController.navigate(SignInDestination.route){
                        popUpTo(SignUpDestination.route){inclusive = true}
                    }
                },
                onNavigateToVerifyEmail = {
                    navHostController.navigate(VerifyEmailDestination.route)
                }
            )
        }
        composable(route = ForgotPasswordDestination.route){
            ForgotPasswordRoute(
                onNavigateBack = {
                    navHostController.navigateUp()
                }
            )
        }
        composable(route = VerifyEmailDestination.route){
            VerifyEmailRoute(
                onNavigateToNextScreen = {
                       navHostController.navigate(SignInDestination.route) {
                           popUpTo(0){inclusive = true}
                       }
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                }
            )
        }
        composable(route = HomeDestination.route){
            HomeRoute(
                onNavigateToContractHistory = {
                    navHostController.navigate(ContractHistoryDestination.route)
                },
                onNavigateToLoginScreen = {
                    navHostController.navigate(SignInDestination.route){
                        popUpTo(HomeDestination.route){ inclusive = true }
                    }
                },
                updateBottomBarState = updateBottomBarState
            )
        }
        composable(route = ContractHistoryDestination.route){
            ContractHistoryRoute(
                onNavigateBack = {
                    navHostController.navigateUp()
                }
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
        composable(route = ReviewContractDestination.route){
            ReviewContractRoute(
                onNavigateBack = {navHostController.navigateUp()}
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