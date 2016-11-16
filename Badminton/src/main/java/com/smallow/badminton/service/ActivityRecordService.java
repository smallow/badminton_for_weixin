package com.smallow.badminton.service;

import com.smallow.badminton.enity.Activity;
import com.smallow.badminton.enity.ActivityRecord;
import com.smallow.badminton.enity.Member;

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
}
