package com.smartherd.helloworld2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_loginpage.*
import kotlinx.android.synthetic.main.user_registration.*

class LoginPage : AppCompatActivity() {

    lateinit var handler: DBHelperUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginpage)

        handler = DBHelperUser(this)

        showHome()

        registration.setOnClickListener {
            showRegistration()
        }

        save.setOnClickListener {
            handler.insertUserData(name.text.toString(), email.text.toString(), password_register.text.toString())
            showHome()
        }

        login_button.setOnClickListener {
            if (handler.userPresent(login_email.text.toString(), login_password.text.toString()))
            {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CitySearch::class.java)
                startActivity(intent)
            }
            else
                Toast.makeText(this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showRegistration() {
        registration_layout.visibility = View.VISIBLE
        home_ll.visibility = View.GONE
    }

    private fun showHome() {
        registration_layout.visibility = View.GONE
        home_ll.visibility = View.VISIBLE
    }

}