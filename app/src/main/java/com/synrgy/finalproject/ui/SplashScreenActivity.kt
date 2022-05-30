package com.synrgy.finalproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.finalproject.databinding.ActivitySplashScreenBinding
import com.synrgy.finalproject.ui.auth.login.LogInActivity
import com.synrgy.finalproject.utils.Constants.SPLASH_DELAY

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, LogInActivity::class.java).also { toLogIn ->
                startActivity(toLogIn)
                finish()
            }
        }, SPLASH_DELAY.toLong())
    }

}