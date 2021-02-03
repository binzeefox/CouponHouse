package com.binzee.coupon.server.server.http.api

import androidx.appcompat.app.AlertDialog
import com.binzee.coupon.common.base.BaseApplication.Companion.couponDB
import com.binzee.coupon.common.http.HTTP_API_PRE_1_0
import com.binzee.coupon.common.http.HttpBean
import com.binzeefox.foxdevframe_kotlin.FoxCore
import com.binzeefox.foxdevframe_kotlin.ui.utils.NoticeUtil
import com.binzeefox.foxdevframe_kotlin.utils.ThreadUtils
import com.yanzhenjie.andserver.annotation.*
import com.yanzhenjie.andserver.framework.body.StringBody
import com.yanzhenjie.andserver.http.HttpResponse

/**
 * v1.0版本Api
 *
 * @author tong.xw
 * 2021/02/03 17:26
 */
@RestController
@RequestMapping(path = [HTTP_API_PRE_1_0])
class CouponApiV1_0 {
    private val dao = FoxCore.couponDB.couponDao()

    // 兑换券状态
    // COUPON_STATE_IDLE = 0 //未使用
    // COUPON_STATE_ACTIVE = 1 //使用中
    // COUPON_STATE_ACHIEVE = 2 //已兑现
    private val couponMap = mapOf(
        Pair(0, "未使用"),
        Pair(1, "使用中"),
        Pair(2, "已兑现")
    )

    @GetMapping(params = ["/requestCouponLists"])
    fun requestCouponList(response: HttpResponse) {
        ThreadUtils.executeIO {
            val json = HttpBean(data = dao.queryAll()).toJsonString()
            val body = StringBody(json)
            response.setBody(body)
        }
    }

    @PostMapping(params = ["/changeCouponState"])
     fun changeCouponState(@RequestParam("couponUid") couponUid: String, @RequestParam("state") state: Int, response: HttpResponse) {
        ThreadUtils.executeIO {
            dao.queryByUid(couponUid).let { coupon ->
                ThreadUtils.runOnUiThread {
                    AlertDialog.Builder(FoxCore.simulatedBackStack.peek())
                        .setTitle("兑换券请求！")
                        .setMessage("是否同意将${coupon.title} 的状态转为 ${couponMap[state]}?")
                        .setPositiveButton("同意") {_, _  ->
                            ThreadUtils.executeIO {
                                dao.updateState(couponUid, state)
                                StringBody(HttpBean(data = true).toJsonString())
                                    .let { response.setBody(it) }
                                ThreadUtils.runOnUiThread {
                                    NoticeUtil.toast("操作成功").showNow()
                                }
                            }
                        }
                        .setNegativeButton("拒绝") {_, _ ->
                            ThreadUtils.executeIO {
                                StringBody(HttpBean(data = false).toJsonString())
                                    .let { response.setBody(it) }
                                ThreadUtils.runOnUiThread {
                                    NoticeUtil.toast("已拒绝").showNow()
                                }
                            }
                        }
                }
            }
        }
    }
}