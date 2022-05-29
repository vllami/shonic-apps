package com.synrgy.finalproject.auth.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.synrgy.finalproject.R
import com.synrgy.finalproject.auth.verification.VerificationActivity
import com.synrgy.finalproject.databinding.ActivitySignUpBinding
import com.synrgy.finalproject.utils.setActionBarTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBarTitle(binding.toolbar, getString(R.string.register_daftar))
        setTermAndPrivacy()

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.etRegisterEmail.setOnEditorActionListener { _, editorInfo, _ ->
            when (editorInfo) {
                EditorInfo.IME_ACTION_DONE -> binding.etRegisterEmail.clearFocus()
            }
            false

        }

        binding.btnRegisterEmail.setOnClickListener {
            Intent(this, VerificationActivity::class.java).also {
                it.putExtra("email", binding.etRegisterEmail.text.toString())
                startActivity(it)
            }
        }
    }

    private fun setTermAndPrivacy() {
        with(binding) {
            val term = getString(R.string.register_term_of_use)
            val privacy = getString(R.string.register_privacy_policy)

            // span term of use
            val spanTerm = SpannableString(term)
            spanTerm.setSpan(
                ForegroundColorSpan(
                    resources.getColor(R.color.primary, null)
                ),
                34,
                term.length - 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvRegisterTerms.text = spanTerm

            // span privacy policy
            val spanPrivacy = SpannableString(privacy)
            spanPrivacy.setSpan(
                ForegroundColorSpan(
                    resources.getColor(R.color.primary, null)
                ),
                0,
                9,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spanPrivacy.setSpan(
                ForegroundColorSpan(
                    resources.getColor(R.color.primary, null)
                ),
                16,
                privacy.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvRegisterConditions.text = spanPrivacy
        }
    }
}