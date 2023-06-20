package com.glintcatcher.dingdong00.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.glintcatcher.dingdong00.local.Repository
import com.glintcatcher.dingdong00.network.UserRepository
import com.glintcatcher.dingdong00.utils.MMKVUtil
import com.glintcatcher.dingdong00.utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val userRepository: UserRepository
) : ViewModel() {

    var viewStates by mutableStateOf(LoginViewState())
        private set

//    init {
////        viewModelScope.launch {
////            viewStates.isRemember = MMKVUtil.getBoolean("isRemember")
////            if (viewStates.isRemember) {
////                changeUsername(MMKVUtil.getString("username"))
////                changePassword(MMKVUtil.getString("password"))
////            }
////        }
//    }

    fun dispatch(action: LoginAction) {
        when (action) {
            is LoginAction.SignIn -> signIn()
            is LoginAction.SignUp -> signUp()
            is LoginAction.ClearNickname -> clearNickname()
            is LoginAction.ClearUsername -> clearUsername()
            is LoginAction.ClearPassword -> clearPassword()
            is LoginAction.ChangeNickname -> changeNickname(action.nickname)
            is LoginAction.ChangeUsername -> changeUsername(action.username)
            is LoginAction.ChangePassword -> changePassword(action.password)
            is LoginAction.ChangeIsLogin -> changeIsLogin()
        }
    }

    private fun signIn() {
        val json = JSON.toJSONString(
            mapOf(
                "username" to viewStates.username,
                "password" to viewStates.password
            )
        )
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())

        viewModelScope.launch {
            try {
                val resp = withContext(Dispatchers.IO) {
                    userRepository.signIn(body)
                }
                resp.msg.toast()
                if (resp.code == 200) {
                    repository.getRemoteReminds()
                    MMKVUtil.put("username", viewStates.username)
                    MMKVUtil.put("token", resp.data["token"])
                    changePassword("")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun getId() {
        viewModelScope.launch {
            val resp = withContext(Dispatchers.IO) {
                userRepository.getId()
            }
            changeUsername(resp.data)
        }
    }

    private fun signUp() {
        if (viewStates.password.length == 8) {
            val json = JSON.toJSONString(
                mapOf(
                    "nickname" to viewStates.nickname,
                    "username" to viewStates.username,
                    "password" to viewStates.password
                )
            )
            val body = json.toRequestBody("application/json".toMediaTypeOrNull())
            viewModelScope.launch {
                val resp = userRepository.signUp(body)
                resp.msg.toast()
                if (resp.code == 200) {
                    changeIsLogin()
                }
            }
        } else {
            "密码为八位数".toast()
        }
    }

    private fun clearNickname() {
        viewStates = viewStates.copy(nickname = "")
    }

    private fun clearUsername() {
        viewStates = viewStates.copy(username = "")
    }

    private fun clearPassword() {
        viewStates = viewStates.copy(password = "")
    }

    private fun changeNickname(nickname: String) {
        viewStates = viewStates.copy(nickname = nickname)
    }

    private fun changeUsername(username: String) {
        if (username.length <= 10) {
            viewStates = viewStates.copy(username = username)
        } else {
            "用户名为八位数".toast()
        }
    }

    private fun changePassword(password: String) {
        if (password.length <= 10) {
            viewStates = viewStates.copy(password = password)
        } else {
            "密码为八位数".toast()
        }
    }

    private fun changeIsLogin() {
        viewStates = viewStates.copy(isLogin = !viewStates.isLogin)
    }

}
// 994314527
data class LoginViewState(
    val nickname: String = "",
    val username: String = MMKVUtil.getString("username"),
    val password: String = "",
    var isLogin: Boolean = true,
)

sealed class LoginAction {
    object SignIn : LoginAction()
    object SignUp : LoginAction()
    object ClearNickname : LoginAction()
    object ClearUsername : LoginAction()
    object ClearPassword : LoginAction()
    data class ChangeNickname(val nickname: String) : LoginAction()
    data class ChangeUsername(val username: String) : LoginAction()
    data class ChangePassword(val password: String) : LoginAction()
    object ChangeIsLogin : LoginAction()
}