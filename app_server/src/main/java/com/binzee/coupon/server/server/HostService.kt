package com.binzee.coupon.server.server

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * 主机服务
 *
 * @author tong.xw
 * 2021/02/03 16:41
 */
class HostService: Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}