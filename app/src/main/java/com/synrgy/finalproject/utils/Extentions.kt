package com.synrgy.finalproject.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings


fun AppCompatActivity.setActionBarTitle(
    toolbar: Toolbar,
    title: String? = null,
) {
    setSupportActionBar(toolbar)
    if (title != null) {
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    } else {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
fun ViewBinding.setButtonEnabled(enabled: Boolean) {
    root.isEnabled = enabled
}