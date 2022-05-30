package com.synrgy.finalproject.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.google.android.material.textfield.TextInputEditText


fun AppCompatActivity.setActionBarTitle(
    toolbar: Toolbar,
    title: String? = null,
) {
    setSupportActionBar(toolbar)
    if (title != null) {
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    } else {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}

fun ViewBinding.setButtonEnabled(enabled: Boolean) {
    root.isEnabled = enabled
}

fun TextInputEditText.addTextWatcher(textWatcher: TextWatcher) {
    addTextChangedListener(textWatcher)
    setOnEditorActionListener { _, i, _ ->
        when (i) {
            EditorInfo.IME_ACTION_DONE -> clearFocus()
        }
        false
    }
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        when {
            hasFocus -> selectAll()
        }
    }
}

// addtextChangedListener extension function

fun TextInputEditText.addTextChanged(text: TextInputEditText) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            when (p0.toString().length) {
                1 -> text.requestFocus()
            }
        }
        override fun afterTextChanged(p0: Editable?) {}

    })
}