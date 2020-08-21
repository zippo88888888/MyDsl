package com.zp.mydsl.content

import com.chad.library.adapter.base.entity.MultiItemEntity

data class Teacher(
    var studyClass: String = "",
    var name: String = "",
    var age: Int = 0,
    var sex: Boolean = true
)

data class Student(
    var className: String = "",
    var name: String = "",
    var age: Int = 0,
    var sex: Boolean = true
)

data class People(
    var name: String = "",
    var age: Int = 0,
    var sex: Int = 1 // 1男；2女
) : MultiItemEntity {
    override fun getItemType() = sex
}