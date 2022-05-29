package com.synrgy.finalproject.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.synrgy.finalproject.R

object BaseMessage {
    // Toast
    fun shortToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun longToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    // Snackbar
    fun shortSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
            context.apply {
                setTextColor(getColor(R.color.white))
                setBackgroundTint(getColor(R.color.dark_gray))
            }
            animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
            anchorView = view
            show()
        }
    }

    fun errorShortSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
            context.apply {
                setTextColor(getColor(R.color.white))
                setBackgroundTint(getColor(R.color.dark_gray))
            }
            animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
            anchorView = view
            show()
        }
    }

    fun longSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
            context.apply {
                setTextColor(getColor(R.color.white))
                setBackgroundTint(getColor(R.color.dark_gray))
            }
            animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
            anchorView = view
            show()
        }
    }

    fun errorLongSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
            context.apply {
                setTextColor(getColor(R.color.white))
                setBackgroundTint(getColor(R.color.dark_gray))
            }
            animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
            anchorView = view
            show()
        }
    }
}