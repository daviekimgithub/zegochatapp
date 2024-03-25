package com.chat.daviechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chat.chatapp.common.Constants
import com.chat.daviechat.common.Route
import com.chat.daviechat.screens.auth.login.LoginScreen
import com.chat.daviechat.screens.auth.login.LoginViewModel
import com.chat.daviechat.screens.conversations.ConversationScreen
import com.chat.daviechat.screens.conversations.ConversationViewModel
import com.chat.daviechat.ui.theme.DavieChatTheme
import com.zegocloud.zimkit.services.ZIMKit

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initZegocloud()
        setContent {
            val navController = rememberNavController()
            val loginViewModel = LoginViewModel()
            val conversationViewModel = ConversationViewModel()
            DavieChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.Auth.login
                    ) {
                        composable(Route.Auth.login) {
                            LoginScreen(
                                viewModel = loginViewModel,
                                navController = navController
                            )
                        }
                        composable(Route.Conversations.chatList) {
                            ConversationScreen(this@MainActivity, conversationViewModel)
                        }
                    }
                }
            }
        }
    }

    fun initZegocloud() {
        ZIMKit.initWith(this.application, Constants.appID, Constants.appSignature)
        ZIMKit.initNotifications()
    }
}