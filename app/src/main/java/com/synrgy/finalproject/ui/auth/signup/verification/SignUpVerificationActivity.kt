package com.synrgy.finalproject.ui.auth.signup.verification

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivitySignUpVerificationBinding
import com.synrgy.finalproject.ui.auth.signup.CompleteSignUpActivity
import com.synrgy.finalproject.ui.auth.signup.SignUpActivity.Companion.EXTRA_EMAIL
import com.synrgy.finalproject.ui.auth.signup.SignUpViewModel
import com.synrgy.finalproject.utils.addTextChanged
import com.synrgy.finalproject.utils.addTextWatcher
import com.synrgy.finalproject.utils.gone
import com.synrgy.finalproject.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpVerificationBinding
    private lateinit var countDownTimer: CountDownTimer
    private val viewModel by viewModels<SignUpViewModel>()

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
            val email = intent?.extras?.getString(EXTRA_EMAIL) ?: "capek bener badan ya"
            Log.d("SignUpVerificationActivity", "email: $email")
            observe(email)
            setContentView(root)
            setDescription(getString(R.string.sign_up_verification_description, email), email)
            startTimeCountDown(binding.tvSignUpVerificationDescriptionCountdown)
            editTextMoving()
            editTextFocus()
            setOnVerifyClickListener(email)
            onNaviBackPressed(this.llSignUpVerificationBtnBack)
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
            etSignUpVerificationDigit1.addTextWatcher(textWatcher)
            etSignUpVerificationDigit2.addTextWatcher(textWatcher)
            etSignUpVerificationDigit3.addTextWatcher(textWatcher)
            etSignUpVerificationDigit4.addTextWatcher(textWatcher)
            etSignUpVerificationDigit5.addTextWatcher(textWatcher)
            etSignUpVerificationDigit6.addTextWatcher(textWatcher)
        }
    }

    private fun startTimeCountDown(tvBinding: TextView) {
        tvBinding.apply {
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
    }

    private fun observe(email: String) {
        with(binding) {
            viewModel.stateVerifyEmail.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach { state ->
                    when (state) {
                        is SignUpViewModel.SignUpState.Init -> Unit
                        is SignUpViewModel.SignUpState.Loading -> {
                            pbVerification.visible()
                        }
                        is SignUpViewModel.SignUpState.Error -> {
                            pbVerification.gone()
                            tvErrorVerificationCode.visible()
                        }
                        is SignUpViewModel.SignUpState.Success -> {
                            pbVerification.gone()
                            tvErrorVerificationCode.gone()
                            Intent(
                                this@SignUpVerificationActivity,
                                CompleteSignUpActivity::class.java
                            )
                                .apply {
                                    putExtra(EXTRA_EMAIL, email)
                                    startActivity(this)
                                    finish()
                                }
                        }
                    }
                }.launchIn(lifecycleScope)
        }
    }

    private fun setOnVerifyClickListener(email: String) {
        with(binding){
            btnSignUpVerification.setOnClickListener {
                val otp = StringBuilder().append(
                    binding.etSignUpVerificationDigit1.text.toString()
                ).append(
                    binding.etSignUpVerificationDigit2.text.toString()
                ).append(
                    binding.etSignUpVerificationDigit3.text.toString()
                ).append(
                    binding.etSignUpVerificationDigit4.text.toString()
                ).append(
                    binding.etSignUpVerificationDigit5.text.toString()
                ).append(
                    binding.etSignUpVerificationDigit6.text.toString()
                ).toString().toInt()
                email.let {
                    viewModel.verifyEmail(it, otp)
                }
            }
        }
    }

    private fun onNaviBackPressed(llSignUpVerificationBtnBack: LinearLayout) {
        llSignUpVerificationBtnBack.setOnClickListener {
            onBackPressed()
        }
    }

}
