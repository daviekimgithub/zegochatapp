package com.chat.daviechat.screens.conversations

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.chat.daviechat.screens.auth.login.LoginScreenState
import com.zegocloud.zimkit.common.ZIMKitRouter
import com.zegocloud.zimkit.common.enums.ZIMKitConversationType

class ConversationViewModel(): ViewModel() {

    var state by mutableStateOf(ConversationState())

    fun onAction(action: Action){
        when(action){
            is Action.showDialog -> {
                state = state.copy(
                    showDialog = action.show
                )
            }
            is Action.onStartChat -> {
                state = state.copy(
                    createNewChat = true,
                    showDialog = true
                )
            }
        }
    }

    sealed interface Action {
        object onStartChat: Action
        data class showDialog(val show: Boolean): Action
    }
}