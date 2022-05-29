package com.synrgy.finalproject.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding


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