package com.sfic.demo.main

import android.databinding.ObservableField

/**
 * Created by wutiaorong on 2018/8/1.
 * Original Project demo
 */
class MainItemViewModel{
    var name = ObservableField<String>()
    var age = ObservableField<String>()

    fun setData(bean: MainBean) {
        name.set(bean.name)
        age.set(bean.age)
    }
}