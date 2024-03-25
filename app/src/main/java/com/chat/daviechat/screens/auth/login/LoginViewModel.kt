package com.chat.daviechat.screens.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zegocloud.zimkit.services.ZIMKit
import im.zego.zim.enums.ZIMErrorCode

class LoginViewModel: ViewModel() {

    var loginState by mutableStateOf(LoginScreenState())

    fun onAction(action: Action){
        when(action){
            is Action.onChangeUserName -> {
                loginState = loginState.copy(
                    userName = action.userName
                )
            }
            is Action.onLogin -> {
                connectUser(
                    userId = loginState.userName,
                    userName = loginState.userName,
                    userAvatar = loginState.userAvatar
                )
            }
        }
    }

    fun connectUser(userId: String, userName: String, userAvatar: String){
        ZIMKit.connectUser(userId,userName,userAvatar)  { errorInfo ->
            Log.e("zego connection", errorInfo.toString())
            if (errorInfo.code == ZIMErrorCode.SUCCESS) {
                loginState = loginState.copy(
                    isConnectionSuccess = true
                )
            } else {
                loginState = loginState.copy(
                    errorMessage = "An error occurred"
                )
            }
        }
    }

    sealed interface Action{
        object onLogin: Action
        data class onChangeUserName(val userName: String): Action
    }
}