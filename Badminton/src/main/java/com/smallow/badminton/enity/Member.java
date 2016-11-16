package com.smallow.badminton.enity;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by smallow on 16/8/30.
 */
public class Member extends BeanPropertyRowMapper<Member> {

    private Integer id;
    private String qqName;
    private String phone;
    private String weixinNum;//微信号
    //private Integer groupId;
    private String pwd;
    private Double money;
    private String qqGroupNum;
    private String qqGroupName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQqName() {
        return qqName;
    }

    public void setQqName(String qqName) {
        this.qqName = qqName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeixinNum() {
        return weixinNum;
    }

    public void setWeixinNum(String weixinNum) {
        this.weixinNum = weixinNum;
    }



    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }


    public String getQqGroupNum() {
        return qqGroupNum;
    }

    public void setQqGroupNum(String qqGroupNum) {
        this.qqGroupNum = qqGroupNum;
    }

    public String getQqGroupName() {
        return qqGroupName;
    }

    public void setQqGroupName(String qqGroupName) {
        this.qqGroupName = qqGroupName;
    }

    @Override
    public Member mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Member member=new Member();
        member.setId(resultSet.getInt("id"));
        member.setQqName(resultSet.getString("qq_name"));
        member.setPhone(resultSet.getString("phone"));
        member.setWeixinNum(resultSet.getString("weixin_num"));
        member.setQqGroupNum(resultSet.getString("qq_group_num"));
        member.setQqGroupName(resultSet.getString("qq_group_name"));
        member.setMoney(resultSet.getDouble("money"));
        member.setPwd(resultSet.getString("pwd"));
        return member;
    }
}
