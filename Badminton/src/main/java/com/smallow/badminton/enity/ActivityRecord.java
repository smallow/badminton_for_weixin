package com.smallow.badminton.enity;

/**
 * Created by smallow on 16/8/30.
 */
public class ActivityRecord {

    private Integer id;
    private Member member;//活动参加人
    private Member chargeMember;//活动负责人
    private Activity activity;//活动
    private Double money;//消费
    private String memo;//备注
    private int friendNum;//携带人数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getChargeMember() {
        return chargeMember;
    }

    public void setChargeMember(Member chargeMember) {
        this.chargeMember = chargeMember;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(int friendNum) {
        this.friendNum = friendNum;
    }
}
