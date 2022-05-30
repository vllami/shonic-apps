package com.synrgy.finalproject.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivitySignUpBinding
import com.synrgy.finalproject.ui.auth.login.LogInActivity
import com.synrgy.finalproject.ui.auth.verification.SignUpVerificationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private val viewModel by viewModels<SignUpViewModel>()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            binding.apply {
                etSignUpEmail.text.toString().trim().apply {
                    btnSignUp.isEnabled = isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
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

            llSignUpBtnBack.setOnClickListener {
                Intent(this@SignUpActivity, LogInActivity::class.java).apply {
                    startActivity(this)
                }
            }

            tvSignUpTextBtnLogIn.setOnClickListener {
                Intent(this@SignUpActivity, LogInActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

                    startActivity(this)
                }
            }

            etSignUpEmail.apply {
                addTextChangedListener(textWatcher)

                setOnEditorActionListener { _, editorInfo, _ ->
                    when (editorInfo) {
                        EditorInfo.IME_ACTION_DONE -> clearFocus()
                    }

                    false
                }
            }

            btnSignUp.apply {
                setOnClickListener {
                    Intent(this@SignUpActivity, SignUpVerificationActivity::class.java).also {
                        it.apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

                            putExtra("email", text.toString())
                            startActivity(this)
                        }
                    }
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

}