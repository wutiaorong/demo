package com.sfic.demo.list

import android.content.Context

/**
 * Created by wutiaorong on 2018/11/13.
 * Original Project demo
 */
fun Context.dp2px(dpValue: Float): Int {
    val scale = applicationContext.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}
