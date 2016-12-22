package com.smallow.badminton.dao;

import com.smallow.badminton.enity.ActivityRecord;
import com.smallow.badminton.vo.ActivityRecordVo;

import java.util.List;

/**
 * Created by smallow on 16/10/14.
 */
public interface ActivityRecordDao {

    public int saveActivityRecord(final ActivityRecord activityRecord);
    public List<ActivityRecord> queryActivityRecordByProerties(String[] propertyName, Object[] propertyValue, int[] types);


    public List<ActivityRecordVo> queryAtyRecordViewByProperties(String[] propertyName, Object[] propertyValue, int[] types);
}
