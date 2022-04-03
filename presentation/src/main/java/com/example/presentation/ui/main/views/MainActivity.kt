package com.example.presentation.ui.main.views

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.presentation.R
import com.example.presentation.dagger.DaggerApp
import com.example.presentation.ui.login.LoginActivity
import com.example.presentation.ui.main.viewmodels.MainActivityViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as DaggerApp).applicationComponent.inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(app_bar_main.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home), drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

        addLogoutOption()
        fillUserData()
    }

    private fun addLogoutOption() {
        nav_view.menu.getItem(1).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {
            viewModel.googleLogout().addOnCompleteListener {
                finish()
            }
            return@OnMenuItemClickListener true
        })
    }

    private fun fillUserData() {
        val account = intent.extras?.get(LoginActivity.ACCOUNT) as GoogleSignInAccount

        val header = nav_view.getHeaderView(0)

        val userNameText = header.findViewById<TextView>(R.id.user_login)
        userNameText.text = account.displayName
        val userEmailText = header.findViewById<TextView>(R.id.email_textView)
        userEmailText.text = account.email

        val image = header.findViewById<ImageView>(R.id.user_image)
        val placeholder = getDrawable(R.mipmap.ic_launcher_round)!!
        Picasso.get().load(account.photoUrl).resize(placeholder.intrinsicHeight, placeholder.intrinsicWidth)
            .placeholder(placeholder).into(image)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}