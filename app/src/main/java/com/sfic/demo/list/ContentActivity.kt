package com.sfic.demo.list

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import com.sfic.demo.R
import com.sfic.demo.main.MainBean
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * Created by wutiaorong on 2018/11/12.
 * Original Project demo
 */
class ContentActivity: AppCompatActivity() {
    lateinit var recyclerView: PullToRefreshRecyclerView
    lateinit var adapter: PullListAdapter
    var mHandler: ContentActivityHandler = ContentActivityHandler(this)
    var dataList : ArrayList<MainBean> = arrayListOf()
    var mIndex = 23

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        recyclerView = findViewById(R.id.recyclerView)
        initViews()
    }

    private fun initViews() {
        dataList.clear()
        for (i in 1..22) {
            val model = MainBean(name = "$i", age = "2")
            dataList.add(model)
        }
        adapter = PullListAdapter()
        recyclerView.setAdapter(adapter)
        adapter.update(dataList)
        recyclerView.refreshFunction = {
            requestData()
        }
        recyclerView.loadingMoreFunction = {
            requestLoadingMore()
        }
    }

    private fun requestData() {
        mHandler.sendEmptyMessageDelayed(1,2000)
    }

    private fun requestCompletion() {
        recyclerView.stopLoading()
    }

    private fun loadingMoreCompletion() {
        recyclerView.stopLoading()
        val start = mIndex
        val end = mIndex + 4
        for (i in start..end) {
            val model = MainBean(name = "$i", age = "2")
            dataList.add(model)
        }
        adapter.update(dataList)
        recyclerView.isLoadingMoreHide = true
        mIndex = end + 1
    }

    private fun requestLoadingMore() {
        mHandler.sendEmptyMessageDelayed(2,2000)
    }

    companion object {
        class ContentActivityHandler(mActivity: ContentActivity): Handler() {
            private var weakReference: WeakReference<ContentActivity>? = null
            init {
                weakReference = WeakReference(mActivity)
            }

            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when(msg?.what) {
                    1 -> {
                        weakReference?.get()?.requestCompletion()
                    }
                    2 -> {
                        weakReference?.get()?.loadingMoreCompletion()
                    }
                }
            }
        }
    }
}