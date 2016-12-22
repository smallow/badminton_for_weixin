package com.smallow.badminton.vo;

import com.smallow.badminton.Constant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by smallow on 16/10/14.
 */
public class ActivityRecordVo extends BeanPropertyRowMapper<ActivityRecordVo> {



    private String atyDate;//活动时间
    private String atyAddress;//活动地点
    private int atyTotalPerson;//活动总人数
    private int atySiteNum;//活动场地数
    private int atyBadNum;//活动用球数
    private int atyTimeNum;//活动时间数
    private Double atyTotalMoney;//活动总消费
    private Double atyAvgMoney;//活动人均消费

    private String qqName;//会员QQ昵称
    private String qqNum;//qq号
    private int friendNum;//带人数
    private Double currentDayCost;//当天活动花销
    private Double currentDayLeft;//当天消费后余额




    public String getAtyDate() {
        return atyDate;
    }

    public void setAtyDate(String atyDate) {
        this.atyDate = atyDate;
    }

    public String getAtyAddress() {
        return atyAddress;
    }

    public void setAtyAddress(String atyAddress) {
        this.atyAddress = atyAddress;
    }

    public int getAtyTotalPerson() {
        return atyTotalPerson;
    }

    public void setAtyTotalPerson(int atyTotalPerson) {
        this.atyTotalPerson = atyTotalPerson;
    }

    public int getAtySiteNum() {
        return atySiteNum;
    }

    public void setAtySiteNum(int atySiteNum) {
        this.atySiteNum = atySiteNum;
    }

    public int getAtyBadNum() {
        return atyBadNum;
    }

    public void setAtyBadNum(int atyBadNum) {
        this.atyBadNum = atyBadNum;
    }

    public int getAtyTimeNum() {
        return atyTimeNum;
    }

    public void setAtyTimeNum(int atyTimeNum) {
        this.atyTimeNum = atyTimeNum;
    }

    public Double getAtyTotalMoney() {
        return atyTotalMoney;
    }

    public void setAtyTotalMoney(Double atyTotalMoney) {
        this.atyTotalMoney = atyTotalMoney;
    }

    public Double getAtyAvgMoney() {
        return atyAvgMoney;
    }

    public void setAtyAvgMoney(Double atyAvgMoney) {
        this.atyAvgMoney = atyAvgMoney;
    }

    public String getQqName() {
        return qqName;
    }

    public void setQqName(String qqName) {
        this.qqName = qqName;
    }

    public int getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(int friendNum) {
        this.friendNum = friendNum;
    }

    public Double getCurrentDayCost() {
        return currentDayCost;
    }

    public void setCurrentDayCost(Double currentDayCost) {
        this.currentDayCost = currentDayCost;
    }

    public Double getCurrentDayLeft() {
        return currentDayLeft;
    }

    public void setCurrentDayLeft(Double currentDayLeft) {
        this.currentDayLeft = currentDayLeft;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    @Override
    public ActivityRecordVo mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ActivityRecordVo activityRecordVo=new ActivityRecordVo();
        activityRecordVo.setAtyAddress(rs.getString("address"));
        activityRecordVo.setAtyDate(Constant.formatForYYYYMMDD.format(rs.getDate("date")));
        activityRecordVo.setAtySiteNum(rs.getInt("site_num"));
        activityRecordVo.setAtyTimeNum(rs.getInt("time_num"));
        activityRecordVo.setAtyBadNum(rs.getInt("bad_num"));
        activityRecordVo.setAtyTotalPerson(rs.getInt("total_person"));
        activityRecordVo.setAtyTotalMoney(rs.getDouble("total_cost"));
        activityRecordVo.setAtyAvgMoney(rs.getDouble("avg_cost"));
        activityRecordVo.setQqName(rs.getString("qq_name"));
        activityRecordVo.setFriendNum(rs.getInt("friend_num"));
        activityRecordVo.setCurrentDayCost(rs.getDouble("current_day_cost"));
        activityRecordVo.setCurrentDayLeft(rs.getDouble("current_day_left_money"));
        activityRecordVo.setQqNum(rs.getString("qq_num"));
        return activityRecordVo;
    }
}
