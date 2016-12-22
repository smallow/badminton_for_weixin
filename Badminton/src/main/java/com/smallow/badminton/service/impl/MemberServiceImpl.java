package com.smallow.badminton.service.impl;

import com.smallow.badminton.dao.MemberDao;
import com.smallow.badminton.enity.Member;
import com.smallow.badminton.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * Created by smallow on 16/10/13.
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public List<Member> getMembersByQqNum(String qqGroupNum) {

        return memberDao.queryMemberByProperties(new String[]{"qq_group_num"},new Object[]{qqGroupNum},new int[]{Types.VARCHAR});
    }

    @Override
    public Member getMemberById(Integer id) {
        return memberDao.getMemberByProerties(new String[]{"id"},new Object[]{id},new int[]{Types.INTEGER});
    }

    @Override
    public boolean updateMemberByProperties(String[] propertyName, Object[] propertyValue,String[]condition,Object[]conditionValue, int[] types) {
        int i=memberDao.updateMemberByProperties(propertyName,propertyValue,condition,conditionValue,types);
        if(i>0)
            return true;
        return false;
    }

    @Override
    public List<Member> searchMember(String qqNum,String qqName,String qqGroupNum, String phone) {
        return memberDao.queryMemberByProperties(new String[]{"qq_group_num","qq_num","qq_name","phone"},new Object[]{qqGroupNum,qqNum,qqName,phone},new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR});

    }

    @Override
    public int saveMember(Member member) {

        return memberDao.saveMember(member);
    }

    @Override
    public int[] deleteMemberByIds(int[] ids) {
        return memberDao.deleteMemberByIds(ids);

    }

    @Override
    public Member memberLogin(String phone, String pwd) {
        Member member=null;
        try{
            member=memberDao.getMemberByProerties(new String[]{"phone","pwd"},new Object[]{phone,pwd},new int[]{Types.VARCHAR,Types.VARCHAR});
        }catch (Exception e){
            e.printStackTrace();
        }
        return member;

    }
}
