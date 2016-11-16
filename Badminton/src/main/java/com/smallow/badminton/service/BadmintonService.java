package com.smallow.badminton.service;

import com.smallow.badminton.enity.Activity;
import com.smallow.badminton.enity.ActivityRecord;
import com.smallow.badminton.enity.Group;
import com.smallow.badminton.enity.Member;

import java.util.List;

/**
 * Created by smallow on 16/8/30.
 */
public interface BadmintonService {












    public List<Group> getAllGroup();







    /**
     * 批量保存打球记录
     * @param list
     */

    public void batchInsertActivityRecord(List<ActivityRecord> list);

    /**
     * 获取个人某一次的活动记录
     * @param memberId
     * @param activiyId
     * @return
     */
    public ActivityRecord getPersonalActivityRecord(Integer memberId,Integer activiyId);


    /**
     * 保存会员一次活动记录
     * @param activityRecord
     * @return
     */
    public ActivityRecord saveMemberActivityRecord(ActivityRecord activityRecord);
}
