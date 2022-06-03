package com.synrgy.finalproject.ui.auth.verification

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
import com.synrgy.finalproject.databinding.ActivitySignUpVerificationBinding
import com.synrgy.finalproject.ui.auth.signup.CompleteSignUpActivity
import com.synrgy.finalproject.ui.auth.signup.SignUpActivity
import com.synrgy.finalproject.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpVerificationBinding
    private lateinit var countDownTimer: CountDownTimer

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            binding.apply {
                val input1 = etSignUpVerificationDigit1.text.toString().trim()
                val input2 = etSignUpVerificationDigit2.text.toString().trim()
                val input3 = etSignUpVerificationDigit3.text.toString().trim()
                val input4 = etSignUpVerificationDigit4.text.toString().trim()
                val input5 = etSignUpVerificationDigit5.text.toString().trim()
                val input6 = etSignUpVerificationDigit6.text.toString().trim()

                btnSignUpVerification.isEnabled =
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

        binding = ActivitySignUpVerificationBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            llSignUpVerificationBtnBack.setOnClickListener {
                // Intent(this@SignUpVerificationActivity, SignUpActivity::class.java).apply {
                //     startActivity(this)
                // }
                //
                // this@SignUpVerificationActivity.finish()
                onBackPressed()
            }

            setDescription(
                getString(
                    R.string.sign_up_verification_description,
                    Constants.DUMMY_EMAIL
                )
            )

            editTextMoving()

            editTextFocus()

            tvSignUpVerificationDescriptionCountdown.apply {
                countDownTimer = object : CountDownTimer(60000, 1000) {
                    override fun onFinish() {
                        text = ""
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        text = getString(
                            R.string.sign_up_verification_resend_button,
                            millisUntilFinished / 1000
                        )
                    }
                }.start()
            }

            btnSignUpVerification.setOnClickListener {
                Intent(this@SignUpVerificationActivity, CompleteSignUpActivity::class.java).apply {
                    startActivity(this)
                }
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

                binding.tvSignUpVerificationDescription.text = this
            }
        }
    }

    private fun editTextMoving() {
        with(binding) {
            etSignUpVerificationDigit1.addTextChanged(etSignUpVerificationDigit2)
            etSignUpVerificationDigit2.addTextChanged(etSignUpVerificationDigit3)
            etSignUpVerificationDigit3.addTextChanged(etSignUpVerificationDigit4)
            etSignUpVerificationDigit4.addTextChanged(etSignUpVerificationDigit5)
            etSignUpVerificationDigit5.addTextChanged(etSignUpVerificationDigit6)
        }
    }

    private fun editTextFocus() {
        with(binding) {
            etSignUpVerificationDigit1.apply {
                addTextWatcher(textWatcher)
            }

            etSignUpVerificationDigit2.apply {
                addTextWatcher(textWatcher)
            }

            etSignUpVerificationDigit3.apply {
                addTextWatcher(textWatcher)
            }

            etSignUpVerificationDigit4.apply {
                addTextWatcher(textWatcher)
            }

            etSignUpVerificationDigit5.apply {
                addTextWatcher(textWatcher)
            }

            etSignUpVerificationDigit6.apply {
                addTextWatcher(textWatcher)
            }
        }
    }
}
