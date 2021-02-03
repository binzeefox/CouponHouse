package com.binzee.coupon.server.server.socket

import com.binzee.coupon.common.http.SOCKET_PASSWORD_IN
import com.binzee.coupon.common.http.SOCKET_PASSWORD_OUT
import com.binzee.coupon.common.http.SOCKET_PORT
import com.binzeefox.foxdevframe_kotlin.utils.LogUtil
import com.binzeefox.foxdevframe_kotlin.utils.net.socket.ServerHelper
import com.binzeefox.foxdevframe_kotlin.utils.net.socket.SocketServerCallback
import java.net.Socket

/**
 * Socket服务器
 *
 * @author tong.xw
 * 2021/02/03 16:42
 */
object SocketHost : SocketServerCallback {
    private val helper: ServerHelper by lazy( mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        ServerHelper(SOCKET_PORT).apply {
            callback = this@SocketHost
        }
    }

    override fun onAccept(client: Socket) {
        LogUtil("CouponSocketHost").setMessage("accept client: $client").v()
    }

    override fun onReceive(client: Socket, text: String) {
        if (SOCKET_PASSWORD_IN == text) {
            helper.sendTo(client, SOCKET_PASSWORD_OUT)
        }
    }

    override fun onLost(client: Socket) {
        // do nothing...
    }

    override fun onError(errorCode: Int, t: Throwable) {
        LogUtil("CouponSocketHost")
            .setMessage("Socket服务器异常， 代码: $errorCode")
            .setThrowable(t)
    }

}