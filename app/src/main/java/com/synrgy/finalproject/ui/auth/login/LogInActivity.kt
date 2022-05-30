package com.synrgy.finalproject.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.finalproject.databinding.ActivityLogInBinding
import com.synrgy.finalproject.ui.auth.forgotpassword.ForgotPasswordActivity
import com.synrgy.finalproject.ui.auth.signup.SignUpActivity

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    private val viewModel by viewModels<LogInViewModel>()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            binding.apply {
                val email = etLogInEmail.text.toString().trim()
                val password = etLogInPassword.text.toString().trim()

                btnLogIn.isEnabled = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotEmpty()
            }
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

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

                setOnEditorActionListener { _, editorInfo, _ ->
                    when (editorInfo) {
                        EditorInfo.IME_ACTION_DONE -> clearFocus()
                    }

                    false
                }
            }

            tvLogInBtnForgotPassword.setOnClickListener {
                Intent(this@LogInActivity, ForgotPasswordActivity::class.java).apply {
                    startActivity(this)
                }
            }

            btnLogIn.apply {
                setOnClickListener {

                }
            }
        }
    }

}