package com.synrgy.finalproject.ui.auth.login.password.newpassword

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.finalproject.databinding.ActivityNewPasswordBinding
import com.synrgy.finalproject.ui.auth.login.password.passwordchanged.PasswordChangedActivity

class NewPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPasswordBinding

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            binding.apply {
                val password = etNewPassword.text.toString().trim()
                val confirmPassword = etNewPasswordConfirm.text.toString().trim()

                btnNewPasswordSave.isEnabled =
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

        binding = ActivityNewPasswordBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            llNewPasswordBtnBack.setOnClickListener {
                onBackPressed()
            }

            etNewPassword.addTextChangedListener(textWatcher)

            etNewPasswordConfirm.apply {
                addTextChangedListener(textWatcher)

                setOnEditorActionListener { _, editorInfo, _ ->
                    when (editorInfo) {
                        EditorInfo.IME_ACTION_DONE -> clearFocus()
                    }

                    false
                }
            }

            btnNewPasswordSave.setOnClickListener {
                Intent(this@NewPasswordActivity, PasswordChangedActivity::class.java).apply {
                    startActivity(this)
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
            tilNewPasswordPassword.error = null
            tilNewPasswordConfirmPassword.error = null
        }
    }

}