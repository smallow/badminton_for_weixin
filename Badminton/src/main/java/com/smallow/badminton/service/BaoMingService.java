package com.smallow.badminton.service;

import com.smallow.badminton.enity.BaoMing;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghuidong on 2017/1/3.
 */
public interface BaoMingService {

    /**
     * 保存报名记录
     * @param bean
     * @return
     */
    public int saveBaoMingRecord(BaoMing bean);


    /**
     * 根据活动ID获取报名记录
     * @param atyId
     * @return
     */
    public List<BaoMing> queryBaoMingRecordByAtyId(Integer atyId);


    /**
     * 根据活动iD 会员ID查询会员当天活动报名情况
     * @param atyId
     * @param memberId
     * @return
     */
    public BaoMing queryPersonalBaoMingRecordByAtyIdAndMemberId(Integer atyId,Integer memberId);


    /**
     *
     * @return
     */
    public BaoMing getBaoMingRecordById(Integer id);


    /**
     * 更新报名记录
     * @param bean
     * @return
     */
    public int updateBaoMing(BaoMing bean);



}
