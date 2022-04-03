package com.example.presentation.ui.login

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.presentation.LiveMessageEvent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val googleSignInClient: GoogleSignInClient): ViewModel() {

    val startActivityForResultEvent = LiveMessageEvent<ActivityNavigation>()
    private var account: LiveData<GoogleSignInAccount?> = MutableLiveData()
    private var errorMessage: LiveData<String?> = MutableLiveData()

    fun getAccount() = account
    fun getErrorMessage() = errorMessage

    fun googleSignUp() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResultEvent.sendEvent {
            startActivityForResult(
                signInIntent,
                LoginActivity.SIGN_IN
            )
        }
    }

    fun clearValues() {
        account = MutableLiveData()
        errorMessage = MutableLiveData()
    }

    fun onResultFromActivity(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LoginActivity.SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                if (task.isSuccessful)
                    googleSignInComplete(task)
                else
                    errorMessage = MutableLiveData("Не удалось авторизоваться.")
            }
            else -> {}
        }
    }

    fun googleLogout() {
        googleSignInClient.signOut()
    }

    private fun googleSignInComplete(completedTask: Task<GoogleSignInAccount>) {
        try {
            this.account = MutableLiveData(completedTask.result)
        } catch (e: ApiException) {
            this.errorMessage = MutableLiveData(e.message?:"Ошибка!")
        }
    }

}