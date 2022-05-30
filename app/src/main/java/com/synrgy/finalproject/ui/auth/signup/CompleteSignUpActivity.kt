package com.synrgy.finalproject.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivityCompleteSignUpBinding
import com.synrgy.finalproject.ui.auth.login.LogInActivity
import com.synrgy.finalproject.ui.auth.verification.SignUpVerificationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompleteSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompleteSignUpBinding

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            binding.apply {
                val fullName = etCompleteSignUpFullName.text.toString().trim()
                val password = etCompleteSignUpPassword.text.toString().trim()
                val confirmPassword = etCompleteSignUpConfirmPassword.text.toString().trim()

                btnCompleteSignUpDone.isEnabled =
                    fullName.isNotEmpty() &&
                    password.isNotEmpty() &&
                    // isValidPassword(password) &&
                    confirmPassword.isNotEmpty() &&
                    isPasswordMatch(password, confirmPassword)
            }
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompleteSignUpBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            llCompleteSignUpBtnBack.setOnClickListener {
                Intent(this@CompleteSignUpActivity, SignUpVerificationActivity::class.java).apply {
                    startActivity(this)
                }
            }

            etCompleteSignUpFullName.addTextChangedListener(textWatcher)

            etCompleteSignUpPassword.addTextChangedListener(textWatcher)

            etCompleteSignUpConfirmPassword.apply {
                addTextChangedListener(textWatcher)

                setOnEditorActionListener { _, editorInfo, _ ->
                    when (editorInfo) {
                        EditorInfo.IME_ACTION_DONE -> clearFocus()
                    }

                    false
                }
            }

            btnCompleteSignUpDone.apply {
                setOnClickListener {
                    val password = etCompleteSignUpPassword.text.toString()
                    val confirmPassword = etCompleteSignUpConfirmPassword.text.toString()

                    when {
                        !isPasswordMatch(password, confirmPassword) -> {
                            tilCompleteSignUpConfirmPassword.error = getString(R.string.error_password_not_match)
                        }
                        else -> {
                            Intent(this@CompleteSignUpActivity, LogInActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                                startActivity(this)
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isAllFieldsFilled(username: String, password: String, confirmPassword: String): Boolean {
        resetError()

        return when {
            username.isEmpty() -> false
            password.isEmpty() -> false
            confirmPassword.isEmpty() -> false
            else -> true
        }
    }

    // private fun isValidPassword(password: String?): Boolean {
    //     password?.let {
    //         val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,20}$"
    //         val passwordMatcher = Regex(passwordPattern)
    //
    //         return passwordMatcher.find(password) != null
    //     } ?: return false
    // }

    private fun isPasswordMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    private fun resetError() {
        with(binding) {
            tilCompleteSignUpFullName.error = null
            tilCompleteSignUpPassword.error = null
            tilCompleteSignUpConfirmPassword.error = null
        }
    }

}