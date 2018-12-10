package com.sfic.demo.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by wutiaorong on 2018/11/12.
 * Original Project demo
 */
class PullToRefreshRecyclerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : PullToRefreshBase<RecyclerView>(context, attrs, defStyleAttr) {
    init {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRefreshableView.layoutManager = linearLayoutManager
    }

    override fun createRefreshableView(): RecyclerView {
        return RecyclerView(context)
    }

    override fun canPullUp(): Boolean {
        return mRefreshableView.canScrollVertically(1)
    }

    override fun canPullDown(): Boolean {
        return mRefreshableView.canScrollVertically(-1)
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        mRefreshableView.adapter = adapter
    }

    override fun scrollContentBy(y: Int) {
        mRefreshableView.smoothScrollBy(0,y)
    }
}