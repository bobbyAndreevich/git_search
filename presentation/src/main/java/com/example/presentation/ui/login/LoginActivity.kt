package com.example.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.ui.main.MainActivity
import com.example.presentation.R
import com.example.presentation.dagger.DaggerApp
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity: AppCompatActivity(), ActivityNavigation {

    companion object {
        const val SIGN_IN = 1
        const val ACCOUNT = "account"
    }

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as DaggerApp).applicationComponent.inject(this)
        setContentView(R.layout.activity_login)

        subscribeUi()
        initializeLoginButtons()
        viewModel.googleLogout()
    }

    private fun initializeLoginButtons() {
        sign_in_google_button.setOnClickListener {
            viewModel.googleSignUp()
        }
    }

    private fun subscribeUi() {
        viewModel.startActivityForResultEvent.setEventReceiver(this, this)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.onResultFromActivity(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)
        if (viewModel.getAccount().value != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.putExtra(ACCOUNT, viewModel.getAccount().value)
            startActivity(intent)
        } else if (viewModel.getErrorMessage().value != null) {
            Toast.makeText(this, viewModel.getErrorMessage().value, Toast.LENGTH_LONG).show()
        }
        viewModel.clearValues()
    }
}