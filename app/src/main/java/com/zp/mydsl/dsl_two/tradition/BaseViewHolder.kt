package com.zp.mydsl.dsl_two.tradition

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder(parent: ViewGroup, resource: Int) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(resource, parent, false)
)