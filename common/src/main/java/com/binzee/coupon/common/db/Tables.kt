package com.binzee.coupon.common.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

// 兑换券类别
const val COUPON_TYPE_CASH = 0
const val COUPON_TYPE_WISH = 1

// 兑换券状态
const val COUPON_STATE_IDLE = 0 //未使用
const val COUPON_STATE_ACTIVE = 1 //使用中
const val COUPON_STATE_ACHIEVE = 2 //已兑现

/**
 * 现金兑换券
 *
 * @author tong.xw
 * 2021/02/03 15:59
 */
@Entity(indices = [Index(value = ["couponUid"], unique = true)])
class Coupon(
    @PrimaryKey(autoGenerate = true) var id: Long,  //主键
    val couponUid: String,  // 兑换券UID
    val type: Int,  //兑换券类别
    var title: String,  // 兑换券名称
    var describe: String,   // 兑换券描述
    val createTime: Long = Date().time, // 创建时间
    var deadline: Long, //过期时间
    val amount: Int = 0,    //金额（元￥）
    val state: Int = COUPON_STATE_IDLE  //状态
)