package com.smallow.badminton.controller;

import com.alibaba.fastjson.JSON;
import com.smallow.badminton.Constant;
import com.smallow.badminton.enity.Activity;
import com.smallow.badminton.enity.ActivityRecord;
import com.smallow.badminton.enity.Group;
import com.smallow.badminton.enity.Member;
import com.smallow.badminton.service.ActivityRecordService;
import com.smallow.badminton.service.ActivityService;
import com.smallow.badminton.service.BadmintonService;
import com.smallow.badminton.service.MemberService;
import com.smallow.badminton.util.ResponseUtils;
import com.smallow.badminton.vo.ActivityRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smallow on 16/9/18.
 */

@Controller
public class ActivityRecordController {

    @Autowired
    private BadmintonService badmintonService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRecordService activityRecordService;

    /**
     * 报名参加活动
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/applyForAty.do", method = {RequestMethod.POST})
    public void applyForAty(HttpServletRequest request, HttpServletResponse response) {
        String phone = request.getParameter("phone");
        String qqNames = request.getParameter("qqNames");
        if (qqNames != null && !"".equals(qqNames)) {

        }
    }


    @RequestMapping(value = "/addActivityRecord.do", method = {RequestMethod.GET})
    public String addActivityRecord(HttpServletRequest request, HttpServletResponse response, String activityId) {
        List<Group> list = badmintonService.getAllGroup();
        request.setAttribute("activityId", activityId);
        request.setAttribute("list", JSON.toJSON(list).toString());
        return "addActivityRecord";
    }


    @RequestMapping(value = "/getMemberByGroupNum.do", method = {RequestMethod.POST})
    public void getMemberByGroupNum(HttpServletRequest request, HttpServletResponse response, String qqNum) {

        List<Member> list = memberService.getMembersByQqNum(qqNum);
        String json = JSON.toJSONString(list);
        ResponseUtils.renderJson(response, json);
    }


    @RequestMapping(value = "/addActivityRecordSubmit.do", method = {RequestMethod.POST})
    public void addActivityRecordSubmit(HttpServletRequest request, HttpServletResponse response, String memberIds, String friendNums, Integer siteNum, Integer timeNum, Integer badmintonNum, String activityId) {
        List<ActivityRecordVo> list = new ArrayList<ActivityRecordVo>();
        Map<String, Object> mapReturn = new HashMap<String, Object>();
        if (activityId != null && !"".equals(activityId)) {
            /**
             * 计算场地费用更新活动
             */

            try {
                String[] memberIdsArr = memberIds.split(",");
                String[] friendNumsArr = friendNums.split(",");
                Map<String, String> map = new HashMap<String, String>();//记录会员id与带人数的对应关系集合

                int totalPerson = 0;
                for (int i = 0; i < friendNumsArr.length; i++) {
                    totalPerson += Integer.parseInt(friendNumsArr[i]);
                    map.put(memberIdsArr[i], friendNumsArr[i]);
                }
                totalPerson += memberIdsArr.length;
                System.out.println("打球总人数:" + totalPerson);
                double totalCost = 30.00 * timeNum * siteNum + 5 * badmintonNum;//场地费一小时30,球费用一个5块
                double avgCost = new Double(Constant.decimalFormat.format(totalCost / totalPerson)).doubleValue();
                boolean updateSign = activityService.updateActivityByProperties(new String[]{"site_num", "bad_num", "time_num", "total_cost", "avg_cost", "total_person"},
                        new Object[]{siteNum, badmintonNum, timeNum, totalCost, avgCost, totalPerson},
                        new String[]{"id"},
                        new Object[]{Integer.parseInt(activityId)},
                        new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.DOUBLE, Types.DOUBLE, Types.INTEGER, Types.INTEGER});
                if (updateSign) {
                    System.out.println("活动信息更新成功,开始更新个人活动记录");
                    Activity activity = activityService.getActivityById(Integer.parseInt(activityId));
                    Member member = null;
                    ActivityRecordVo activityRecordVo = null;
                    Member chargeMember = memberService.getMemberById(activity.getChargeMember().getId());
                    for (String memberid : memberIdsArr) {
                        member = memberService.getMemberById(Integer.parseInt(memberid));
                        int _friendNum = Integer.parseInt(map.get(memberid));
                        member.setMoney(member.getMoney() - avgCost * (_friendNum + 1));
                        boolean b = activityRecordService.savePersonalActivityRecord(member, chargeMember, activity, _friendNum);
                        if (b) {
                            activityRecordVo = new ActivityRecordVo();
                            activityRecordVo.setAtyAddress(activity.getAddress());
                            activityRecordVo.setAtyDate(Constant.formatForYYYYMMDD.format(activity.getDate()));
                            activityRecordVo.setAtySiteNum(activity.getSiteNum());
                            activityRecordVo.setAtyTimeNum(activity.getTimeNum());
                            activityRecordVo.setAtyBadNum(activity.getBadmintonNum());
                            activityRecordVo.setAtyTotalPerson(activity.getTotalPerson());
                            activityRecordVo.setAtyTotalMoney(activity.getTotalCost());
                            activityRecordVo.setAtyAvgMoney(activity.getAvgCost());
                            activityRecordVo.setQqName(member.getQqName());
                            activityRecordVo.setFriendNum(_friendNum);
                            activityRecordVo.setMoney(activity.getAvgCost() * (_friendNum + 1));
                            activityRecordVo.setLeftMoney(member.getMoney());
                            list.add(activityRecordVo);
                        }
                    }
                    mapReturn.put("msg","success");
                    mapReturn.put("data",list);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mapReturn.put("msg","fail");
            }
        }

        ResponseUtils.renderJson(response,JSON.toJSONString(mapReturn));

    }


}
