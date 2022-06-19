package com.synrgy.finalproject.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.hafidh.domain.common.Event
import com.hafidh.domain.login.model.LoginDomain
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivityLogInBinding
import com.synrgy.finalproject.ui.auth.login.password.forgotpassword.ForgotPasswordActivity
import com.synrgy.finalproject.ui.auth.signup.SignUpActivity
import com.synrgy.finalproject.utils.gone
import com.synrgy.finalproject.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    private val viewModel by viewModels<LogInViewModel>()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            binding.apply {
                val email = etLogInEmail.text.toString().trim()
                val password = etLogInPassword.text.toString().trim()

                btnLogIn.isEnabled = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches() && password.isNotEmpty()
            }
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            viewModel.loginState.flowWithLifecycle(lifecycle).onEach {
                when (it) {
                    is Event.Loading -> {
                        btnLogIn.isEnabled = false
                        pbLogin.visible()
                    }
                    is Event.Success<LoginDomain> -> {
                        pbLogin.gone()
                        btnLogIn.isEnabled = false
                        Log.d("Login", "Success")
                    }

                    is Event.None -> Unit

                    is Event.Error -> {
                        btnLogIn.isEnabled = true
                        pbLogin.gone()
                        tvErrorLogin.visible()
                        tvErrorLogin.text = resources.getString(R.string.error_login)
                    }
                }
            }.launchIn(lifecycleScope)
            llLogInBtnBack.setOnClickListener {
                Intent(Intent.ACTION_MAIN).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    addCategory(Intent.CATEGORY_HOME)
                    startActivity(this)
                }
            }

            tvLogInTextBtnSignUp.setOnClickListener {
                Intent(this@LogInActivity, SignUpActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(this)
                }
            }

            etLogInEmail.addTextChangedListener(textWatcher)

            etLogInPassword.apply {
                addTextChangedListener(textWatcher)
            }

            tvLogInBtnForgotPassword.setOnClickListener {
                Intent(this@LogInActivity, ForgotPasswordActivity::class.java).apply {
                    startActivity(this)
                }
            }

            btnLogIn.apply {
                setOnClickListener {
                    viewModel.login(etLogInEmail.text.toString(), etLogInPassword.text.toString())
                }
            }
        }
    }

}