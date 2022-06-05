package com.synrgy.finalproject.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivitySignUpBinding
import com.synrgy.finalproject.ui.auth.login.LogInActivity
import com.synrgy.finalproject.ui.auth.signup.verification.SignUpVerificationActivity
import com.synrgy.finalproject.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private val viewModel by viewModels<SignUpViewModel>()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            binding.apply {
                etSignUpEmail.text.toString().trim().apply {
                    btnSignUp.isEnabled =
                        isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
                }
            }
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            viewModel.stateCheckEmail.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach { state ->
                    when (state) {
                        is SignUpViewModel.SignUpState.Init -> Unit
                        is SignUpViewModel.SignUpState.Loading -> {
                            pbSignUp.visible()
                        }
                        is SignUpViewModel.SignUpState.Error -> {
                            pbSignUp.gone()
                            val email = etSignUpEmail.text.toString().trim()
                            alert {
                                title("Email sudah terdaftar")
                                message("Lanjut masuk menggunakan email ${email}?")
                                positiveButton(text = "Ya, Masuk") {
                                    Intent(this@SignUpActivity, LogInActivity::class.java).also {
                                        startActivity(it)
                                    }
                                }
                                negativeButton(text = "Ubah Email")
                            }
                        }
                        is SignUpViewModel.SignUpState.Success -> {
                            pbSignUp.gone()
                            val email = etSignUpEmail.text.toString().trim()
                            Intent(
                                this@SignUpActivity,
                                SignUpVerificationActivity::class.java
                            ).also {
                                it.putExtra(EXTRA_EMAIL, email)
                                Log.d("SignUpActivity", "email: $email")
                                startActivity(it)
                            }
                        }
                    }
                }.launchIn(lifecycleScope)

            llSignUpBtnBack.setOnClickListener {
                onBackPressed()
            }

            tvSignUpTextBtnLogIn.setOnClickListener {
                onBackPressed()
            }

            etSignUpEmail.apply {
                addTextChanged(this)
                addTextWatcher(textWatcher)
            }

            btnSignUp.apply {
                setOnClickListener {
                    val email = etSignUpEmail.text.toString().trim()
                    viewModel.checkEmail(email)
                }
            }

            setTermAndPrivacy()
        }
    }

    private fun setTermAndPrivacy() {
        with(binding) {
            val term = getString(R.string.sign_up_term_of_use)
            val privacy = getString(R.string.sign_up_privacy_policy)

            // Span Term of Use
            SpannableString(term).also { spanTerm ->
                spanTerm.apply {
                    setSpan(
                        ForegroundColorSpan(
                            resources.getColor(R.color.primary, null)
                        ),
                        34,
                        term.length - 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    tvSignUpTerms.text = this
                }
            }

            // Span Privacy Policy
            SpannableString(privacy).also { spanPrivacy ->
                spanPrivacy.apply {
                    setSpan(
                        ForegroundColorSpan(
                            resources.getColor(R.color.primary, null)
                        ),
                        0,
                        9,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    setSpan(
                        ForegroundColorSpan(
                            resources.getColor(R.color.primary, null)
                        ),
                        16,
                        privacy.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    tvSignUpConditions.text = this
                }
            }
        }
    }

    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }

}