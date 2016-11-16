package com.smallow.badminton.enity;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by smallow on 16/8/30.
 */
public class Activity extends BeanPropertyRowMapper<Activity> {

    private Integer id;
    private String address;
    private Timestamp startTime;//活动开始时间
    private Timestamp endTime;//活动结束时间
    private Integer timeNum;//活动时间数
    private Date date;//活动日期
    private Member chargeMember;//活动负责人
    private Double totalCost;//总费用
    private Integer badmintonNum;//羽毛球个数
    private Integer siteNum;//场地个数
    private Double avgCost;//人均费用
    private Integer totalPerson;//活动总人数

    //private List<Member> membersList;//活动参加人


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(Integer timeNum) {
        this.timeNum = timeNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Member getChargeMember() {
        return chargeMember;
    }

    public void setChargeMember(Member chargeMember) {
        this.chargeMember = chargeMember;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getBadmintonNum() {
        return badmintonNum;
    }

    public void setBadmintonNum(Integer badmintonNum) {
        this.badmintonNum = badmintonNum;
    }

    public Integer getSiteNum() {
        return siteNum;
    }

    public void setSiteNum(Integer siteNum) {
        this.siteNum = siteNum;
    }


    public Double getAvgCost() {
        return avgCost;
    }

    public void setAvgCost(Double avgCost) {
        this.avgCost = avgCost;
    }

    public Integer getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(Integer totalPerson) {
        this.totalPerson = totalPerson;
    }

    @Override
    public Activity mapRow(ResultSet resultSet, int i) throws SQLException {
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
        activity.setAvgCost(resultSet.getDouble("avg_cost"));
        activity.setTotalPerson(resultSet.getInt("total_person"));
        return activity;
    }
}
