package com.smallow.badminton.dao.impl;


import com.smallow.badminton.dao.ActivityDao;
import com.smallow.badminton.enity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;

/**
 * Created by smallow on 16/10/13.
 */
@Repository
public class ActivityDaoImpl implements ActivityDao {
    public static final String TABLE = "badminton_activity";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Activity getActivityByProerties(String[] propertyName, Object[] propertyValue, int[] types) {
        StringBuffer sql = new StringBuffer("select * from " + TABLE + " where 1=1 ");
        if (propertyName != null && propertyValue != null && propertyName.length == propertyValue.length) {
            for (int i = 0; i < propertyName.length; i++) {
                sql.append(" and ");
                sql.append(propertyName[i] + "=?");
            }
        }
        return jdbcTemplate.queryForObject(sql.toString(), propertyValue, types, new Activity());
    }

    public Activity getActivityBySql(String sql, Object... objects) {
        return jdbcTemplate.queryForObject(sql, new Activity(), objects);
    }

    @Override
    public int publishActivity(final Activity activity) {

        if (activity != null) {
            final String sql = "insert into badminton_activity  (address,start_time,end_time,date,charge_member_id,charge_member_name,charge_member_phone,site_num,time_num)" +
                    "values (?,?,?,?,?,?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            ps.setString(1, activity.getAddress());
                            ps.setTimestamp(2, activity.getStartTime());
                            ps.setTimestamp(3, activity.getEndTime());
                            ps.setDate(4, new Date(activity.getDate().getTime()));
                            ps.setInt(5, activity.getChargeMember().getId());
                            ps.setString(6, activity.getChargeMember().getQqName());
                            ps.setString(7, activity.getChargeMember().getPhone());
                            ps.setInt(8, activity.getSiteNum());
                            ps.setInt(9, activity.getTimeNum());
                            return ps;
                        }
                    }, keyHolder);

            System.out.println("自动插入id============================" + keyHolder.getKey().intValue());
            return keyHolder.getKey().intValue();
        }
        return 0;
    }

    @Override
    public int updateActivityByProperties(String[] propertyName, Object[] propertyValue,String[]condition,Object[]conditionValue, int[] types) {
        StringBuffer sql = new StringBuffer("update badminton_activity a set ");
        if (propertyName != null && propertyValue != null && propertyName.length == propertyValue.length) {
            for (int i = 0; i < propertyName.length; i++) {
                if (i < propertyName.length - 1) {
                    sql.append(propertyName[i]).append("=?,");
                } else {
                    sql.append(propertyName[i]).append("=?");
                }
            }
        }
        sql.append(" where 1=1 ");
        for(int i=0;i<condition.length;i++){
            sql.append(" and ").append(condition[i]).append("=?");
        }
        int index=types.length-propertyValue.length;
        Object[] copyedPropertyValue=null;
        if(index>0){
            copyedPropertyValue=new Object[propertyValue.length+index];
            System.arraycopy(propertyValue,0,copyedPropertyValue,0,propertyValue.length);
            for (int j=0;j<index;j++ ){
                copyedPropertyValue[propertyValue.length+j]=conditionValue[j];
            }
        }
        return jdbcTemplate.update(sql.toString(), copyedPropertyValue, types);

    }

    @Override
    public int updateActivityBySql(String sql, Object... objects) {
        return jdbcTemplate.update(sql,objects);
    }
}
