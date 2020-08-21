### DSL Adapter demo 感觉还可以继续优化！

``` kotlin

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

// 基于BRVAH DSL Adapter
quickAdapter = dsl1RecyclerView.quick<Student>(R.layout.item_student) {
    layoutManger {
        LinearLayoutManager(this@DslList3Activity)
    }
    itemDecoration{
        arrayListOf(RecyclerViewDivider.getDefaultDivider(this@DslList3Activity))
    }
    addHeader {
        arrayListOf(getView(100), getView(100, "#008577"), getView(100))
    }
    addFooter {
        arrayListOf(getView(200), getView(200, "#008577"), getView(200))
    }
    bind {
        bindView { item, _, holder ->
            holder.setText(R.id.item_student_nameTxt, item.name)
            holder.setText(R.id.item_student_classTxt, item.className)
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
    }
    data {
        DataUtil.getStudentList()
    }
}.adapter

```