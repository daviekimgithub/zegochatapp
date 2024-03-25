package com.chat.daviechat.screens.conversations

import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.chat.daviechat.R

@Composable
fun ZIMKitConversationScreen() {
    AndroidView(
        factory = { context ->
            // Inflate the layout XML
            LayoutInflater.from(context).inflate(R.layout.conversation_list, null)
        },
        update = { view ->
            // Update the view if needed
            // For example, you can access views and set properties here
        }
    )
}
