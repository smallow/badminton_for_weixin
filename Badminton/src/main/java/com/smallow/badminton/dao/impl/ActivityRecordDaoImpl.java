package com.smallow.badminton.dao.impl;

import com.smallow.badminton.dao.ActivityRecordDao;
import com.smallow.badminton.enity.ActivityRecord;
import com.smallow.badminton.enity.Member;
import com.smallow.badminton.vo.ActivityRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smallow on 16/10/14.
 */
@Repository
public class ActivityRecordDaoImpl implements ActivityRecordDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int saveActivityRecord(final ActivityRecord activityRecord) {
        if (activityRecord != null) {
            final String sql = "insert into badminton_activity_record  (member_id,charge_member_id,activity_id,money,memo,friend_num,avg_money,current_day_left_money)" +
                    "values (?,?,?,?,?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            ps.setInt(1,activityRecord.getMember().getId());
                            ps.setInt(2,activityRecord.getChargeMember().getId());
                            ps.setInt(3,activityRecord.getActivity().getId());
                            ps.setDouble(4,activityRecord.getMoney());
                            ps.setString(5,activityRecord.getMember()==null?"":activityRecord.getMemo());
                            ps.setInt(6,activityRecord.getFriendNum());
                            ps.setDouble(7,activityRecord.getActivity().getAvgCost());
                            ps.setDouble(8,activityRecord.getCurrentDayLeftMoney());
                            return ps;
                        }
                    }, keyHolder);

           // System.out.println("自动插入id============================" + keyHolder.getKey().intValue());
            return keyHolder.getKey().intValue();
        }
        return 0;
    }


    @Override
    public List<ActivityRecord> queryActivityRecordByProerties(String[] propertyName, Object[] propertyValue, int[] types) {
        return null;
    }

    @Override
    public List<ActivityRecordVo> queryAtyRecordViewByProperties(String[] propertyName, Object[] propertyValue, int[] types) {
        List<Object> value = new ArrayList();
        List<Integer> _types = new ArrayList<Integer>();
        StringBuffer sql = new StringBuffer("select * from view_member_record m where 1=1 ");
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
        return jdbcTemplate.query(sql.toString(), value.toArray(), __types, new ActivityRecordVo());
    }
}
