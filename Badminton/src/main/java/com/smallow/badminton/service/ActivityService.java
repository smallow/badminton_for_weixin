package com.smallow.badminton.service;

import com.smallow.badminton.enity.Activity;

import java.util.List;

/**
 * 活动服务接口
 * Created by smallow on 16/10/13.
 */
public interface ActivityService {


    /**
     * 获取今日活动
     *
     * @return
     */
    public Activity getTodayActivity();


    /**
     * 获取本周活动
     *
     * @return
     */
    public List<Activity> getWeekActivity();

    /**
     * 获取本月活动
     *
     * @return
     */
    public List<Activity> getMonthsActivit();

    /**
     * 根据ID获取活动
     *
     * @param id
     * @return
     */
    public Activity getActivityById(Integer id);


    /**
     * 发布活动
     *
     * @param bean
     * @return
     */
    public Activity publishActivity(Activity bean);

    /**
     * 更新活动
     *
     * @param bean
     */
    public boolean updateActivityByProperties(String[] propertyName, Object[] propertyValue,String[]condition,Object[]conditionValue, int[] types);


    /**
     * 查询群活动
     * @param qqGroupNum
     * @return
     */
    public List<Activity> queryAtysByQQGroupNum(String  qqGroupNum,Integer pageNum);

    /**
     * 获取活动的实际参加人数  不包含请假的
     * @param atyId
     * @return
     */
    public int getTotalPersonByAtyId(Integer atyId);

}
