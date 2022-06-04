package com.synrgy.finalproject.ui.auth.login.password.passwordchanged

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivityPasswordChangedBinding
import com.synrgy.finalproject.ui.auth.login.LogInActivity

class PasswordChangedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordChangedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPasswordChangedBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            btnPasswordChangedToLogIn.setOnClickListener {
                Intent(this@PasswordChangedActivity, LogInActivity::class.java).apply {
                    startActivity(this)
                }
            }

            btnPasswordChangedToHome.setOnClickListener {

            }
        }
    }

}