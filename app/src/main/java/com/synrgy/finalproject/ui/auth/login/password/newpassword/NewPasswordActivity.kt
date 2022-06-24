package com.synrgy.finalproject.ui.auth.login.password.newpassword

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hafidh.domain.common.Event
import com.synrgy.finalproject.databinding.ActivityNewPasswordBinding
import com.synrgy.finalproject.ui.auth.login.password.ForgotPasswordViewModel
import com.synrgy.finalproject.ui.auth.login.password.passwordchanged.PasswordChangedActivity
import com.synrgy.finalproject.utils.Constants.EXTRA_EMAIL_FORGOT_PASSWORD
import com.synrgy.finalproject.utils.Constants.EXTRA_TOKEN_FORGOT_PASSWORD
import com.synrgy.finalproject.utils.gone
import com.synrgy.finalproject.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NewPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPasswordBinding
    private val viewModel by viewModels<ForgotPasswordViewModel>()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            binding.apply {
                resetError()
                val password = etNewPassword.text.toString().trim()
                val confirmPassword = etNewPasswordConfirm.text.toString().trim()

                btnNewPasswordSave.isEnabled =
                    password.isNotEmpty() &&
                            confirmPassword.isNotEmpty()
            }
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewPasswordBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            val email = intent.getStringExtra(EXTRA_EMAIL_FORGOT_PASSWORD)
            val token = intent.getStringExtra(EXTRA_TOKEN_FORGOT_PASSWORD)

            viewModel.stateNewPassword.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).onEach { event ->
                when (event) {
                    is Event.Init -> Unit
                    is Event.Loading -> {
                        pbNewPasswordVerification.visible()
                    }
                    is Event.Error -> {
                        pbNewPasswordVerification.gone()
                        tvErrorMessageNewPassword.visible()
                        tvErrorMessageNewPassword.text = event.error
                    }
                    is Event.Success -> {
                        pbNewPasswordVerification.gone()
                        Intent(this@NewPasswordActivity, PasswordChangedActivity::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(it)
                        }
                    }
                }
            }.launchIn(lifecycleScope)

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
                if (isPasswordMatch(
                        password = binding.etNewPassword.text.toString(),
                        confirmPassword = binding.etNewPasswordConfirm.text.toString()
                    ) && email != null && token != null
                ) {
                    viewModel.newPassword(
                        email,
                        newPassword = binding.etNewPassword.text.toString(),
                        token = token
                    )
                } else {
                    tilNewPasswordPassword.error = "Password tidak sama"
                }
            }
        }
    }


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