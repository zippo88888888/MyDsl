package com.zp.mydsl.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.zp.mydsl.R
import com.zp.mydsl.content.DataUtil
import com.zp.mydsl.content.Student
import com.zp.mydsl.content.Teacher
import com.zp.mydsl.dsl_two.*
import com.zp.mydsl.dsl_two.tradition.EasyAdapter
import com.zp.mydsl.dsl_two.tradition.ViewHolderCreator
import kotlinx.android.synthetic.main.activity_dsl1.*

class DslList2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dsl1)

        // 方式一
        /*easyAdapter<Any> {
            bindRecyclerView(dsl1RecyclerView)
            addItem(R.layout.item_student) {
                viewType { item, _ -> item is Student }
                bindView { item, _, holder ->
                    item as Student
                    holder.setText(R.id.item_student_nameTxt, item.name)
                    holder.setText(R.id.item_student_classTxt, item.className)
                }
            }
            addItem(R.layout.item_teacher) {
                viewType { item, _ -> item is Teacher }
                bindView { item, _, holder ->
                    item as Teacher
                    holder.setText(R.id.item_teacher_nameTxt, item.name)
                    holder.setText(R.id.item_teacher_classTxt, item.studyClass)
                }
            }
            datas = DataUtil.getAnyData()
        }*/

        // 方式二
        /*dsl1RecyclerView.setData<Any> {
            bindLayoutManager { LinearLayoutManager(this@DslList2Activity) }
            bindAdapter {
                addItem(R.layout.item_student) {
                    viewType { item, _ -> item is Student }
                    bindView { item, _, holder ->
                        item as Student
                        holder.setText(R.id.item_student_nameTxt, item.name)
                        holder.setText(R.id.item_student_classTxt, item.className)
                    }
                }
                addItem(R.layout.item_teacher) {
                    viewType { item, _ -> item is Teacher }
                    bindView { item, _, holder ->
                        item as Teacher
                        holder.setText(R.id.item_teacher_nameTxt, item.name)
                        holder.setText(R.id.item_teacher_classTxt, item.studyClass)
                    }
                }
            }
            setData(DataUtil.getAnyData())
        }*/


        // 方式三
        dsl1RecyclerView.setup<Any> {
            layoutManager {
                LinearLayoutManager(this@DslList2Activity)
            }
            addItem(R.layout.item_student) {
                viewType { item, _ -> item is Student }
                bindView { item, _, holder ->
                    item as Student
                    holder.setText(R.id.item_student_nameTxt, item.name)
                    holder.setText(R.id.item_student_classTxt, item.className)
                }
            }
            addItem(R.layout.item_teacher) {
                viewType { item, _ -> item is Teacher }
                bindView { item, _, holder ->
                    item as Teacher
                    holder.setText(R.id.item_teacher_nameTxt, item.name)
                    holder.setText(R.id.item_teacher_classTxt, item.studyClass)
                    holder.setOnClick(R.id.item_teacher_nameTxt) {
                        this.showToast(item.name)
                    }
                }
            }
            bindData {
                DataUtil.getAnyData()
            }
        }

    }
}
