package com.sfic.demo.list

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import com.sfic.demo.R
import com.sfic.demo.list.view.LoadingMoreFooter
import com.sfic.demo.list.view.RefreshHeader
import kotlin.math.abs
import android.animation.ValueAnimator
import android.util.Log
import android.widget.LinearLayout

/**
 * Created by wutiaorong on 2018/11/13.
 * Original Project demo
 */
abstract class PullToRefreshBase<T : View> @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), PullAble {

    private var refreshHeader = RefreshHeader(context)
    private var loadingMoreFooter = LoadingMoreFooter(context)
    private var mLastY: Float = 0f
    private lateinit var pullDirection: PullDirection
    private var headerHeight = context.dp2px(56f)
    private var footerHeight = context.dp2px(40f)
    lateinit var mRefreshableView: T
    private var allowRefresh = false
        set(value) {
            field = value
            configHeader()
        }

    var isLoadingMoreHide = true

    private var isRefreshing = false
    private var isLoadingMore = false

    var refreshFunction: (() -> Unit)? = null
        set(value) {
            field = value
            allowRefresh = true
        }

    var loadingMoreFunction: (() -> Unit)? = null
        set(value) {
            field = value
            isLoadingMoreHide = false
        }

    init {
        initView()
    }

    protected abstract fun createRefreshableView(): T


    private fun initView() {
        orientation = VERTICAL
        refreshHeader.id = R.id.refresh_header
        val headerLp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        headerLp.topMargin = headerHeight
        addView(refreshHeader, headerLp)

        mRefreshableView = createRefreshableView()
        val refreshableView = mRefreshableView
        refreshableView.id = R.id.refreshable_view
        val refreshViewLp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        addView(refreshableView, refreshViewLp)

        loadingMoreFooter.id = R.id.loading_more_footer
        val loadingMoreLp = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        loadingMoreLp.bottomMargin = -footerHeight
        addView(loadingMoreFooter, loadingMoreLp)
        pullDirection = PullDirection.NONE

        configHeader()
    }

    private fun configHeader() {
        val headerLp = refreshHeader.layoutParams as LinearLayout.LayoutParams
        when (allowRefresh) {
            true -> {
                headerLp.topMargin = -headerHeight
                headerLp.height = headerHeight
            }
            else -> {
                headerLp.topMargin = 0
                headerLp.height = 0
            }
        }
        refreshHeader.layoutParams = headerLp
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (isRefreshing || isLoadingMore) {
            return true
        }
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (ev.y > mLastY) {
                    pullDirection = PullDirection.DOWN
                    if (allowRefresh && !canPullDown()) {
                        return true
                    }
                } else if (ev.y < mLastY) {
                    pullDirection = PullDirection.UP
                    if (!isLoadingMoreHide && !canPullUp()) {
                        return true
                    }
                }
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (isRefreshing || isLoadingMore) {
            return true
        }
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val offsetY = event.y - mLastY
                mLastY = event.y
                when (pullDirection) {
                    PullDirection.DOWN -> {
                        if (abs(scrollY) > context.dp2px(100f)) {
                            refreshAction()
                            return true
                        }
                    }
                    PullDirection.UP -> {
                        if (abs(scrollY) > context.dp2px(40f)) {
                            loadingMoreAction()
                            return true
                        }
                    }
                    else -> {
                    }
                }
                scrollBy(0, (-offsetY * 0.5).toInt())
            }
            MotionEvent.ACTION_UP -> {
                when (pullDirection) {
                    PullDirection.DOWN -> {
                        if (abs(scrollY) > context.dp2px(33f)) {
                            refreshAction()
                            return true
                        } else {
                            hideRefresh()
                        }
                    }
                    PullDirection.UP -> {
                        if (abs(scrollY) > context.dp2px(40f)) {
                            return true
                        }
                    }
                    else -> {
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun stopLoading() {
        if (isRefreshing) {
            hideRefresh()
        }
        if (isLoadingMore) {
            hideLoadingMore()
        }
    }

    private fun refreshAction() {
        isRefreshing = true
        val valueAnimator = ValueAnimator.ofFloat(scrollY.toFloat(), -context.dp2px(56f).toFloat())
        valueAnimator.duration = 500
        valueAnimator.addUpdateListener {
            val value = valueAnimator.animatedValue as Float
            scrollTo(0, value.toInt())
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                refreshHeader.startRotate()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        valueAnimator.start()
        refreshFunction?.invoke()
    }

    private fun hideRefresh() {
        refreshHeader.stopRotate()
        val valueAnimator = ValueAnimator.ofFloat(scrollY.toFloat(), 0f)
        valueAnimator.duration = 500
        valueAnimator.addUpdateListener {
            val value = valueAnimator.animatedValue as Float
            scrollTo(0, value.toInt())
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                isRefreshing = false
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        valueAnimator.start()
    }

    private fun loadingMoreAction() {
        isLoadingMore = true
        loadingMoreFunction?.invoke()
    }

    private fun hideLoadingMore() {
        val mScrollY = scrollY
        scrollBy(0,-mScrollY)
        isLoadingMore = false
        scrollContentBy(mScrollY)
    }
}

enum class PullDirection {
    DOWN, UP, NONE
}