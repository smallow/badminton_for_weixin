package com.smallow.badminton.dao;


import com.smallow.badminton.enity.Activity;

import java.util.List;

/**
 * Created by smallow on 16/10/13.
 */
public interface ActivityDao {


    public Activity getActivityByProerties(String[] propertyName, Object[] propertyValue, int[] types);

    public Activity getActivityBySql(String sql, Object... objects);

    public int publishActivity(final Activity bean);


    public int updateActivityByProperties(String[] propertyName, Object[] propertyValue,String[]condition,Object[]conditionValue, int[] types);

    public int updateActivityBySql(String sql,Object... objects);

    public List<Activity> queryActivityByProerties(String[] propertyName, Object[] propertyValue, int[] types,Integer pageNum);

    /**
     * 获取活动的实际参加人数  不包含请假的
     * @param atyId
     * @return
     */
    public int getTotalPersonByAtyId(Integer atyId);

}
