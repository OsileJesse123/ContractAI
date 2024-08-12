package com.jesse.contractai.util

enum class SignInMethod{
    GOOGLE,
    EMAIL_PASSWORD
}
enum class ConversationMessageStatus(val number: Int){
    SENDING(number = 1),
    SENT(number = 2),
    SEND_FAILED(number = 3)
}