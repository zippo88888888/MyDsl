package com.zp.mydsl.dsl_three

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zp.mydsl.content.RecyclerViewDivider
import com.zp.mydsl.dsl_one.log

class QuickAdapter<T>(resId: Int) : BaseQuickAdapter<T, BaseViewHolder>(resId), BaseQuickAdapter.OnItemClickListener,
    BaseQuickAdapter.OnItemLongClickListener, BaseQuickAdapter.OnItemChildClickListener {

    init {
        onItemClickListener = this
        onItemLongClickListener = this
        onItemChildClickListener = this
    }

    private var bindView: ((item: T, position: Int, holder: BaseViewHolder) -> Unit)? = null
    private var itemClick: ((item: T, position: Int, view: View) -> Unit)? = null
    private var itemLongClick: ((item: T, position: Int, view: View) -> Boolean)? = null
    private var addChildViewClick: (() -> IntArray)? = null
    private var itemChildViewClick: ((item: T, position: Int, view: View) -> Unit)? = null

    fun bindView(bindView: (item: T, position: Int, holder: BaseViewHolder) -> Unit) {
        this.bindView = bindView
    }

    fun itemClick(itemClick: (item: T, position: Int, view: View) -> Unit) {
        this.itemClick = itemClick
    }

    fun itemLongClick(itemLongClick: (item: T, position: Int, view: View) -> Boolean) {
        this.itemLongClick = itemLongClick
    }

    fun addChildViewClick(addChildViewClick: () -> IntArray) {
        this.addChildViewClick = addChildViewClick
    }

    fun itemChildViewClick(itemChildViewClick: (item: T, position: Int, view: View) -> Unit) {
        this.itemChildViewClick = itemChildViewClick
    }

    override fun convert(helper: BaseViewHolder, item: T) {
        val position = helper.layoutPosition
        bindView?.invoke(item, position, helper)
        val ids = addChildViewClick?.invoke()
        if (ids != null) {
            helper.addOnClickListener(*ids)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val item = getItem(position)
        if (item == null || view == null) {
            return
        }
        itemClick?.invoke(item, position, view)
    }

    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int): Boolean {
        val item = getItem(position)
        if (item == null || view == null) {
            return true
        }
        return itemLongClick?.invoke(item, position, view) ?: true
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val item = getItem(position)
        if (item == null || view == null) {
            return
        }
        itemChildViewClick?.invoke(item, position, view)
    }
}

class DslQuickAdapter<T>(private var recyclerView: RecyclerView) {

    var adapter: QuickAdapter<T>? = null

    fun init(resId: Int) {
        adapter = QuickAdapter(resId)
        if (recyclerView.layoutManager == null) {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        }
        recyclerView.adapter = adapter
    }

    fun bindLayoutManager(block: DslQuickAdapter<T>.() -> RecyclerView.LayoutManager) {
        recyclerView.layoutManager = block()
    }

    fun bindItemDecoration(block: DslQuickAdapter<T>.() -> MutableList<RecyclerView.ItemDecoration>) {
        block().forEach {
            recyclerView.addItemDecoration(it)
        }
    }

    fun bindDataView(block: QuickAdapter<T>.() -> Unit) {
        block.invoke(adapter!!)
    }

}

fun <T> RecyclerView.quick(resId: Int, block: DslQuickAdapter<T>.() -> Unit): DslQuickAdapter<T> {
    val dslQuickAdapter = DslQuickAdapter<T>(this)
    dslQuickAdapter.init(resId)
    block.invoke(dslQuickAdapter)
    return dslQuickAdapter
}

fun <T> DslQuickAdapter<T>.bind(block: QuickAdapter<T>.() -> Unit) {
    bindDataView(block)
}

fun  <T> DslQuickAdapter<T>.layoutManger(block: DslQuickAdapter<T>.() -> RecyclerView.LayoutManager) {
    bindLayoutManager(block)
}

fun  <T> DslQuickAdapter<T>.itemDecoration(block: DslQuickAdapter<T>.() -> MutableList<RecyclerView.ItemDecoration>) {
    bindItemDecoration(block)
}

fun <T> DslQuickAdapter<T>.data(block: DslQuickAdapter<T>.() -> MutableList<T>) {
    adapter?.setNewData(block())
}

fun <T> DslQuickAdapter<T>.addHeader(block: DslQuickAdapter<T>.() -> MutableList<View>) {
    block().forEach {
        adapter?.addHeaderView(it)
    }
}

fun <T> DslQuickAdapter<T>.addFooter(block: DslQuickAdapter<T>.() -> MutableList<View>) {
    block().forEach {
        adapter?.addFooterView(it)
    }
}




