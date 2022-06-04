package com.synrgy.finalproject.ui.auth.login.password.forgotpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivityForgotPasswordBinding
import com.synrgy.finalproject.ui.auth.login.password.forgotpassword.verification.ForgotPasswordVerificationActivity

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            binding.apply {
                etForgotPasswordEmail.text.toString().trim().apply {
                    btnForgotPassword.isEnabled = isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
                }
            }
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            llForgotPasswordBtnBack.setOnClickListener {
                onBackPressed()
            }

            etForgotPasswordEmail.addTextChangedListener(textWatcher)

            btnForgotPassword.apply {
                setOnClickListener {
                    Intent(this@ForgotPasswordActivity, ForgotPasswordVerificationActivity::class.java).also {
                        it.apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

                            putExtra("forgot_password", text.toString())
                            startActivity(this)
                        }
                    }
                }
            }

            textButtonBackToLogInOrSignUp()
        }
    }

    private fun textButtonBackToLogInOrSignUp() {
        with(binding) {
            val backToLogInOrSignUp = getString(R.string.forgot_password_back_to_log_in_or_sign_up_text_button)

            // Span Log In and Sign Up Text Button
            SpannableString(backToLogInOrSignUp).also { spanbackToLogInOrSignUp ->
                spanbackToLogInOrSignUp.apply {
                    setSpan(
                        ForegroundColorSpan(
                            resources.getColor(R.color.primary, null)
                        ),
                        19,
                        24,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    setSpan(
                        ForegroundColorSpan(
                            resources.getColor(R.color.primary, null)
                        ),
                        30,
                        backToLogInOrSignUp.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    tvForgotPasswordToLogInOrSignUpTextButton.text = this
                }
            }
        }
    }

}