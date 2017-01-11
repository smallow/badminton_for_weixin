package com.smallow.badminton.enity;

import com.smallow.badminton.Constant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wanghuidong on 2017/1/9.
 */
public class BaoMingRecordVo extends BeanPropertyRowMapper<BaoMingRecordVo> {


    private Integer id;//报名记录id
    private String memberName;//会员名称
    private Integer memberId;//会员ID
    private Integer atyId;//活动ID



    private Integer friendNum;//带朋友数
    private String friendNames;//朋友名称
    private String  baomingTime;//报名时间
    private String updateTime;//更新时间
    private String isCancle;//是否请假

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getAtyId() {
        return atyId;
    }

    public void setAtyId(Integer atyId) {
        this.atyId = atyId;
    }





    public Integer getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(Integer friendNum) {
        this.friendNum = friendNum;
    }

    public String getFriendNames() {
        return friendNames;
    }

    public void setFriendNames(String friendNames) {
        this.friendNames = friendNames;
    }

    public String getBaomingTime() {
        return baomingTime;
    }

    public void setBaomingTime(String baomingTime) {
        this.baomingTime = baomingTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsCancle() {
        return isCancle;
    }

    public void setIsCancle(String isCancle) {
        this.isCancle = isCancle;
    }

    @Override
    public BaoMingRecordVo mapRow(ResultSet rs, int rowNumber) throws SQLException {
        BaoMingRecordVo bean=new BaoMingRecordVo();
        bean.setId(rs.getInt("id"));
        bean.setMemberId(rs.getInt("member_id"));
        bean.setAtyId(rs.getInt("aty_id"));
        bean.setFriendNum(rs.getInt("friend_num"));
        bean.setFriendNames(rs.getString("friend_names"));
        bean.setBaomingTime(Constant.formatForYYYYMMDDHHMM.format(rs.getTimestamp("baoming_time")));
        bean.setUpdateTime(Constant.formatForYYYYMMDDHHMM.format(rs.getTimestamp("update_time")));
        bean.setIsCancle(parseCancle(rs.getBoolean("sf_qj")));
        bean.setMemberName(rs.getString("qq_name"));
        return bean;
    }


    private String parseCancle(boolean isCancle){
        if(isCancle){
            return "是";
        }

        return "否";
    }
}
