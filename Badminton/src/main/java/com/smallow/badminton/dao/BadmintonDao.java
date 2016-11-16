package com.smallow.badminton.dao;

import com.smallow.badminton.enity.Activity;
import com.smallow.badminton.enity.ActivityRecord;
import com.smallow.badminton.enity.Group;
import com.smallow.badminton.enity.Member;


import java.util.List;
import java.util.Map;

/**
 * Created by smallow on 16/8/30.
 */

public interface BadmintonDao {




    public List<Group> getAllGroup();







    public void batchInsertActivityRecord(List<ActivityRecord> list);

    public ActivityRecord getPersonalActivityRecord(Integer memberId,Integer activiyId);





}
