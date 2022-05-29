package com.synrgy.finalproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.synrgy.finalproject.ui.auth.login.LogInActivity
import com.synrgy.finalproject.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var activitySplashScreenBinding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySplashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)

        setContentView(activitySplashScreenBinding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, LogInActivity::class.java).also { toLogIn ->
                startActivity(toLogIn)
                finish()
            }
        }, SPLASH_DELAY.toLong())
    }

    companion object {
        const val SPLASH_DELAY = 3_000
    }

}