package com.smallow.badminton.enity;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by wanghuidong on 2017/1/3.
 */
public class BaoMing extends BeanPropertyRowMapper<BaoMing> {

    private Integer id;
    private Integer memberId;//会员ID
    private Integer atyId;//活动ID
    private String memberName;//会员名称
    private Integer friendNum;//带朋友数
    private String friendName;//朋友名字
    private Timestamp baoMingTime;//报名时间
    private Timestamp updateTime;//更新时间
    private boolean isCancle;//是否取消


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Timestamp getBaoMingTime() {
        return baoMingTime;
    }

    public void setBaoMingTime(Timestamp baoMingTime) {
        this.baoMingTime = baoMingTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isCancle() {
        return isCancle;
    }

    public void setCancle(boolean cancle) {
        isCancle = cancle;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public BaoMing mapRow(ResultSet rs, int rowNumber) throws SQLException {
        BaoMing baoMing=new BaoMing();
        baoMing.setId(rs.getInt("id"));
        baoMing.setMemberId(rs.getInt("member_id"));
        baoMing.setAtyId(rs.getInt("aty_id"));
        baoMing.setFriendName(rs.getString("friend_names"));
        baoMing.setFriendNum(rs.getInt("friend_num"));
        baoMing.setBaoMingTime(rs.getTimestamp("baoming_time"));
        baoMing.setUpdateTime(rs.getTimestamp("update_time"));
        baoMing.setCancle(rs.getBoolean("sf_qj"));
        baoMing.setMemberName(rs.getString("qq_name"));
        return baoMing;
    }
}
