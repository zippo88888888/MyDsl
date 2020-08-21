package com.zp.mydsl.content

object DataUtil {

    fun getStudentList(size: Int = 10): MutableList<Student> {
        val list = ArrayList<Student>()
        for (i in 0 until size) {
            list.add(Student("高一34$i", "张三好多${i + 1}", (i + 1) * 2, i % 2 == 0))
        }
        return list
    }

    fun getTeacherList(size: Int = 10): MutableList<Teacher> {
        val list = ArrayList<Teacher>()
        for (i in 0 until size) {
            list.add(Teacher("高三84$i", "老师栗甸${i + 1}", (i + 1) * 2, i % 2 == 0))
        }
        return list
    }

    fun getPeopleList(size: Int = 10): MutableList<People> {
        val list = ArrayList<People>()
        for (i in 0 until size) {
            val sex = if (i % 2 == 0) 1 else 2
            list.add(People("人类$i", (i + 1) * 2, sex))
        }
        return list
    }

    fun getAnyData(): MutableList<Any> {
        val list = ArrayList<Any>()
        list.addAll(getStudentList())
        list.addAll(getTeacherList())
        return list
    }

}