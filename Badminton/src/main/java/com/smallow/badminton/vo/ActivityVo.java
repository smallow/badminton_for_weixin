package com.smallow.badminton.vo;

import com.smallow.badminton.enity.Member;

/**
 * Created by wanghuidong on 2017/1/3.
 */
public class ActivityVo {

    private Integer id;
    private String address;
    private String startTime;//活动开始时间
    private String endTime;//活动结束时间
    private Integer timeNum;//活动时间数
    private String date;//活动日期
    private Member chargeMember;//活动负责人
    private Double totalCost;//总费用
    private Integer badmintonNum;//羽毛球个数
    private Integer siteNum;//场地个数
    private Double avgCost;//人均费用
    private Integer totalPerson;//活动总人数
    private String qqGroupNum;//QQ群号
    private String atyStatus;


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


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(Integer timeNum) {
        this.timeNum = timeNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getQqGroupNum() {
        return qqGroupNum;
    }

    public void setQqGroupNum(String qqGroupNum) {
        this.qqGroupNum = qqGroupNum;
    }

    public String getAtyStatus() {
        return atyStatus;
    }

    public void setAtyStatus(String atyStatus) {
        this.atyStatus = atyStatus;
    }
}
