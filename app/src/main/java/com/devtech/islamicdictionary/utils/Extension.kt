package com.devtech.islamicdictionary.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun View.snackbar(message:String) = Snackbar.make(this,message,Snackbar.LENGTH_SHORT).show()

fun AppCompatActivity.toogleActionbar(show: Boolean, back: Boolean, title: String?) {
    if (show) {
        supportActionBar?.show()
        supportActionBar?.title = title
    } else {
        supportActionBar?.hide()
    }
    if (back) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }else{
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun TextInputLayout.inputError(data: String, message: String?): Boolean {
    return if (data.isEmpty()) {
        error = message
        false
    } else {
        error = null
        true
    }
}

fun TextInputEditText.clearInput(
    inputLayout: TextInputLayout
) {
    this.setOnFocusChangeListener { _, hasFoccus ->
        if (hasFoccus) {
            inputLayout.error = null
        }
    }
}


