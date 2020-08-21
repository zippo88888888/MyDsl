package com.zp.mydsl.dsl_one

import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

fun EditText.textWatcher(
    textWatcher: DslTextWatcher.() -> Unit
): EditText {
    val dslTextWatcher = DslTextWatcher(this)
    textWatcher.invoke(dslTextWatcher)
    return this
}

fun <T> RecyclerView.setData() {

}

fun log(msg: String) {
    Log.i("app", msg)
}