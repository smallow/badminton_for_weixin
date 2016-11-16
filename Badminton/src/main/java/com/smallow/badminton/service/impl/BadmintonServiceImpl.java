package com.smallow.badminton.service.impl;

import com.smallow.badminton.dao.BadmintonDao;
import com.smallow.badminton.enity.Activity;
import com.smallow.badminton.enity.ActivityRecord;
import com.smallow.badminton.enity.Group;
import com.smallow.badminton.enity.Member;
import com.smallow.badminton.service.BadmintonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by smallow on 16/8/30.
 */
@Service
public class BadmintonServiceImpl implements BadmintonService {

    @Autowired
    private BadmintonDao badmintonDao;










    @Override
    public List<Group> getAllGroup() {

        return badmintonDao.getAllGroup();
    }







    @Override
    public void batchInsertActivityRecord(List<ActivityRecord> list) {
        badmintonDao.batchInsertActivityRecord(list);
    }

    @Override
    public ActivityRecord getPersonalActivityRecord(Integer memberId, Integer activiyId) {
        return badmintonDao.getPersonalActivityRecord(memberId,activiyId);
    }

    @Override
    public ActivityRecord saveMemberActivityRecord(ActivityRecord activityRecord) {
        return null;
    }


    private Activity parseActivity(Map<String,Object> dbData){
        if(dbData!=null){
            Activity activity=new Activity();
            activity.setId(Integer.parseInt(String.valueOf(dbData.get("id"))));
            activity.setAddress(String.valueOf(dbData.get("address")));
            activity.setStartTime((Timestamp)dbData.get("start_time"));
            activity.setEndTime((Timestamp)dbData.get("end_time"));
            activity.setDate((Date)dbData.get("date"));
            Member chargeMember=new Member();
            chargeMember.setId(Integer.parseInt(String.valueOf(dbData.get("charge_member_id"))));
            chargeMember.setQqName(String.valueOf(dbData.get("charge_member_name")));
            chargeMember.setPhone(String.valueOf(dbData.get("charge_member_phone")));
            activity.setChargeMember(chargeMember);
            activity.setBadmintonNum(Integer.parseInt(String.valueOf(dbData.get("bad_num"))));
            activity.setSiteNum(Integer.parseInt(String.valueOf(dbData.get("site_num"))));
            activity.setTimeNum(Integer.parseInt(String.valueOf(dbData.get("time_num"))));
            activity.setTotalCost(Double.valueOf(String.valueOf(dbData.get("total_cost"))));
            return activity;
        }

        return null;
    }
}
