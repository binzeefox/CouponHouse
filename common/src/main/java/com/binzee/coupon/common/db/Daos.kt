package com.binzee.coupon.common.db

import androidx.room.*
import java.util.*


/**
 * 现金兑换券数据操作
 *
 * @author tong.xw
 * 2021/02/03 16:14
 */
@Dao
interface CouponDao {
    
    /**
     * 添加全部
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Coupon::class)
    fun insertAll(vararg condition: CouponCondition): List<Long>

    /**
     * 添加
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Coupon::class)
    fun insert(condition: CouponCondition): Long

    /**
     * 删除
     */
    @Delete(entity = Coupon::class)
    fun delete(vararg coupon: Coupon)

    /**
     * 更新状态
     */
    @Query("UPDATE Coupon SET state = :state WHERE couponUid = :couponUid")
    fun updateState(couponUid: String, state: Int)

    /**
     * 更新
     */
    @Update(onConflict = OnConflictStrategy.REPLACE, entity = Coupon::class)
    fun update(coupon: Coupon)

    /**
     * 查询全部
     */
    @Query("SELECT * FROM Coupon")
    fun queryAll(): List<Coupon>

    /**
     * 查询类别
     */
    @Query("SELECT * FROM Coupon WHERE type = :type")
    fun queryByType(type: Int): List<Coupon>

    /**
     * 查询状态
     */
    @Query("SELECT * FROM Coupon WHERE state = :state")
    fun queryState(state: Int): List<Coupon>

    /**
     * 查询Uid
     */
    @Query("SELECT * FROM Coupon WHERE couponUid = :couponUid")
    fun queryByUid(couponUid: String): Coupon

    /**
     * 查询结束日期在时间区间内的数据
     */
    @Query("SELECT * From Coupon where deadLine >= :startTime and deadLine <= :endTime")
    fun queryByDeadlineRange(startTime: Long, endTime: Long): List<Coupon>

    /**
     * 查询创建日期在时间区间内的数据
     */
    @Query("SELECT * From Coupon where createTime >= :startTime and deadLine <= :endTime")
    fun queryByCreateTimeRange(startTime: Long, endTime: Long): List<Coupon>
}

///////////////////////////////////////////////////////////////////////////
// 条件
///////////////////////////////////////////////////////////////////////////

/**
 * 现金兑换券条件类
 */
data class CouponCondition(
    val couponUid: String,  // 兑换券UID
    var title: String,  // 兑换券名称
    var type: Int,  //兑换券类别
    var describe: String,   // 兑换券描述
    val createTime: Long = Date().time, // 创建时间
    var deadline: Long, //过期时间
    val amount: Int = 0,    //金额（元￥）
    val state: Int = COUPON_STATE_IDLE  //状态
)