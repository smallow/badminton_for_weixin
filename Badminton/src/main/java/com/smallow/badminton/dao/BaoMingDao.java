package com.smallow.badminton.dao;

import com.smallow.badminton.enity.BaoMing;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghuidong on 2017/1/4.
 */
public interface BaoMingDao {


    /**
     * 保存报名记录
     * @param bean
     * @return
     */
    public int saveBaoMingRecord(BaoMing bean);


    public List<BaoMing> query(String sql,Object[]args,int [] argTypes);

    public List<Map<String,Object>> queryForList(String sql, Object[] args, int[] argTypes);


    public int updateBaoMingByProperties(String[] propertyName, Object[] propertyValue,String[]condition,Object[]conditionValue, int[] types);
}
