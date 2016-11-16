package com.smallow.badminton.dao.impl;

import com.smallow.badminton.dao.BadmintonDao;
import com.smallow.badminton.enity.Activity;
import com.smallow.badminton.enity.ActivityRecord;
import com.smallow.badminton.enity.Group;
import com.smallow.badminton.enity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by smallow on 16/8/30.
 */
@Repository
public class BadmintonDaoImpl implements BadmintonDao {
    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<Group> getAllGroup() {

        List<Group> list= jdbcTemplate.query("select * from badminton_group", new RowMapper<Group>() {
            @Override
            public Group mapRow(ResultSet resultSet, int i) throws SQLException {
                Group group=new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                group.setMasterName(resultSet.getString("master_name"));
                group.setMasterPhone(resultSet.getString("master_phone"));
                group.setQqNumber(resultSet.getString("qq_number"));
                return group;
            }
        });
        return list;
    }





    @Override
    public void batchInsertActivityRecord(List<ActivityRecord> list) {
        final List<ActivityRecord> _list=list;
        String sql="insert into badminton_activity_record (member_id,charge_member_id,activity_id) values (?,?,?) ";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1,_list.get(i).getMember().getId());
                preparedStatement.setInt(2,_list.get(i).getChargeMember().getId());
                preparedStatement.setInt(3,_list.get(i).getActivity().getId());
            }

            @Override
            public int getBatchSize() {
                return _list.size();
            }
        });
    }

    @Override
    public ActivityRecord getPersonalActivityRecord(Integer memberId, Integer activiyId) {
        List<ActivityRecord> list=jdbcTemplate.query("select * from badminton_activity_record where member_id=? and activity_id=? ", new RowMapper<ActivityRecord>() {
            @Override
            public ActivityRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                ActivityRecord activityRecord=parseActivityRecordFromResultSet(resultSet);
                return activityRecord;
            }
        },memberId,activiyId);

        if(list!=null && list.size()>0)
            return list.get(0);
        return null;
    }



    private ActivityRecord parseActivityRecordFromResultSet(ResultSet resultSet) throws SQLException{
        ActivityRecord activityRecord=new ActivityRecord();
        activityRecord.setId(resultSet.getInt("id"));


        return activityRecord;
    }

    private Member parseMemberFormResultSet(ResultSet resultSet) throws SQLException{
        Member member=new Member();
        member.setId(resultSet.getInt("id"));
        member.setQqName(resultSet.getString("qq_name"));
        member.setPhone(resultSet.getString("phone"));
        member.setWeixinNum(resultSet.getString("weixin_num"));
        member.setPwd(resultSet.getString("pwd"));
        member.setMoney(resultSet.getDouble("money"));
        member.setQqGroupName(resultSet.getString("qq_group_name"));
        member.setQqGroupNum(resultSet.getString("qq_group_num"));
        return member;
    }


    private Activity parseActivityFromResultSet(ResultSet resultSet) throws SQLException {
        Activity activity=new Activity();
        activity.setId(resultSet.getInt("id"));
        activity.setAddress(resultSet.getString("address"));
        activity.setBadmintonNum(resultSet.getInt("bad_num"));
        activity.setSiteNum(resultSet.getInt("site_num"));
        activity.setTimeNum(resultSet.getInt("time_num"));
        activity.setDate(resultSet.getDate("date"));
        activity.setStartTime(resultSet.getTimestamp("start_time"));
        activity.setEndTime(resultSet.getTimestamp("end_time"));
        Member chargeMember=new Member();
        chargeMember.setId(resultSet.getInt("charge_member_id"));
        chargeMember.setQqName(resultSet.getString("charge_member_name"));
        chargeMember.setPhone(resultSet.getString("charge_member_phone"));
        activity.setChargeMember(chargeMember);
        activity.setTotalCost(resultSet.getDouble("total_cost"));
        return activity;
    }


}
