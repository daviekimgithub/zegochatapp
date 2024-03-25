package com.chat.daviechat.screens.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chat.daviechat.common.CustomButton
import com.chat.daviechat.common.CustomTextField
import com.chat.daviechat.common.Route

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val state = viewModel.loginState
    val focusManager = LocalFocusManager.current
    if (state.isConnectionSuccess){
        navController.navigate(Route.Conversations.chatList)
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(all = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Sign up new account",
                style = TextStyle(
                    fontSize = 32.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                value = state.userName,
                onChange = {
                    viewModel.onAction(LoginViewModel.Action.onChangeUserName(it))
                },
                hint = "John Smith",
                onDone = {},
                label = "User Name",
                fieldDescription = "",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                isValid = true,
                errorMessage = "User name is required",
                enabled = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                label = "Sign in",
                enabled = true,
                onClick = {
                    viewModel.onAction(LoginViewModel.Action.onLogin)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

//            ClickableText(
//                text = stringResource(R.string.already_have_an_account),
//                linkText = stringResource(R.string.sign_in),
//                onLinkClick = {},
//                arrangement = TextPosition.CENTER.position
//            )
        }
    }
}

enum class TextPosition(
    val position: Arrangement.Horizontal,
){
    SPACEAROUND(Arrangement.SpaceAround),
    CENTER(Arrangement.Center),
    START(Arrangement.Start),
    END(Arrangement.End)
}

@Composable
fun ClickableText(
    text: String,
    linkText: String,
    onLinkClick: () -> Unit,
    arrangement: Arrangement.Horizontal = Arrangement.Center
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = arrangement,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = linkText,
            color = Color.Blue,
            modifier = Modifier.clickable(onClick = onLinkClick),
            textDecoration = TextDecoration.Underline
        )
    }
}