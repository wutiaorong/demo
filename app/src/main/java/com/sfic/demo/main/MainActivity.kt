package com.sfic.demo.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sfic.demo.R
import com.sfic.demo.databinding.ActivityMainBinding
import java.util.ArrayList


/**
 * Created by wutiaorong on 2018/7/31.
 * Original Project demo
 */
class MainActivity: AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = MainViewModel()
        viewModel.name = "wutiaorong"
        binding.viewModel = viewModel
        initView()
    }

    private fun initView() {
        adapter = MainListAdapter()
        recyclerView = binding.recyclerView
        val dataList: ArrayList<MainBean> = arrayListOf()
        for (i in 1..5) {
            val model = MainBean(name = "1", age = "2")
            dataList.add(model)
        }
        recyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(this.resources.getDrawable(R.drawable.shape_main_divider))
            it.addItemDecoration(dividerItemDecoration)
        }
        adapter.setGroup(dataList)
    }
}