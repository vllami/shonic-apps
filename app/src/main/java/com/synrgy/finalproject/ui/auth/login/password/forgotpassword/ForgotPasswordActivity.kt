package com.synrgy.finalproject.ui.auth.login.password.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hafidh.domain.common.Event
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivityForgotPasswordBinding
import com.synrgy.finalproject.ui.auth.login.password.ForgotPasswordViewModel
import com.synrgy.finalproject.ui.auth.login.password.forgotpassword.verification.ForgotPasswordVerificationActivity
import com.synrgy.finalproject.utils.gone
import com.synrgy.finalproject.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel by viewModels<ForgotPasswordViewModel>()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            binding.apply {
                etForgotPasswordEmail.text.toString().trim().apply {
                    btnForgotPassword.isEnabled =
                        isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
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
            viewModel.stateCheckEmail.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach { event ->
                    when (event) {
                        is Event.Init -> Unit
                        is Event.Loading -> {
                            pbForgotPassword.visible()
                            tvErrorUbahPassword.gone()
                        }
                        is Event.Error -> {
                            pbForgotPassword.gone()
                            tvErrorUbahPassword.visible()
                        }
                        is Event.Success -> {
                            pbForgotPassword.gone()
                            tvErrorUbahPassword.gone()
                            event.data.message?.let {
                                Snackbar.make(
                                    root,
                                    it, Snackbar.LENGTH_LONG
                                ).show()
                            }
                            startActivity(Intent(this@ForgotPasswordActivity, ForgotPasswordVerificationActivity::class.java))
                        }

                    }
                }.launchIn(lifecycleScope)

            llForgotPasswordBtnBack.setOnClickListener {
                onBackPressed()
            }

            etForgotPasswordEmail.addTextChangedListener(textWatcher)

            btnForgotPassword.apply {
                setOnClickListener {
                    viewModel.checkEmail(etForgotPasswordEmail.text.toString())
                }
            }

            textButtonBackToLogInOrSignUp()
        }
    }

    private fun textButtonBackToLogInOrSignUp() {
        with(binding) {
            val backToLogInOrSignUp =
                getString(R.string.forgot_password_back_to_log_in_or_sign_up_text_button)

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