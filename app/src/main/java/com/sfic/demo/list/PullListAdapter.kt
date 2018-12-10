package com.sfic.demo.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sfic.demo.R
import com.sfic.demo.main.MainBean
import java.util.ArrayList

/**
 * Created by wutiaorong on 2018/11/13.
 * Original Project demo
 */
class PullListAdapter: RecyclerView.Adapter<PullListAdapter.PullViewHolder>() {
    private var dataList: ArrayList<MainBean> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_pull,null)
        return PullViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

    override fun onBindViewHolder(holder: PullViewHolder, position: Int) {
        holder.textView.text = dataList[position].name
    }

    class PullViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var textView = itemView.findViewById<TextView>(R.id.textView)
    }

    fun update(array: ArrayList<MainBean>) {
        dataList = array
        notifyDataSetChanged()
    }
}