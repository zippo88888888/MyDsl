package com.zp.mydsl.tradition

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class TraditionAdapter<T>(protected var context: Context) : RecyclerView.Adapter<TraditionViewHolder>() {

    constructor(context: Context, layoutID: Int) : this(context) {
        this.resId = layoutID
    }

    private var resId = -1

    var datas: MutableList<T>? = ArrayList()
        set(value) {
            clear()
            if (!value.isNullOrEmpty()) {
                if (field?.addAll(value) == true) {
                    notifyDataSetChanged()
                }
            }
        }

    open fun addAll(list: MutableList<T>) {
        val oldSize = itemCount
        if (datas?.addAll(list) == true) {
            notifyItemRangeChanged(oldSize, list.size)
        }
    }

    open fun addItem(position: Int, t: T) {
        datas?.add(position, t)
        notifyItemInserted(position)
    }

    open fun setItem(position: Int, t: T) {
        if (itemCount > 0) {
            datas!![position] = t
            notifyItemChanged(position)
        }
    }

    open fun remove(position: Int, changeDataNow: Boolean = true) {
        if (itemCount > 0) {
            datas?.removeAt(position)
            if (changeDataNow) {
                notifyItemRangeRemoved(position, 1)
            }
        }
    }

    open fun clear(changeDataNow: Boolean = true) {
        datas?.clear()
        if (changeDataNow) {
            notifyItemRangeRemoved(0, itemCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraditionViewHolder {
        val layoutRes = getLayoutID(viewType)
        if (layoutRes > 0) {
            val view = LayoutInflater.from(context).inflate(layoutRes, parent, false)
            return TraditionViewHolder(view)
        } else {
            throw NullPointerException("adapter layoutId is not null")
        }
    }

    override fun onBindViewHolder(holder: TraditionViewHolder, position: Int) {
        bindView(holder, getItem(position), position)
    }

    override fun getItemCount() = datas?.size ?: 0

    fun getItem(position: Int): T? = datas?.get(position)

    open fun getLayoutID(viewType: Int) = resId

    protected abstract fun bindView(holder: TraditionViewHolder, item: T?, position: Int)
}