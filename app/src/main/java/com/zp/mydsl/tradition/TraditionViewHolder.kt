package com.zp.mydsl.tradition

import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TraditionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var array: SparseArray<View> = SparseArray()

    fun <V : View> getView(id: Int): V {
        var view: View? = array.get(id)
        if (view == null) {
            view = itemView.findViewById(id)
            array.put(id, view)
        }
        return view as V
    }

    fun setText(id: Int, msg: String) {
        val txtView = getView<TextView>(id)
        txtView.text = msg
    }

    fun setImageRes(id: Int, res: Int) {
        val pic = getView<ImageView>(id)
        pic.setImageResource(res)
    }

    fun setVisibility(id: Int, visibility: Int) {
        getView<View>(id).visibility = visibility
    }

    fun setVisibility(id: Int, isVisibility: Boolean) {
        if (isVisibility) setVisibility(id, View.VISIBLE) else setVisibility(id, View.GONE)
    }

    fun setOnViewClickListener(id: Int, listener: View.() -> Unit) {
        getView<View>(id).setOnClickListener {
            listener.invoke(it)
        }
    }

    fun setOnItemClickListener(listener: View.() -> Unit) {
        itemView.setOnClickListener {
            listener.invoke(it)
        }
    }

    fun setOnItemLongClickListener(listener: View.() -> Boolean) {
        itemView.setOnLongClickListener {
            listener.invoke(it)
        }
    }

}