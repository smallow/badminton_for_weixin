package com.smallow.badminton.dao.impl;

import com.smallow.badminton.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by wanghuidong on 2017/1/3.
 */
public class BaseDaoImpl<T> implements BaseDao<T>{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected Class<T> entityClass;


    public BaseDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }



    @Override
    public T getObjectByProerties(String tableName, String[] propertyName, Object[] propertyValue, int[] types) {
        return null;
    }

    @Override
    public List<T> queryObjectsByProerties(String tableName, String[] propertyName, Object[] propertyValue, int[] types, Integer pageNum) {
        return null;
    }

    @Override
    public int updateObjectByProperties(String tableName, String[] propertyName, Object[] propertyValue, String[] condition, Object[] conditionValue, int[] types) {
        return 0;
    }
}
