package com.glintcatcher.dingdong00.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.glintcatcher.dingdong00.R
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.glintcatcher.dingdong00.viewmodel.LoginAction
import com.glintcatcher.dingdong00.viewmodel.LoginViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login(viewModel: LoginViewModel) {
    val viewState = viewModel.viewStates
    val keyboardCtrl = LocalSoftwareKeyboardController.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        keyboardCtrl?.hide()
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_logo),
            contentDescription = null
        )

        AnimatedVisibility(!viewState.isLogin) {
            LoginTextField(text = "昵称", content = viewState.nickname, {
                viewModel.dispatch(LoginAction.ChangeNickname(it))
            }, 0) {
                viewModel.dispatch(LoginAction.ClearNickname)
            }
        }

        LoginTextField(text = "用户名", content = viewState.username, {
            viewModel.dispatch(LoginAction.ChangeUsername(it))
        }, 1, readOnly = !viewState.isLogin) {
            viewModel.dispatch(LoginAction.ClearUsername)
        }
        LoginTextField(text = "密码", content = viewState.password, {
            viewModel.dispatch(LoginAction.ChangePassword(it))
        }, 2) {
            viewModel.dispatch(LoginAction.ClearPassword)
        }
        SignButton(viewState.isLogin, {
            if (viewState.isLogin) {
                viewModel.getId()
            }
            viewModel.dispatch(LoginAction.ChangeIsLogin)
        }) {
            viewModel.dispatch(if (viewState.isLogin) LoginAction.SignIn else LoginAction.SignUp)
        }
    }
}

@Composable
private fun LoginTextField(
    text: String,
    content: String,
    onContentChange: (String) -> Unit,
    id: Int,
    readOnly: Boolean = false,
    onClear: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var passwordVisible by remember {
            mutableStateOf(false)
        }
        TextField(
            readOnly = readOnly,
            value = content,
            onValueChange = onContentChange,
            label = {
                Text(text = text)
            },
            placeholder = {
                Text(text = text)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(
                        id = when (id) {
                            0 -> R.drawable.nickname
                            1 -> R.drawable.username
                            2 -> R.drawable.password
                            else -> R.drawable.nickname
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                TrailingIcon(content, id == 2, onClear, !readOnly) {
                    passwordVisible = !passwordVisible
                }
            },
            visualTransformation = if (id == 2 && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = when (id) {
                    0 -> KeyboardType.Text
                    1 -> KeyboardType.Number
                    2 -> KeyboardType.Password
                    else -> KeyboardType.Text
                },
            ),
            keyboardActions = KeyboardActions(),
            singleLine = true,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TrailingIcon(
    content: String,
    isPassword: Boolean,
    onClear: () -> Unit,
    clearVisible: Boolean,
    changePasswordVisible: () -> Unit
) {
    AnimatedVisibility(
        visible = content.isNotBlank(),
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        Row {

            AnimatedVisibility(isPassword) {
                Icon(
                    painter = painterResource(id = R.drawable.eyes),
                    contentDescription = null,
                    Modifier
                        .clickable(onClick = changePasswordVisible)
                        .size(20.dp)
                )
            }
            AnimatedVisibility(clearVisible) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    Modifier
                        .clickable(onClick = onClear)
                        .size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun SignButton(
    isLogin: Boolean,
    onTextClick: () -> Unit,
    onButtonClick: () -> Unit
) {
    Column {
        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 60.dp, end = 60.dp),
            colors = ButtonDefaults.buttonColors(MyTheme.colors.button),
            elevation = ButtonDefaults.elevation(10.dp)
        ) {
            Text(if (isLogin) MyString.login else MyString.register)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 65.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = if (isLogin) MyString.register else MyString.login,
                modifier = Modifier.clickable(onClick = onTextClick),
                textDecoration = TextDecoration.Underline
            )
        }
    }

}
