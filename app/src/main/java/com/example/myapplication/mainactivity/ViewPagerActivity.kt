package com.example.myapplication.mainactivity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.ui.main.CustomPagerAdapter
import com.example.myapplication.network.bean.DataModel

class ViewPagerActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        viewPager2 = findViewById(R.id.viewPager)

        val dataList = intent.getSerializableExtra("dataList") as? List<DataModel>
        if (dataList == null) {
            Toast.makeText(this, "未接收到数据！", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        Log.d("ViewPagerActivity", "datalist received: $dataList")
        viewPager2.adapter = CustomPagerAdapter(dataList)
    }



}
