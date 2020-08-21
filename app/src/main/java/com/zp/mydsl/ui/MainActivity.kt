package com.zp.mydsl.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zp.mydsl.R
import com.zp.mydsl.dsl_one.log
import com.zp.mydsl.dsl_one.textWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_edit.textWatcher {
            textChanged {
                log("textChanged")
            }
            afterChanged {
                log("afterChanged")
            }
            beforeChange {
                log("beforeChange")
            }
        }

        mainBtn1.setOnClickListener {
            startActivity(Intent(this, DslList1Activity::class.java))
        }
        mainBtn2.setOnClickListener {
            startActivity(Intent(this, DslList2Activity::class.java))
        }
        mainBt3.setOnClickListener {
            startActivity(Intent(this, DslList3Activity::class.java))
        }
        mainBt4.setOnClickListener {
            startActivity(Intent(this, MultiActivity::class.java))
        }
    }
}
