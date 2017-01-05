package com.smallow.badminton.service.impl;

import com.smallow.badminton.dao.BaoMingDao;
import com.smallow.badminton.enity.BaoMing;
import com.smallow.badminton.service.BaoMingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghuidong on 2017/1/4.
 */
@Service
public class BaoMingServiceImpl implements BaoMingService {


    @Autowired
    private BaoMingDao baoMingDao;

    @Override
    public int saveBaoMingRecord(BaoMing bean) {
        return baoMingDao.saveBaoMingRecord(bean);
    }

    @Override
    public List<BaoMing> queryBaoMingRecordByAtyId(Integer atyId) {
        List<BaoMing> data=new ArrayList<BaoMing>();
        data=baoMingDao.query("select * from badminton_baoming b  where b.aty_id=? and sf_qj=0 order by baoming_time desc ",new Object[]{atyId},new int[]{Types.INTEGER});
        return data;

    }

    @Override
    public BaoMing queryPersonalBaoMingRecordByAtyIdAndMemberId(Integer atyId, Integer memberId) {
        List<BaoMing> data=baoMingDao.query("select * from badminton_baoming where aty_id=? and member_id=? ",new Object[]{atyId,memberId},new int[]{Types.INTEGER,Types.INTEGER});
        if(data!=null && data.size()>0)
            return data.get(0);
        return null;
    }

    @Override
    public BaoMing getBaoMingRecordById(Integer id) {
        List<BaoMing> data=baoMingDao.query("select * from badminton_baoming where id=? ",new Object[]{id},new int[]{Types.INTEGER});
        if(data!=null && data.size()>0)
            return data.get(0);
        return null;
    }

    @Override
    public int updateBaoMing(BaoMing bean) {
        return baoMingDao.updateBaoMingByProperties(new String[]{"friend_num","friend_names","update_time","sf_qj"},new Object[]{bean.getFriendNum(),bean.getFriendName(),bean.getUpdateTime(),bean.isCancle()},new String[]{"id"},new Object[]{bean.getId()},new int[]{Types.INTEGER,Types.VARCHAR,Types.TIMESTAMP,Types.BOOLEAN,Types.INTEGER});
    }
}
