package com.zp.mydsl.dsl_two.tradition

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zp.mydsl.dsl_two.throwNull

class EasyAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas = mutableListOf<T>()
        set(value) {
            clear()
            if (!value.isNullOrEmpty()) {
                if (field.addAll(value)) {
                    notifyDataSetChanged()
                }
            }
        }

    private var viewHolders = SparseArray<ViewHolderCreator<T>>()

    private fun getViewHolderForType(viewType: Int): ViewHolderCreator<T>? {
        return viewHolders[viewType]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = getViewHolderForType(viewType)
        if (viewHolder == null) throwNull("viewHolder is Null")
        return BaseViewHolder(parent, viewHolder!!.getResId())
    }

    override fun getItemViewType(position: Int): Int {
        if (datas.isNullOrEmpty()) throwNull("datas is Null")
        for (i in 0 until viewHolders.size()) {
            val holder = viewHolders[i]
            val item = datas[position]
            if (holder.isForViewType(item, position)) {
                return viewHolders.keyAt(i)
            }
        }
        throw NullPointerException("Can't find viewType by position and item")
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = getViewHolderForType(holder.itemViewType)
        if (viewHolder == null) throwNull("viewHolder is Null")
        viewHolder!!.init(holder.itemView)
        viewHolder.bindView(getItem(position), position, viewHolder)
    }

    fun addViewHolder(holder: ViewHolderCreator<T>) : EasyAdapter<T> {
        var viewType = viewHolders.size()
        while (viewHolders[viewType] != null) {
            viewType ++
        }
        viewHolders.put(viewType, holder)
        return this
    }

    fun bindRecyclerView(recyclerView: RecyclerView) : EasyAdapter<T> {
        recyclerView.adapter = this
        return this
    }

    fun getItem(position: Int) = datas[position]

    open fun addAll(list: MutableList<T>) {
        val oldSize = itemCount
        if (datas.addAll(list)) {
            notifyItemRangeChanged(oldSize, list.size)
        }
    }

    open fun setItem(position: Int, t: T) {
        if (itemCount > 0) {
            datas[position] = t
            notifyItemChanged(position)
        }
    }

    open fun remove(position: Int, changeDataNow: Boolean = true) {
        if (itemCount > 0) {
            datas.removeAt(position)
            if (changeDataNow) {
                notifyItemRangeRemoved(position, 1)
            }
        }
    }

    open fun clear(changeDataNow: Boolean = true) {
        val oldCount = itemCount
        datas.clear()
        if (changeDataNow) {
            notifyItemRangeRemoved(0, oldCount)
        }
    }
}