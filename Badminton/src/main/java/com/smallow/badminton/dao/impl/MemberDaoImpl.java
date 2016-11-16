package com.smallow.badminton.dao.impl;

import com.smallow.badminton.dao.MemberDao;
import com.smallow.badminton.enity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by smallow on 16/10/13.
 */
@Repository
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Member> queryMemberByProperties(String[] propertyName, Object[] propertyValue, int[] types) {
        StringBuffer sql=new StringBuffer("select * from badminton_member m where 1=1 ");
        if(propertyName!=null && propertyValue!=null && propertyName.length==propertyValue.length){
            for(int i=0;i<propertyName.length;i++){
                sql.append(" and ").append(propertyName[i]).append("=? ");
            }
        }
        return jdbcTemplate.query(sql.toString(),propertyValue,types,new Member());
    }


    @Override
    public int updateMemberByProperties(String[] propertyName, Object[] propertyValue,String[]condition,Object[]conditionValue, int[] types) {
        StringBuffer sql = new StringBuffer("update badminton_member  set ");
        if (propertyName != null && propertyValue != null && propertyName.length == propertyValue.length ) {
            for (int i = 0; i < propertyName.length; i++)
                if (i < propertyName.length - 1) {
                    sql.append(propertyName[i]).append("=?,");
                } else {
                    sql.append(propertyName[i]).append("=?");
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
    public Member getMemberByProerties(String[] propertyName, Object[] propertyValue, int[] types) {
        StringBuffer sql = new StringBuffer("select * from badminton_member where 1=1 ");
        if (propertyName != null && propertyValue != null && propertyName.length == propertyValue.length) {
            for (int i = 0; i < propertyName.length; i++) {
                sql.append(" and ");
                sql.append(propertyName[i] + "=?");
            }
        }
        return jdbcTemplate.queryForObject(sql.toString(),propertyValue,types,new Member());
    }

    @Override
    public Member getMemberBySql(String sql, Object... objects) {
        return null;
    }
}
