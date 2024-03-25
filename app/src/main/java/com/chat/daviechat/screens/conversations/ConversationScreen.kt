package com.chat.daviechat.screens.conversations

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import com.chat.daviechat.R
import com.chat.daviechat.screens.auth.login.LoginViewModel
import com.chat.daviechat.ui.theme.green70
import com.zegocloud.zimkit.common.ZIMKitRouter
import com.zegocloud.zimkit.common.enums.ZIMKitConversationType
import com.zegocloud.zimkit.components.conversation.ui.ZIMKitConversationFragment
import com.zegocloud.zimkit.services.ZIMKit
import im.zego.zim.enums.ZIMErrorCode

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConversationScreen(
    context: Context,
    viewModel: ConversationViewModel
) {
    val state = viewModel.state
    if (state.createNewChat){
        makeToast(context, viewModel.state.showDialog.toString())
        TextInputDialog(
            showDialog = state.showDialog,
            onDismiss = {
                viewModel.onAction(ConversationViewModel.Action.showDialog(false))
            },
            onConfirm = { enteredText ->
                makeToast(context, enteredText)
                startSingleChat(enteredText, context)
            }
        )
    }
    Scaffold(
        topBar = {
            Text("Chat List")
        },
        content = {
//            IncludeActivityLayout()
            ZIMKitConversationFragmentWrapper(context)
        },
        floatingActionButton = { FloatingActionButtonCompose(context, viewModel) },
        floatingActionButtonPosition = FabPosition.End
    )
}
//
//@Composable
//fun ZIMKitConversationFragmentWrapper(context: Context) {
//    val fragment = remember { ZIMKitConversationFragment() }
//
//    DisposableEffect(Unit) {
//        // Fragment initialization
//        val fragmentManager = (context as FragmentActivity).supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        transaction.add(fragment, null)
//        transaction.commit()
//
//        onDispose {
//            // Fragment cleanup
//            fragmentManager.beginTransaction().remove(fragment).commit()
//        }
//    }
//
//    AndroidView(factory = { context ->
//        fragment.requireView()
//    })
//}

@Composable
fun ZIMKitConversationFragmentWrapper(context: Context) {
    val fragmentManager = (context as FragmentActivity).supportFragmentManager

    // Remember the fragment instance
    val fragment = remember {
        ZIMKitConversationFragment().apply {
            // Any additional setup if needed
        }
    }

    // Initialize the fragment when the composition is first created
    DisposableEffect(Unit) {
        // Ensure that the fragment is created only once
        val transaction = fragmentManager.beginTransaction()
        transaction.add(fragment, null)
        transaction.commit()

        // Handle cleanup
        onDispose {
            fragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    // Make sure to wait until the fragment's view is created
    AndroidView(factory = { context ->
        fragment.requireView()
    })
}


@Composable
fun IncludeActivityLayout() {
    AndroidView(
        factory = { context ->
            LayoutInflater.from(context).inflate(R.layout.activity_conversation, null)
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun FloatingActionButtonCompose(context: Context, viewModel: ConversationViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Column() {
        DropdownMenu(
            modifier = Modifier
                .shadow(0.dp)
                .background(color = Color.Transparent),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FloatingActionButton(
                    shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
                    backgroundColor = green70,
                    contentColor = Color.White,
                    onClick = {
                        makeToast(context, "Fab clicked")
                        expanded = true
                    })
                {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_logout_24),
                        contentDescription = "add icon"
                    )
                }
                FloatingActionButton(
                    shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
                    backgroundColor = green70,
                    contentColor = Color.White,
                    onClick = {
                        makeToast(context, "Fab clicked")
                        expanded = true
                    })
                {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_group_add_24),
                        contentDescription = "add icon"
                    )
                }
                FloatingActionButton(
                    shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
                    backgroundColor = green70,
                    contentColor = Color.White,
                    onClick = {
                        makeToast(context, "Fab clicked")
                        expanded = true
                    })
                {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_group_24),
                        contentDescription = "add icon"
                    )
                }
                FloatingActionButton(
                    shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
                    backgroundColor = green70,
                    contentColor = Color.White,
                    onClick = {
                        viewModel.onAction(ConversationViewModel.Action.onStartChat)
                    })
                {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_chat_24),
                        contentDescription = "add icon"
                    )
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .graphicsLayer(rotationZ = if(expanded) 45f else 0f),
            shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
            backgroundColor = green70,
            contentColor = Color.White,
            onClick = {
                makeToast(context, "Fab clicked")
                expanded = true
            })
        {
            Image(painter = painterResource(id = R.drawable.baseline_add_24), contentDescription = "add icon")
        }
    }
}

private fun startSingleChat(userId: String, context: Context) {
    ZIMKitRouter.toMessageActivity(context, userId, ZIMKitConversationType.ZIMKitConversationTypePeer)
}

fun createGroupChat(ids: List<String>, groupName: String, context: Context) {
    if (ids.isNullOrEmpty()) {
        return
    }
    ZIMKit.createGroup(groupName, ids) { groupInfo, inviteUserErrors, errorInfo ->
        if (errorInfo.code == ZIMErrorCode.SUCCESS) {
            if (inviteUserErrors.isNotEmpty()) {
                // Implement the logic for the prompt window based on your business logic when there is a non-existing user ID in the group.
            } else {
                // Directly enter the chat page when the group chat is created successfully.
                ZIMKitRouter.toMessageActivity(context, groupInfo.getId(), ZIMKitConversationType.ZIMKitConversationTypeGroup)
            }
        } else {
            // Implement the logic for the prompt window based on the returned error info when failing to create a group chat.
        }
    }
}

fun joinGroupChat(groupId: String, context: Context) {
    ZIMKit.joinGroup(groupId) { groupInfo, errorInfo ->
        if (errorInfo.code == ZIMErrorCode.SUCCESS) {
            // Enter the group chat page after joining the group chat successfully.
            ZIMKitRouter.toMessageActivity(context, groupInfo.getId(), ZIMKitConversationType.ZIMKitConversationTypeGroup)
        } else {
            // Implement the logic for the prompt window based on the returned error info when failing to join the group chat.
        }
    }
}

fun makeToast(ctx: Context, msg: String) {
    Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
}

@Composable
fun TextInputDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var textValue by remember { mutableStateOf("") }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Enter Text") },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm(textValue)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = green70)
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() }
                ) {
                    Text("Dismiss")
                }
            },
            text = {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = textValue,
                        onValueChange = { textValue = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Enter text") }
                    )
                }
            }
        )
    }
}
