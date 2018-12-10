package com.sfic.demo.list.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.sfic.demo.R

/**
 * Created by wutiaorong on 2018/11/13.
 * Original Project demo
 */
class LoadingMoreFooter @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): ConstraintLayout(context,attrs,defStyleAttr) {
    init {
        View.inflate(context, R.layout.layout_loading_more_footer,this)
    }
}