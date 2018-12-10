package com.sfic.demo.main

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sfic.demo.BR
import com.sfic.demo.R
import java.util.ArrayList

/**
 * Created by wutiaorong on 2018/8/1.
 * Original Project demo
 */
class MainListAdapter: RecyclerView.Adapter<MainListAdapter.ViewHolder>() {
    private var mainList:ArrayList<MainBean> = arrayListOf()

    fun setGroup(array: ArrayList<MainBean>) {
        mainList = array
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mainList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), R.layout.item_main,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(mainList[position])
    }


    inner class ViewHolder(private val binding: ViewDataBinding):RecyclerView.ViewHolder(binding.root) {
        private var viewModel = MainItemViewModel()
        init {
            bind(viewModel)
        }
        private fun bind(data: Any) {
            binding.setVariable(BR.data,data)
            binding.executePendingBindings()
        }
        fun setData(data: MainBean) {
            viewModel.setData(data)
        }
    }

}