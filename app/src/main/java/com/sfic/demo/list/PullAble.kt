package com.sfic.demo.list

/**
 * Created by wutiaorong on 2018/11/26.
 * Original Project demo
 */
interface PullAble {
    fun canPullUp(): Boolean
    fun canPullDown(): Boolean
    fun scrollContentBy(y: Int)
}