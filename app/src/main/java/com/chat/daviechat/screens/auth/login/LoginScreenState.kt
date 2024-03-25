package com.chat.daviechat.screens.auth.login

data class LoginScreenState(
    val isLoading: Boolean = false,
    val isConnectionSuccess: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "",
    val userAvatar: String = ""
)
