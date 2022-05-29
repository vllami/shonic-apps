package com.synrgy.finalproject.ui.auth.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private val viewModel by viewModels<LogInViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}