package com.example.presentation.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val googleSignInClient: GoogleSignInClient) : ViewModel() {
    fun googleLogout() = googleSignInClient.signOut()
}