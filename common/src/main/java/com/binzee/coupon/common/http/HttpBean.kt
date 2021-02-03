package com.binzee.coupon.common.http

import com.google.gson.Gson

/**
 * Http交互的数据包
 *
 * @author tong.xw
 * 2021/02/03 17:15
 */
data class HttpBean<T> (
    val errorCode: Int = 0,    // 错误代码，0为无异常，其它为异常
    val errorMsg: String? = null,   // 错误信息，若errorCode为0则为空
    val data: T // 携带的数据
) {
    private val gson = Gson()

    fun toJsonString(): String = gson.toJson(this)
}
