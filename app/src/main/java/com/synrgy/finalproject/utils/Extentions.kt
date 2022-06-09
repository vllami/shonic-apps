package com.synrgy.finalproject.utils

import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

fun ProgressBar.visible() {
    visibility = View.VISIBLE
}

fun ProgressBar.gone() {
    visibility = View.GONE
}

fun TextView.visible() {
    visibility = View.VISIBLE
}
fun TextView.gone() {
    visibility = View.GONE
}

fun Button.enable() {
    isEnabled = true
}

fun Button.disable() {
    isEnabled = false
}

fun Context.alert(
    @StyleRes style: Int = 0,
    dialogBuilder: MaterialAlertDialogBuilder.() -> Unit
) {
    MaterialAlertDialogBuilder(this, style)
        .apply {
            setCancelable(false)
            dialogBuilder()
            create()
            show()
        }
}

fun MaterialAlertDialogBuilder.negativeButton(
    text: String = "No",
    handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() }
) {
    this.setNegativeButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
}

fun MaterialAlertDialogBuilder.positiveButton(
    text: String = "Yes",
    handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() }
) {
    this.setPositiveButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
}

fun MaterialAlertDialogBuilder.title(title: String) {
    this.setTitle(title)
}

fun MaterialAlertDialogBuilder.message(message: String) {
    this.setMessage(message)
}