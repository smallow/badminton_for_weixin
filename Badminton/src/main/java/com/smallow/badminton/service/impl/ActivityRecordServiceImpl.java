package com.smallow.badminton.service.impl;

import com.smallow.badminton.dao.ActivityRecordDao;
import com.smallow.badminton.dao.MemberDao;
import com.smallow.badminton.enity.Activity;
import com.smallow.badminton.enity.ActivityRecord;
import com.smallow.badminton.enity.Member;
import com.smallow.badminton.service.ActivityRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;

/**
 * Created by smallow on 16/10/14.
 */
@Service
public class ActivityRecordServiceImpl implements ActivityRecordService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private ActivityRecordDao activityRecordDao;


    @Override
    public boolean savePersonalActivityRecord(Member member, Member chargeMember, Activity activity,int friendNum) {
        try{
            int i=memberDao.updateMemberByProperties(new String[]{"money"},new Object[]{member.getMoney()},new String[]{"id"},new Object[]{member.getId()},new int[]{Types.DOUBLE,Types.INTEGER});
            if(i>0){
                System.out.println("会员:"+member.getQqName()+" 活动金额更新完成,开始插入活动记录");
                ActivityRecord activityRecord=new ActivityRecord();
                activityRecord.setMember(member);
                activityRecord.setChargeMember(chargeMember);
                activityRecord.setActivity(activity);
                activityRecord.setMoney(activity.getAvgCost()*(friendNum+1));
                activityRecord.setMemo("");
                activityRecord.setFriendNum(friendNum);
                int j=activityRecordDao.saveActivityRecord(activityRecord);
                if(j>0){
                    System.out.println("会员:"+member.getQqName()+" 活动记录插入成功!");
                    return true;
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveActivityRecord(ActivityRecord bean) {
        if(activityRecordDao.saveActivityRecord(bean)>0)
            return true;
        return false;
    }
}
