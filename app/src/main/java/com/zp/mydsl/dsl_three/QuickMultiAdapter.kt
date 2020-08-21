package com.zp.mydsl.dsl_three

import android.view.View
import androidx.annotation.LayoutRes
import androidx.collection.ArrayMap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zp.mydsl.R
import com.zp.mydsl.dsl_one.log

data class MultitemType(
    var type: Int = -1,
    @LayoutRes var layoutResId: Int = -1
)

class QuickMultiAdapter<T : MultiItemEntity> : BaseMultiItemQuickAdapter<T, BaseViewHolder>(null),
    BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener,
    BaseQuickAdapter.OnItemChildClickListener {

    init {
        onItemClickListener = this
        onItemLongClickListener = this
        onItemChildClickListener = this
    }

    private var itemMap = ArrayMap<Int, Int>()

    private var bindView: ((item: T, position: Int, holder: BaseViewHolder) -> Unit)? = null
    private var itemClick: ((item: T, position: Int, view: View) -> Unit)? = null
    private var itemLongClick: ((item: T, position: Int, view: View) -> Boolean)? = null
    private var addChildViewClick: (() -> IntArray)? = null
    private var itemChildViewClick: ((item: T, position: Int, view: View) -> Unit)? = null

    fun addViewType(addViewType: () -> MultitemType) {
        val mulitemType = addViewType()
        require(!(mulitemType.type == -1 || mulitemType.layoutResId == -1)) { "MultitemType error" }
        if (!itemMap.contains(mulitemType.type)) {
            itemMap[mulitemType.type] = mulitemType.layoutResId
            addItemType(mulitemType.type, mulitemType.layoutResId)
        }
    }

    fun addViewTypes(addViewType: () -> MutableList<MultitemType>) {
        addViewType().forEach {
            addViewType {
                it
            }
        }
    }

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

    override fun onItemLongClick(
        adapter: BaseQuickAdapter<*, *>?,
        view: View?,
        position: Int
    ): Boolean {
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

class DslQuickMultiAdapter<T : MultiItemEntity>(private var recyclerView: RecyclerView) {

    var adapter: QuickMultiAdapter<T>? = null

    fun init() {
        adapter = QuickMultiAdapter()
        if (recyclerView.layoutManager == null) {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        }
        recyclerView.adapter = adapter
    }

    fun bindLayoutManager(block: DslQuickMultiAdapter<T>.() -> RecyclerView.LayoutManager) {
        recyclerView.layoutManager = block()
    }

    fun bindItemDecoration(block: DslQuickMultiAdapter<T>.() -> MutableList<RecyclerView.ItemDecoration>) {
        block().forEach {
            recyclerView.addItemDecoration(it)
        }
    }

    fun bindDataView(block: QuickMultiAdapter<T>.() -> Unit) {
        block.invoke(adapter!!)
    }

}

fun <T : MultiItemEntity> RecyclerView.quickMulti(block: DslQuickMultiAdapter<T>.() -> Unit): DslQuickMultiAdapter<T> {
    val dslQuickAdapter = DslQuickMultiAdapter<T>(this)
    dslQuickAdapter.init()
    block.invoke(dslQuickAdapter)
    return dslQuickAdapter
}

fun <T : MultiItemEntity> DslQuickMultiAdapter<T>.bind(block: QuickMultiAdapter<T>.() -> Unit) {
    bindDataView(block)
}

fun  <T : MultiItemEntity> DslQuickMultiAdapter<T>.layoutManger(block: DslQuickMultiAdapter<T>.() -> RecyclerView.LayoutManager) {
    bindLayoutManager(block)
}

fun  <T : MultiItemEntity> DslQuickMultiAdapter<T>.itemDecoration(block: DslQuickMultiAdapter<T>.() -> MutableList<RecyclerView.ItemDecoration>) {
    bindItemDecoration(block)
}

fun <T : MultiItemEntity> DslQuickMultiAdapter<T>.data(block: DslQuickMultiAdapter<T>.() -> MutableList<T>) {
    adapter?.setNewData(block())
}

fun <T : MultiItemEntity> DslQuickMultiAdapter<T>.addHeader(block: DslQuickMultiAdapter<T>.() -> MutableList<View>) {
    block().forEach {
        adapter?.addHeaderView(it)
    }
}

fun <T : MultiItemEntity> DslQuickMultiAdapter<T>.addFooter(block: DslQuickMultiAdapter<T>.() -> MutableList<View>) {
    block().forEach {
        adapter?.addFooterView(it)
    }
}
