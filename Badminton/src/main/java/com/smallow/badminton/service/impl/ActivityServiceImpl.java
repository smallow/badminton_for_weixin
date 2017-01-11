package com.smallow.badminton.service.impl;


import com.smallow.badminton.dao.ActivityDao;
import com.smallow.badminton.enity.Activity;
import com.smallow.badminton.enity.Member;
import com.smallow.badminton.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by smallow on 16/10/13.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Override
    public Activity getTodayActivity() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);//获取年份
        int month = ca.get(Calendar.MONTH) + 1;//获取月份
        int day = ca.get(Calendar.DATE);//获取日
        String startDate = year + "-" + month + "-" + day;
        String endDate = year + "-" + month + "-" + day;
        String sql = "select * from badminton_activity a where a.date between ? and ? ";
        return activityDao.getActivityBySql(sql,startDate,endDate);
    }

    @Override
    public List<Activity> getWeekActivity() {
        return null;
    }

    @Override
    public List<Activity> getMonthsActivit() {
        return null;
    }

    @Override
    public Activity getActivityById(Integer id) {
        return activityDao.getActivityByProerties(new String[]{"id"},new Object[]{id},new int[]{Types.INTEGER});
    }

    @Override
    public Activity publishActivity(Activity bean) {

        int activityId=activityDao.publishActivity(bean);
        if(activityId!=0)
            return getActivityById(activityId);
        return null;
    }

    @Override
    public boolean updateActivityByProperties(String[] propertyName, Object[] propertyValue,String[]condition,Object[]conditionValue, int[] types) {
        int i=activityDao.updateActivityByProperties(propertyName,propertyValue,condition,conditionValue,types);
        if(i>0)
            return true;
        return false;
    }

    @Override
    public boolean gengxinAtyTotalPerson(Integer totalPerson, Integer atyId) {
        int i=activityDao.updateActivityByProperties(new String[]{"total_person"},new Object[]{totalPerson},new String[]{"id"},new Object[]{atyId},new int[]{Types.INTEGER,Types.INTEGER});
        if(i>0)
            return true;
        return false;
    }

    @Override
    public List<Activity> queryAtysByQQGroupNum(String qqGroupNum,Integer pageNum) {
        return activityDao.queryActivityByProerties(new String[]{"qq_group_num"},new Object[]{qqGroupNum},new int[]{Types.VARCHAR},pageNum);
    }

    @Override
    public int getTotalPersonByAtyId(Integer atyId) {
        return activityDao.getTotalPersonByAtyId(atyId);
    }

    private Activity parseActivity(Map<String, Object> dbData) {
//        if (dbData != null) {
//            Activity activity = new Activity();
//            activity.setId(Integer.parseInt(String.valueOf(dbData.get("id"))));
//            activity.setAddress(String.valueOf(dbData.get("address")));
//            activity.setStartTime((Timestamp) dbData.get("start_time"));
//            activity.setEndTime((Timestamp) dbData.get("end_time"));
//            activity.setDate((Date) dbData.get("date"));
//            Member chargeMember = new Member();
//            chargeMember.setId(Integer.parseInt(String.valueOf(dbData.get("charge_member_id"))));
//            chargeMember.setQqName(String.valueOf(dbData.get("charge_member_name")));
//            chargeMember.setPhone(String.valueOf(dbData.get("charge_member_phone")));
//            activity.setChargeMember(chargeMember);
//            activity.setBadmintonNum(Integer.parseInt(String.valueOf(dbData.get("bad_num"))));
//            activity.setSiteNum(Integer.parseInt(String.valueOf(dbData.get("site_num"))));
//            activity.setTimeNum(Integer.parseInt(String.valueOf(dbData.get("time_num"))));
//            activity.setTotalCost(Double.valueOf(String.valueOf(dbData.get("total_cost"))));
//            return activity;
//        }

        return null;
    }
}
