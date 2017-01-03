package com.smallow.badminton.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghuidong on 2017/1/3.
 */
public interface BaseDao<T> {




    public T getObjectByProerties(String tableName,String[] propertyName, Object[] propertyValue, int[] types);

    public List<T> queryObjectsByProerties(String tableName,String[] propertyName, Object[] propertyValue, int[] types, Integer pageNum);

    public int updateObjectByProperties(String tableName,String[] propertyName, Object[] propertyValue,String[]condition,Object[]conditionValue, int[] types);
}
