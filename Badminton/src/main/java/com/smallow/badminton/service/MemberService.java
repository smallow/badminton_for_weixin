package com.smallow.badminton.service;

import com.smallow.badminton.enity.Member;

import java.util.List;

/**
 * Created by smallow on 16/10/13.
 */
public interface MemberService {

    /**
     * 根据群号获取群成员
     * @param qqGroupNum
     * @return
     */
    public List<Member> getMembersByQqNum(String qqGroupNum);

    /**
     * 根据ID获取会员信息
     * @param id
     * @return
     */
    public Member getMemberById(Integer id);


    /**
     * 更新会员信息
     * @param propertyName
     * @param propertyValue
     * @param types
     * @return
     */
    public boolean updateMemberByProperties(String[] propertyName, Object[] propertyValue,String[]condition,Object[]conditionValue, int[] types);


    /**
     * 查询会员信息
     * @param qqNum
     * @param qqName
     * @param qqGroupNum
     * @param phone
     * @return
     */
    List<Member> searchMember(String qqNum,String qqName,String qqGroupNum, String phone);

    public int saveMember(Member member);


    public int[] deleteMemberByIds(int ids[]);


    public Member memberLogin(String phone,String pwd);
}
