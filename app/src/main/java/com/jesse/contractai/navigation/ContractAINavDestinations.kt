package com.jesse.contractai.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.jesse.contractai.R

interface Destinations{
    val route: String
    @get:DrawableRes
    val icon: Int get() = R.drawable.ai_welcome_img
    @get:StringRes
    val iconDescription: Int get() = R.string.ai_welcome_image
}

object SignInDestination: Destinations{
    override val route: String
        get() = "/sign_in"
}

object SignUpDestination: Destinations{
    override val route: String
        get() = "/sign_up"
}

object ForgotPasswordDestination: Destinations{
    override val route: String
        get() = "/forgot_password"
}

object VerifyEmailDestination: Destinations{
    override val route: String
        get() = "/verify_email"
}

object HomeDestination: Destinations{
    override val route: String
        get() = "/home"
    override val icon: Int
        get() = R.drawable.ic_home
    override val iconDescription: Int
        get() = R.string.home
}

object CreateContractDestination: Destinations{
    override val route: String
        get() = "/create_contract"
}

object ContractNatureDestination: Destinations{
    override val route: String
        get() = "contract_nature"
    override val icon: Int
        get() = R.drawable.ic_add_file
    override val iconDescription: Int
        get() = R.string.create_a_contract
}


object QuestionsDestination: Destinations{
    override val route: String = "/question_destination"
}