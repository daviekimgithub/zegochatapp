package com.chat.daviechat.screens.conversations

data class ConversationState(
    val loading: Boolean = false,
    val createNewChat: Boolean = false,
    val showDialog: Boolean = false
)