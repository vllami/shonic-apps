package com.synrgy.finalproject.ui.auth.login.password.forgotpassword.verification

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivityForgotPasswordVerificationBinding
import com.synrgy.finalproject.ui.auth.login.password.forgotpassword.ForgotPasswordActivity
import com.synrgy.finalproject.utils.Constants
import com.synrgy.finalproject.utils.addTextChanged
import com.synrgy.finalproject.utils.addTextWatcher

class ForgotPasswordVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordVerificationBinding
    private lateinit var countDownTimer: CountDownTimer

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

            llForgotPasswordVerificationBtnBack.setOnClickListener {
                Intent(this@ForgotPasswordVerificationActivity, ForgotPasswordActivity::class.java).apply {
                    startActivity(this)
                }
            }

            setDescription(
                getString(
                    R.string.forgot_password_verification_description,
                    Constants.DUMMY_EMAIL
                )
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
                // Intent(this@ForgotPasswordVerificationActivity, ::class.java).apply {
                //     startActivity(this)
                // }
            }
        }
    }

    private fun setDescription(description: String) {
        SpannableString(description).also { span ->
            span.apply {
                setSpan(
                    ForegroundColorSpan(
                        resources.getColor(R.color.primary, null)
                    ),
                    description.indexOf(Constants.DUMMY_EMAIL),
                    description.indexOf(Constants.DUMMY_EMAIL) + Constants.DUMMY_EMAIL.length,
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