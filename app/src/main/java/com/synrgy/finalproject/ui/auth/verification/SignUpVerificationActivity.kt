package com.synrgy.finalproject.ui.auth.verification

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.finalproject.R
import com.synrgy.finalproject.ui.auth.signup.CompleteSignUpActivity
import com.synrgy.finalproject.databinding.ActivityVerificationBinding
import com.synrgy.finalproject.utils.Constants
import com.synrgy.finalproject.utils.setActionBarTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    private lateinit var countDownTimer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarTitle(binding.toolbar, getString(R.string.register_verification))
        setDescription(getString(R.string.verification_description, Constants.DUMMY_EMAIL))
        onNextButtonClicked()
        onToolbarNavigationClicked()
        countdownTimer()
    }

    private fun setDescription(description: String) {
        val span = SpannableString(description)
        span.setSpan(
            ForegroundColorSpan(
                resources.getColor(R.color.primary, null)
            ),
            description.indexOf(Constants.DUMMY_EMAIL),
            description.indexOf(Constants.DUMMY_EMAIL) + Constants.DUMMY_EMAIL.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvVerificationDescription.text = span
    }

    private fun onNextButtonClicked() {
        binding.btnRegisterVerification.setOnClickListener {
            Intent(this, CompleteSignUpActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun onToolbarNavigationClicked() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun countdownTimer() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onFinish() {
                binding.tvVerificationDescriptionCountdown.text = ""
            }

            override fun onTick(millisUntilFinished: Long) {
                binding.tvVerificationDescriptionCountdown.text =
                    getString(R.string.verification_resend_button, millisUntilFinished / 1000)
            }
        }.start()
    }
}