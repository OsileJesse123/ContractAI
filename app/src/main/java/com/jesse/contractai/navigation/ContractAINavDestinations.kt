package com.jesse.contractai.navigation

interface Destinations{
    val route: String
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
}

object CreateContractDestination: Destinations{
    override val route: String
        get() = "/create_contract"
}

object ContractNatureDestination: Destinations{
    override val route: String
        get() = "contract_nature"
}

object QuestionsDestination: Destinations{
    override val route: String = "/question_destination"
}