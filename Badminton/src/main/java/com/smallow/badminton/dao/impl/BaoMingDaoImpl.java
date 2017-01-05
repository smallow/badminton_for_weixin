package com.smallow.badminton.dao.impl;

import com.smallow.badminton.dao.BaoMingDao;
import com.smallow.badminton.enity.BaoMing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by wanghuidong on 2017/1/4.
 */
@Repository
public class BaoMingDaoImpl implements BaoMingDao {
    public static final String TABLE = "badminton_baoming";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int saveBaoMingRecord(final BaoMing bean) {
        final String sql = "insert into " + TABLE + " (member_id,aty_id,friend_num,friend_names,baoming_time,update_time,sf_qj,qq_name)" +
                "values (?,?,?,?,?,?,?,?)";
        if (bean != null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            ps.setInt(1,bean.getMemberId());
                            ps.setInt(2,bean.getAtyId());
                            ps.setInt(3,bean.getFriendNum());
                            ps.setString(4,bean.getFriendName());
                            ps.setTimestamp(5,new Timestamp(new Date().getTime()));
                            ps.setTimestamp(6,new Timestamp(new Date().getTime()));
                            ps.setBoolean(7,bean.isCancle());
                            ps.setString(8,bean.getMemberName());


                            return ps;
                        }
                    }, keyHolder);
            return keyHolder.getKey().intValue();
        }
        return 0;
    }

    @Override
    public List<BaoMing> query(String sql, Object[] args, int[] argTypes) {
        return jdbcTemplate.query(sql,args,argTypes,new BaoMing());
    }
    @Override
    public List<Map<String,Object>> queryForList(String sql, Object[] args, int[] argTypes){
        return jdbcTemplate.queryForList(sql,args,argTypes);
    }

    @Override
    public int updateBaoMingByProperties(String[] propertyName, Object[] propertyValue, String[] condition, Object[] conditionValue, int[] types) {
        StringBuffer sql = new StringBuffer("update badminton_baoming a set ");
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


}
