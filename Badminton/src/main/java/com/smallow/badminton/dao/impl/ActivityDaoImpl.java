package com.smallow.badminton.dao.impl;


import com.smallow.badminton.dao.ActivityDao;
import com.smallow.badminton.enity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            final String sql = "insert into badminton_activity  (address,start_time,end_time,date,charge_member_id,charge_member_name,charge_member_phone,site_num,time_num,qq_group_num,aty_status)" +
                    "values (?,?,?,?,?,?,?,?,?,?,?)";
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
                            ps.setFloat(9, activity.getTimeNum());
                            ps.setString(10,activity.getQqGroupNum());
                            ps.setString(11,activity.getAtyStatus());
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

    @Override
    public List<Activity> queryActivityByProerties(String[] propertyName, Object[] propertyValue, int[] types,Integer pageNum) {

        List<Object> value = new ArrayList();
        List<Integer> _types = new ArrayList<Integer>();
        StringBuffer sql = new StringBuffer("select * from badminton_activity m where 1=1 ");
        if (propertyName != null && propertyValue != null && propertyName.length == propertyValue.length) {

            for (int i = 0; i < propertyName.length; i++) {
                if (propertyValue[i] != null && !"".equals(propertyValue[i])) {
                    sql.append(" and ").append(propertyName[i]).append("=? ");
                    value.add(propertyValue[i]);
                    _types.add(types[i]);
                }

            }
        }
        int[] __types = new int[_types.size()];
        Integer[] tmp = _types.toArray(new Integer[_types.size()]);
        for (int i = 0; i < tmp.length; i++) {
            __types[i] = tmp[i].intValue();
        }
        if(pageNum!=0){
            sql.append(" limit "+(pageNum-1)*10+",10");
        }
        return jdbcTemplate.query(sql.toString(), value.toArray(), __types, new Activity());
    }

    @Override
    public int getTotalPersonByAtyId(Integer atyId) {
        String sql="select count(id) from badminton_baoming where aty_id=? and sf_qj=0 ";
        int num=jdbcTemplate.queryForInt(sql,atyId);
        return num;
    }
}
