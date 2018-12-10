package com.sfic.demo.event

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
/**
 * Created by wutiaorong on 2018/12/4.
 * Original Project demo
 */
class EventView@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("wtr","View down")
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e("wtr","View move")
            }
            MotionEvent.ACTION_UP -> {
                Log.e("wtr","View up")
            }
        }
        return super.dispatchTouchEvent(event)
    }


}