package com.devtech.islamicdictionary.utils

import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar

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


