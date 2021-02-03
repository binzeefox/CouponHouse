package com.binzee.coupon.server.server.http

import android.widget.Toast
import com.binzee.coupon.common.http.HTTP_PORT
import com.binzee.coupon.common.http.HTTP_TIMEOUT
import com.binzeefox.foxdevframe_kotlin.FoxCore
import com.binzeefox.foxdevframe_kotlin.ui.utils.NoticeUtil
import com.binzeefox.foxdevframe_kotlin.utils.LogUtil
import com.binzeefox.foxdevframe_kotlin.utils.ThreadUtils
import com.yanzhenjie.andserver.AndServer
import com.yanzhenjie.andserver.Server
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * Http服务器
 *
 * @author tong.xw
 * 2021/02/03 16:59
 */
object HttpHost: Server.ServerListener {

    // 服务器
    private val server: Server by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        AndServer
            .webServer(FoxCore.appContext)
            .port(HTTP_PORT)
            .timeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
            .listener(this)
            .build()
    }

    /**
     * 是否在运行
     */
    val isRunning: Boolean get() = server.isRunning

    /**
     * 开始
     */
    fun start() = server.startup()

    /**
     * 关闭
     */
    fun shutdown() = server.shutdown()

    override fun onStarted() {
        ThreadUtils.runOnUiThread {
            NoticeUtil.toast("Http服务器开启成功").showNow()
        }
    }

    override fun onStopped() {
        ThreadUtils.runOnUiThread {
            NoticeUtil.toast("Http服务器已关闭").setDuration(Toast.LENGTH_LONG).showNow()
        }
    }

    override fun onException(e: Exception?) {
        ThreadUtils.runOnUiThread {
            NoticeUtil.toast("Http服务器异常").setDuration(Toast.LENGTH_LONG).showNow()
            LogUtil("CouponHttpServer").setMessage("Http服务器异常").setThrowable(e).e()
        }
    }
}