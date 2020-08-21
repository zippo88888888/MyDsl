package com.zp.mydsl.dsl_two.dsl

import com.zp.mydsl.dsl_two.tradition.ViewHolderCreator

class DslViewHolder<T>(private var resId: Int) : ViewHolderCreator<T>() {

    private var viewType: ((item: T, position: Int) -> Boolean)? = null
    private var bindView: ((item: T, position: Int, holder: ViewHolderCreator<T>) -> Unit)? = null

    fun viewType(viewType: (item: T, position: Int) -> Boolean) {
        this.viewType = viewType
    }

    fun bindView(bindView: (item: T, position: Int, holder: ViewHolderCreator<T>) -> Unit) {
        this.bindView = bindView
    }

    override fun getResId() = resId

    override fun isForViewType(item: T, position: Int) = viewType?.invoke(item, position) ?: true

    override fun bindView(item: T, position: Int, holder: ViewHolderCreator<T>) {
        bindView?.invoke(item, position, holder)
    }
}