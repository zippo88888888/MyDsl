package com.zp.mydsl.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zp.mydsl.R
import com.zp.mydsl.content.DataUtil
import com.zp.mydsl.content.Student
import com.zp.mydsl.content.Teacher
import com.zp.mydsl.dsl_two.tradition.EasyAdapter
import com.zp.mydsl.dsl_two.tradition.ViewHolderCreator
import kotlinx.android.synthetic.main.activity_dsl1.*

class DslList1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dsl1)
        dsl1RecyclerView.layoutManager = LinearLayoutManager(this)
        val dslAdapter = EasyAdapter<Any>()
            .addViewHolder(object : ViewHolderCreator<Any>() {
                override fun getResId() = R.layout.item_student
                override fun isForViewType(item: Any, position: Int) = item is Student
                override fun bindView(item: Any, position: Int, holder: ViewHolderCreator<Any>) {
                    val itemBean = item as Student
                    setText(R.id.item_student_nameTxt, itemBean.name)
                    setText(R.id.item_student_classTxt, itemBean.className)
                }
            }).addViewHolder(object : ViewHolderCreator<Any>() {
                override fun getResId() = R.layout.item_teacher
                override fun isForViewType(item: Any, position: Int) = item is Teacher
                override fun bindView(item: Any, position: Int, holder: ViewHolderCreator<Any>) {
                    val itemBean = item as Teacher
                    setText(R.id.item_teacher_classTxt, itemBean.studyClass)
                    setText(R.id.item_teacher_nameTxt, itemBean.name)
                }
            }).bindRecyclerView(dsl1RecyclerView)
        dslAdapter.datas = DataUtil.getAnyData()
    }
}
