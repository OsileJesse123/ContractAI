package com.jesse.contractai.ui.screens.conversation

sealed class SenderType(val number: Int){
    class AIModel(number:Int = 1): SenderType(number)
    class User(number: Int = 2, val userName: String): SenderType(number)
}