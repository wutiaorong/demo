package com.sfic.demo.list.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.sfic.demo.R

/**
 * Created by wutiaorong on 2018/11/13.
 * Original Project demo
 */
class RefreshHeader @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): ConstraintLayout(context,attrs,defStyleAttr) {
    lateinit var ivRotateCircle: ImageView
    val rotateAnimate: RotateAnimation by lazy {
        val rotateAnimation = AnimationUtils.loadAnimation(context,R.anim.rotating) as RotateAnimation
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation
    }

    init {
        View.inflate(context, R.layout.layout_refresh_header,this)
        ivRotateCircle = findViewById(R.id.ivRotateCircle)
    }

    fun startRotate() {
        ivRotateCircle.startAnimation(rotateAnimate)
    }

    fun stopRotate() {
        ivRotateCircle.clearAnimation()
    }


}
