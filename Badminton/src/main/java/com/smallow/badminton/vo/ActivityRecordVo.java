package com.smallow.badminton.vo;

/**
 * Created by smallow on 16/10/14.
 */
public class ActivityRecordVo {



    private String atyDate;//活动时间
    private String atyAddress;//活动地点
    private int atyTotalPerson;//活动总人数
    private int atySiteNum;//活动场地数
    private int atyBadNum;//活动用球数
    private int atyTimeNum;//活动时间数
    private Double atyTotalMoney;//活动总消费
    private Double atyAvgMoney;//活动人均消费

    private String qqName;//会员QQ
    private int friendNum;//带人数
    private Double money;//花销
    private Double leftMoney;//余额




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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getLeftMoney() {
        return leftMoney;
    }

    public void setLeftMoney(Double leftMoney) {
        this.leftMoney = leftMoney;
    }
}
