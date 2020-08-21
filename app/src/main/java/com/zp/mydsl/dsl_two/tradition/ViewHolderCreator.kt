package com.zp.mydsl.dsl_two.tradition

import android.view.View
import android.widget.ImageView
import android.widget.TextView

abstract class ViewHolderCreator<T> {

    abstract fun getResId(): Int

    abstract fun isForViewType(item: T, position: Int): Boolean

    abstract fun bindView(item: T, position: Int, holder: ViewHolderCreator<T>)

    private var itemView: View? = null

    fun init(itemView: View) {
        this.itemView = itemView
    }

    fun <V : View> findViewById(id: Int): V {
        checkItemView()
        return itemView!!.findViewById(id)
    }

    private fun checkItemView() {
        if (itemView == null) {
            throw NullPointerException("itemView is Null")
        }
    }

    fun setText(viewId: Int, value: String) {
        val textView = findViewById<TextView>(viewId)
        textView.text = value
    }

    fun setImageRes(viewId: Int, resId: Int) {
        val imageView = findViewById<ImageView>(viewId)
        imageView.setImageResource(resId)
    }

    fun setOnClick(viewId: Int, click: View.() -> Unit) {
        findViewById<View>(viewId).setOnClickListener {
            click.invoke(it)
        }
    }

    fun setOnLongClick(viewId: Int, click: View.() -> Boolean) {
        findViewById<View>(viewId).setOnLongClickListener {
            click.invoke(it)
        }
    }
}