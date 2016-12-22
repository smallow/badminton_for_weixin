package com.smallow.badminton.dao.impl;

import com.smallow.badminton.dao.MemberDao;
import com.smallow.badminton.enity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
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
        List<Object> value = new ArrayList();
        List<Integer> _types = new ArrayList<Integer>();
        StringBuffer sql = new StringBuffer("select * from badminton_member m where 1=1 ");
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
        return jdbcTemplate.query(sql.toString(), value.toArray(), __types, new Member());
    }


    @Override
    public int updateMemberByProperties(String[] propertyName, Object[] propertyValue, String[] condition, Object[] conditionValue, int[] types) {
        StringBuffer sql = new StringBuffer("update badminton_member  set ");
        if (propertyName != null && propertyValue != null && propertyName.length == propertyValue.length) {
            for (int i = 0; i < propertyName.length; i++)
                if (i < propertyName.length - 1) {
                    sql.append(propertyName[i]).append("=?,");
                } else {
                    sql.append(propertyName[i]).append("=?");
                }
        }
        sql.append(" where 1=1 ");
        for (int i = 0; i < condition.length; i++) {
            sql.append(" and ").append(condition[i]).append("=?");
        }

        int index = types.length - propertyValue.length;
        Object[] copyedPropertyValue = null;
        if (index > 0) {
            copyedPropertyValue = new Object[propertyValue.length + index];
            System.arraycopy(propertyValue, 0, copyedPropertyValue, 0, propertyValue.length);
            for (int j = 0; j < index; j++) {
                copyedPropertyValue[propertyValue.length + j] = conditionValue[j];
            }
        }
        return jdbcTemplate.update(sql.toString(), copyedPropertyValue, types);
    }

    @Override
    public int saveMember(final Member member) {

        if (member != null) {
            final String sql = "insert into badminton_member  (qq_name,qq_num,phone,money,qq_group_num,qq_group_name,pwd)" +
                    "values (?,?,?,?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            ps.setString(1, member.getQqName());

                            ps.setString(2, member.getQqNum());
                            ps.setString(3, member.getPhone());
                            ps.setDouble(4, member.getMoney());
                            ps.setString(5, member.getQqGroupNum());
                            ps.setString(6, member.getQqGroupName());
                            ps.setString(7,member.getPwd());

                            return ps;
                        }
                    }, keyHolder);


            return keyHolder.getKey().intValue();
        }
        return 0;
    }

    @Override
    public int[] deleteMemberByIds(final int[] ids) {

        return jdbcTemplate.batchUpdate("DELETE FROM badminton_member WHERE id =?", new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // TODO Auto-generated method stub
                ps.setInt(1, ids[i]);//
            }

            public int getBatchSize() {
                return ids.length;
            }
        });

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
        return jdbcTemplate.queryForObject(sql.toString(), propertyValue, types, new Member());
    }

    @Override
    public Member getMemberBySql(String sql, Object... objects) {
        return null;
    }
}
