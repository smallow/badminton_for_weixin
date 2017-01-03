package com.smallow.badminton.service;

import com.smallow.badminton.enity.Activity;
import com.smallow.badminton.enity.ActivityRecord;
import com.smallow.badminton.enity.Member;
import com.smallow.badminton.vo.ActivityRecordVo;

import java.util.List;

/**
 * Created by smallow on 16/10/13.
 */
public interface ActivityRecordService {

    /**
     * 保存个人打球记录并实现扣费
     * @param member
     * @param chargeMember
     * @param activity
     * @return
     */

    public boolean savePersonalActivityRecord(Member member, Member chargeMember, Activity activity,int friendNum);

    /**
     * 插入活动记录
     * @param bean
     * @return
     */
    public boolean saveActivityRecord(ActivityRecord bean);


    /**
     * 根据会员ID查询活动记录
     * @param memberId
     * @return
     */
    public List<ActivityRecordVo> queryActivityRecordByMemberId(Integer memberId);

    /**
     * 根据活动ID查询活动详细记录
     * @param atyId
     * @return
     */
    public List<ActivityRecordVo> queryActivityRecordByAtyId(Integer atyId);
}
