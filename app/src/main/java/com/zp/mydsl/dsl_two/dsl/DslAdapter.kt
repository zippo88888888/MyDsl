package com.zp.mydsl.dsl_two.dsl

import androidx.recyclerview.widget.RecyclerView
import com.zp.mydsl.dsl_two.tradition.EasyAdapter
import com.zp.mydsl.dsl_two.tradition.ViewHolderCreator

class DslAdapter<T>(private var recyclerView: RecyclerView) {

    var adapter: EasyAdapter<T>? = null

    fun bindLayoutManager(manager: DslAdapter<T>.() -> RecyclerView.LayoutManager) {
        recyclerView.layoutManager = manager()
    }

    fun bindAdapter(init: EasyAdapter<T>.() -> Unit) {
        adapter = EasyAdapter()
        init.invoke(adapter!!)
        adapter?.bindRecyclerView(recyclerView)
    }

    fun bindAdapter() {
        adapter = EasyAdapter()
        adapter?.bindRecyclerView(recyclerView)
    }

    fun addViewHolder(holder: ViewHolderCreator<T>) {
        adapter?.addViewHolder(holder)
    }

    fun setData(list: MutableList<T>) {
        adapter?.datas = list
    }
}