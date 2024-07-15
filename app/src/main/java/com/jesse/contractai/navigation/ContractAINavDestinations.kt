package com.jesse.contractai.navigation

interface Destinations{
    val route: String
}

object SignInDestination: Destinations{
    override val route: String
        get() = "/sign_in"
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