package com.synrgy.finalproject.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.synrgy.finalproject.R
import com.synrgy.finalproject.databinding.ActivityCompleteSignUpBinding
import com.synrgy.finalproject.ui.auth.login.LogInActivity
import com.synrgy.finalproject.utils.BaseMessage
import com.synrgy.finalproject.utils.disable
import com.synrgy.finalproject.utils.gone
import com.synrgy.finalproject.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CompleteSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompleteSignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompleteSignUpBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)
            val email = intent?.getStringExtra(Intent.EXTRA_EMAIL)
            viewModel.stateComplete.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).onEach { state ->
                when (state) {
                    is SignUpViewModel.CompleteSignUpState.Init -> Unit
                    is SignUpViewModel.CompleteSignUpState.Loading -> {
                        pbCompleteSignUp.visible()
                        btnCompleteSignUpDone.disable()
                    }
                    is SignUpViewModel.CompleteSignUpState.Error -> {
                        pbCompleteSignUp.gone()
                        BaseMessage.errorShortSnackbar(root, state.message)
                    }
                    is SignUpViewModel.CompleteSignUpState.Success -> {
                        pbCompleteSignUp.gone()
                        Intent(this@CompleteSignUpActivity, LogInActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }
                    }
                }
            }.launchIn(lifecycleScope)

            llCompleteSignUpBtnBack.setOnClickListener {
                Intent(this@CompleteSignUpActivity, SignUpActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(this)
                }
            }

            btnCompleteSignUpDone.setOnClickListener {
                resetError()
                val fullName = etCompleteSignUpFullName.text.toString().trim()
                val password = etCompleteSignUpFullName.text.toString().trim()
                val confirmPassword = etCompleteSignUpFullName.text.toString().trim()
                if (fullName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    BaseMessage.errorShortSnackbar(root, "Please fill all the fields")
                }else if (!isPasswordMatch(password, confirmPassword)) {
                    tilCompleteSignUpConfirmPassword.error = resources.getString(R.string.error_password_not_match)
                }else {
                    email?.let { email ->
                        viewModel.completeSignUp(email, fullName, password)
                    }
                }
            }

        }
    }


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