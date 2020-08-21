package com.zp.mydsl.dsl_one

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class DslTextWatcher(editText: EditText) {

    private var onTextChanged: (CharSequence?.() -> Unit)? = null
    private var afterChanged: (Editable?.() -> Unit)? = null
    private var beforeChange: (CharSequence?.() -> Unit)? = null

    fun textChanged(onTextChanged: CharSequence?.() -> Unit) {
        this.onTextChanged = onTextChanged
    }

    fun afterChanged(afterChanged: Editable?.() -> Unit) {
        this.afterChanged = afterChanged
    }

    fun beforeChange(beforeChange: CharSequence?.() -> Unit) {
        this.beforeChange = beforeChange
    }

    init {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterChanged?.invoke(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforeChange?.invoke(s)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged?.invoke(s)
            }
        })

    }
}