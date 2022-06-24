package com.synrgy.finalproject.ui.auth.login.password.forgotpassword.verification

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.hafidh.domain.common.Event
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivityForgotPasswordVerificationBinding
import com.synrgy.finalproject.ui.auth.login.password.ForgotPasswordViewModel
import com.synrgy.finalproject.ui.auth.login.password.forgotpassword.ForgotPasswordActivity
import com.synrgy.finalproject.ui.auth.login.password.newpassword.NewPasswordActivity
import com.synrgy.finalproject.utils.*
import com.synrgy.finalproject.utils.Constants.EXTRA_EMAIL_FORGOT_PASSWORD
import com.synrgy.finalproject.utils.Constants.EXTRA_TOKEN_FORGOT_PASSWORD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ForgotPasswordVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordVerificationBinding
    private lateinit var countDownTimer: CountDownTimer

    private val viewModel by viewModels<ForgotPasswordViewModel>()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            binding.apply {
                val input1 = etForgotPasswordVerificationDigit1.text.toString().trim()
                val input2 = etForgotPasswordVerificationDigit2.text.toString().trim()
                val input3 = etForgotPasswordVerificationDigit3.text.toString().trim()
                val input4 = etForgotPasswordVerificationDigit4.text.toString().trim()
                val input5 = etForgotPasswordVerificationDigit5.text.toString().trim()
                val input6 = etForgotPasswordVerificationDigit6.text.toString().trim()

                btnForgotPasswordVerification.isEnabled =
                    input1.isNotEmpty() &&
                            input2.isNotEmpty() &&
                            input3.isNotEmpty() &&
                            input4.isNotEmpty() &&
                            input5.isNotEmpty() &&
                            input6.isNotEmpty()
            }
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordVerificationBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            val email = intent.getStringExtra(EXTRA_EMAIL_FORGOT_PASSWORD)

            viewModel.stateValidate.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).onEach { state ->
                when(state) {
                    is Event.Init -> Unit
                    is Event.Loading -> {
                        btnForgotPasswordVerification.isEnabled = false
                        pbForgotPasswordVerification.visible()
                    }
                    is Event.Error -> {
                        pbForgotPasswordVerification.gone()
                        tvErrorVerificationCode.visible()
                        btnForgotPasswordVerification.isEnabled = true
                    }
                    is Event.Success -> {
                        pbForgotPasswordVerification.gone()
                        tvErrorVerificationCode.gone()
                        btnForgotPasswordVerification.isEnabled = false
                        Intent(this@ForgotPasswordVerificationActivity, NewPasswordActivity::class.java).apply {
                            email?.let { putExtra(EXTRA_EMAIL_FORGOT_PASSWORD, it)}
                            putExtra(EXTRA_TOKEN_FORGOT_PASSWORD, state.data.token)
                            startActivity(this)
                        }
                    }
                }
            }.launchIn(lifecycleScope)
            llForgotPasswordVerificationBtnBack.setOnClickListener {
                Intent(this@ForgotPasswordVerificationActivity, ForgotPasswordActivity::class.java).apply {
                    startActivity(this)
                }
            }

            setDescription(
                getString(
                    R.string.forgot_password_verification_description,
                    email
                )
                ,
                email ?: ""
            )

            editTextMoving()

            editTextFocus()

            tvForgotPasswordVerificationDescriptionCountdown.apply {
                countDownTimer = object : CountDownTimer(60000, 1000) {
                    override fun onFinish() {
                        text = ""
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        text = getString(
                            R.string.forgot_password_verification_resend_button,
                            millisUntilFinished / 1000
                        )
                    }
                }.start()
            }

            btnForgotPasswordVerification.setOnClickListener {
                val otp = StringBuilder().append(
                    binding.etForgotPasswordVerificationDigit1.text.toString()
                ).append(
                    binding.etForgotPasswordVerificationDigit2.text.toString()
                ).append(
                    binding.etForgotPasswordVerificationDigit3.text.toString()
                ).append(
                    binding.etForgotPasswordVerificationDigit4.text.toString()
                ).append(
                    binding.etForgotPasswordVerificationDigit5.text.toString()
                ).append(
                    binding.etForgotPasswordVerificationDigit6.text.toString()
                ).toString().toInt()
                email?.let {
                    viewModel.validate(it, otp)
                }
            }
        }
    }

    private fun setDescription(description: String, email: String) {
        SpannableString(description).also { span ->
            span.apply {
                setSpan(
                    ForegroundColorSpan(
                        resources.getColor(R.color.primary, null)
                    ),
                    description.indexOf(email),
                    description.indexOf(email) + email.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                binding.tvForgotPasswordVerificationDescription.text = this
            }
        }
    }

    private fun editTextMoving() {
        with(binding) {
            etForgotPasswordVerificationDigit1.addTextChanged(etForgotPasswordVerificationDigit2)
            etForgotPasswordVerificationDigit2.addTextChanged(etForgotPasswordVerificationDigit3)
            etForgotPasswordVerificationDigit3.addTextChanged(etForgotPasswordVerificationDigit4)
            etForgotPasswordVerificationDigit4.addTextChanged(etForgotPasswordVerificationDigit5)
            etForgotPasswordVerificationDigit5.addTextChanged(etForgotPasswordVerificationDigit6)
        }
    }

    private fun editTextFocus() {
        with(binding) {
            etForgotPasswordVerificationDigit1.addTextWatcher(textWatcher)
            etForgotPasswordVerificationDigit2.addTextWatcher(textWatcher)
            etForgotPasswordVerificationDigit3.addTextWatcher(textWatcher)
            etForgotPasswordVerificationDigit4.addTextWatcher(textWatcher)
            etForgotPasswordVerificationDigit5.addTextWatcher(textWatcher)
            etForgotPasswordVerificationDigit6.addTextWatcher(textWatcher)
        }
    }

}