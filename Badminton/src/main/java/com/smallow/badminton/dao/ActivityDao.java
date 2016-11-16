package com.smallow.badminton.dao;


import com.smallow.badminton.enity.Activity;

/**
 * Created by smallow on 16/10/13.
 */
public interface ActivityDao {


    public Activity getActivityByProerties(String[] propertyName, Object[] propertyValue, int[] types);

    public Activity getActivityBySql(String sql, Object... objects);

    public int publishActivity(final Activity bean);


    public int updateActivityByProperties(String[] propertyName, Object[] propertyValue,String[]condition,Object[]conditionValue, int[] types);

    public int updateActivityBySql(String sql,Object... objects);

}
