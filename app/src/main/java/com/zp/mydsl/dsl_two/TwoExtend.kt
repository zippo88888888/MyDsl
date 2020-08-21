package com.zp.mydsl.dsl_two

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zp.mydsl.dsl_one.log
import com.zp.mydsl.dsl_two.dsl.DslAdapter
import com.zp.mydsl.dsl_two.dsl.DslViewHolder
import com.zp.mydsl.dsl_two.tradition.EasyAdapter

fun throwNull(msg: String) {
    throw NullPointerException(msg)
}

fun Any.showToast(msg: String) {
    when (this) {
        is Context -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        is Fragment -> {
            Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
        }
        is View -> {
            Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
        }
        else -> {
            log(msg)
        }
    }
}

fun <T> easyAdapter(block: EasyAdapter<T>.() -> Unit): EasyAdapter<T> {
    val adapter = EasyAdapter<T>()
    block.invoke(adapter)
    return adapter
}

fun <T> EasyAdapter<T>.addItem(layoutId: Int, block: DslViewHolder<T>.() -> Unit) {
    val dslViewHolder = DslViewHolder<T>(layoutId)
    dslViewHolder.block()
    addViewHolder(dslViewHolder)
}

fun <T> RecyclerView.setData(block: DslAdapter<T>.() -> Unit): DslAdapter<T> {
    val adapter = DslAdapter<T>(this)
    block.invoke(adapter)
    if (layoutManager == null) {
        layoutManager = LinearLayoutManager(context)
    }
    return adapter
}


fun <T> RecyclerView.setup(block: DslAdapter<T>.() -> Unit): DslAdapter<T> {
    val adapter = DslAdapter<T>(this)
    adapter.bindAdapter()
    block.invoke(adapter)
    if (layoutManager == null) {
        layoutManager = LinearLayoutManager(context)
    }
    return adapter
}

fun <T> DslAdapter<T>.addItem(layoutId: Int, block: DslViewHolder<T>.() -> Unit) {
    val dslViewHolder = DslViewHolder<T>(layoutId)
    block.invoke(dslViewHolder)
    addViewHolder(dslViewHolder)
}

fun <T> DslAdapter<T>.bindData(block: () -> MutableList<T>) {
    setData(block.invoke())
}

fun <T> DslAdapter<T>.layoutManager(manager: DslAdapter<T>.() -> RecyclerView.LayoutManager) {
    bindLayoutManager(manager)
}