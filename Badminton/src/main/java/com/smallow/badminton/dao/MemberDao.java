package com.smallow.badminton.dao;

import com.smallow.badminton.enity.Member;

import java.util.List;

/**
 * Created by smallow on 16/10/13.
 */
public interface MemberDao {


    public Member getMemberByProerties(String[] propertyName, Object[] propertyValue, int[] types);

    public Member getMemberBySql(String sql, Object... objects);


    public List<Member> queryMemberByProperties(String[] propertyName, Object[] propertyValue, int[] types);


    public int updateMemberByProperties(String[] propertyName, Object[] propertyValue,String[]condition,Object[]conditionValue, int[] types);
}
