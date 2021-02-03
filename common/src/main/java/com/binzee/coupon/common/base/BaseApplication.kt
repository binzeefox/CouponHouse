package com.binzee.coupon.common.base

import android.app.Application
import androidx.room.Room
import com.binzee.coupon.common.db.CouponDatabase
import com.binzeefox.foxdevframe_kotlin.FoxCore

/**
 * App基类
 *
 * @author tong.xw
 * 2021/02/03 15:44
 */
class BaseApplication: Application() {

    companion object {
        private const val DB_NAME = "COUPON_DATABASE"

        /**
         * 数据库, 拓展到FoxCore
         */
        val FoxCore.couponDB: CouponDatabase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(FoxCore.appContext, CouponDatabase::class.java, DB_NAME).build()
        }
    }
}