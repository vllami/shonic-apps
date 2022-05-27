package com.synrgy.finalproject.auth.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.synrgy.finalproject.R
import com.synrgy.finalproject.auth.login.LogInActivity
import com.synrgy.finalproject.databinding.ActivityCompleteRegisterBinding
import com.synrgy.finalproject.utils.BaseMessage
import com.synrgy.finalproject.utils.setActionBarTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompleteRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompleteRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBarTitle(binding.toolbar, getString(R.string.register_complete))
        onToolbarNavigationClicked()
        clearFocus()
        onButtonClicked()
    }


    private fun onToolbarNavigationClicked() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun clearFocus() {
        binding.etRegisterConfirmPassword.setOnEditorActionListener { _, editorInfo, _ ->
            when (editorInfo) {
                EditorInfo.IME_ACTION_DONE -> binding.etRegisterConfirmPassword.clearFocus()
            }
            false
        }
    }

    private fun onButtonClicked() {
        binding.btnRegisterCompleted.setOnClickListener {
            val fullName = binding.etRegisterFullName.text.toString()
            val password = binding.etRegisterPassword.text.toString()
            val confirmPassword = binding.etRegisterConfirmPassword.text.toString()
            if (!isAllFieldsFilled(fullName, password, confirmPassword)) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.error_field_required),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                if (!isPasswordMatch(password, confirmPassword)) {
                    binding.tfRegisterConfirmPassword.error =
                        getString(R.string.error_password_not_match)
                } else {
                    Intent(this, LogInActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(this)
                        finish()
                    }
                }
            }
        }
    }

    private fun isAllFieldsFilled(
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        resetError()
        if (username.isEmpty()) {
            return false
        } else if (password.isEmpty()) {
            return false
        } else if (confirmPassword.isEmpty()) {
            return false
        }
        return true

    }

    private fun isPasswordMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    private fun resetError() {
        with(binding) {
            tfRegisterFullName.error = null
            tfRegisterPassword.error = null
            tfRegisterConfirmPassword.error = null
        }
    }

}