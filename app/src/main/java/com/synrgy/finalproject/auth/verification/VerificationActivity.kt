package com.synrgy.finalproject.auth.verification

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivityVerificationBinding
import com.synrgy.finalproject.utils.Constants
import com.synrgy.finalproject.utils.setActionBarTitle

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get intent data
        val email = intent.getStringExtra("email")
        Log.d("VERIFICATION", "email: $email")

        setActionBarTitle(binding.toolbar, getString(R.string.register_verification))
        setDescription(getString(R.string.verification_description, Constants.DUMMY_EMAIL))
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
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
}