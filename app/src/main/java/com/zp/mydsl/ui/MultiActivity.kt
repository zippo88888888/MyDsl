package com.zp.mydsl.ui

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zp.mydsl.R
import com.zp.mydsl.content.DataUtil
import com.zp.mydsl.content.People
import com.zp.mydsl.content.RecyclerViewDivider
import com.zp.mydsl.dsl_three.*
import com.zp.mydsl.dsl_two.showToast
import kotlinx.android.synthetic.main.activity_dsl1.*

class MultiActivity : AppCompatActivity() {

    private var handler: Handler? = null
    private var dialog: ProgressDialog? = null

    private var quickAdapter: QuickMultiAdapter<People>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dsl1)
        dslBtn.setOnClickListener {
            getTestData {
                if (quickAdapter?.data.isNullOrEmpty()) {
                    quickAdapter?.setNewData(this)
                } else {
                    quickAdapter?.addData(this)
                }
            }
        }
        handler = Handler()
        dialog = ProgressDialog(this).run {
            setTitle("wait")
            setMessage("request data...")
            setCancelable(false)
            this
        }

        quickAdapter = dsl1RecyclerView.quickMulti<People> {
            layoutManger {
                LinearLayoutManager(this@MultiActivity)
            }
            itemDecoration{
                arrayListOf(RecyclerViewDivider.getDefaultDivider(this@MultiActivity))
            }
            addHeader {
                arrayListOf(getView(100), getView(100, "#008577"), getView(100))
            }
            addFooter {
                arrayListOf(getView(200), getView(200, "#008577"), getView(200))
            }
            bind {
                addViewType {
                    MultitemType(1, R.layout.item_student)
                }
                addViewType {
                    MultitemType(2, R.layout.item_teacher)
                }
                itemClick { item, position, view ->
                    view.showToast("itemClick：${item.name}===>>>$position")
                }
                itemLongClick { item, position, view ->
                    view.showToast("itemLongClick：${item.name}===>>>$position")
                    true
                }
                addChildViewClick {
                    intArrayOf(R.id.item_student_nameTxt, R.id.item_student_classTxt)
                }
                itemChildViewClick { item, position, view ->
                    when (view.id) {
                        R.id.item_student_nameTxt -> view.showToast("nameClick：${item.name}===>>>$position")
                        R.id.item_student_classTxt -> view.showToast("classClick：${item.name}===>>>$position")
                    }
                }
                bindView { item, _, holder ->
                    when (holder.itemViewType) {
                        1 -> {
                            holder.setText(R.id.item_student_nameTxt, item.name)
                            holder.setText(R.id.item_student_classTxt, item.age.toString())
                        }
                        2 -> {
                            holder.setText(R.id.item_teacher_nameTxt, item.name)
                            holder.setText(R.id.item_teacher_classTxt, item.age.toString())
                        }
                    }
                }
            }
            data {
                DataUtil.getPeopleList()
            }
        }.adapter
    }

    private fun getTestData(block: MutableList<People>.() -> Unit) {
        dialog?.show()
        handler?.postDelayed({
            block.invoke(DataUtil.getPeopleList())
            dialog?.dismiss()
        }, 1500)
    }

    private fun getView(height: Int, colorStr: String = "#D81B60") = View(this).apply {
        layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, height)
        setBackgroundColor(Color.parseColor(colorStr))
    }

}