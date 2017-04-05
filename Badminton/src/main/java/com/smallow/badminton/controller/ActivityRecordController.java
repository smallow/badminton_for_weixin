package com.smallow.badminton.controller;

import com.alibaba.fastjson.JSON;
import com.smallow.badminton.Constant;
import com.smallow.badminton.enity.*;
import com.smallow.badminton.service.*;
import com.smallow.badminton.util.Md5PwdEncoder;
import com.smallow.badminton.util.ResponseUtils;
import com.smallow.badminton.vo.ActivityRecordVo;
import com.smallow.badminton.vo.ActivityVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
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

    @Autowired
    private BaoMingService baoMingService;

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
    public void getMemberByGroupNum(HttpServletRequest request, HttpServletResponse response, String qqNum, String queryText) {

        List<Member> list = memberService.getMembersByQqNum(qqNum);
        String json = JSON.toJSONString(list);
        ResponseUtils.renderJson(response, json);
    }


    private DecimalFormat df = new java.text.DecimalFormat("#.00");

    @RequestMapping(value = "/addActivityRecordSubmit.do", method = {RequestMethod.POST})
    public void addActivityRecordSubmit(HttpServletRequest request, HttpServletResponse response, String memberIds, String friendNums, Integer siteNum, float timeNum, Integer badmintonNum, String activityId) {
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
                boolean updateSign = activityService.updateActivityByProperties(new String[]{"site_num", "bad_num", "time_num", "total_cost", "avg_cost", "total_person","aty_status"},
                        new Object[]{siteNum, badmintonNum, timeNum, totalCost, avgCost, totalPerson,Activity.ATY_STATUS_YJS},
                        new String[]{"id"},
                        new Object[]{Integer.parseInt(activityId)},
                        new int[]{Types.INTEGER, Types.INTEGER, Types.FLOAT, Types.DOUBLE, Types.DOUBLE, Types.INTEGER,Types.VARCHAR, Types.INTEGER});
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
                            //BigDecimal   bd   =   new   BigDecimal("3.14159265");
                            //bd   =   bd.setScale(2, BigDecimal.ROUND_HALF_UP);


                            activityRecordVo.setCurrentDayCost(new Double(df.format(activity.getAvgCost() * (_friendNum + 1))));
                            activityRecordVo.setCurrentDayLeft(new Double(df.format(member.getMoney())));
                            list.add(activityRecordVo);
                        }
                    }
                    mapReturn.put("msg", "success");
                    mapReturn.put("data", list);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mapReturn.put("msg", "fail");
            }
        }

        ResponseUtils.renderJson(response, JSON.toJSONString(mapReturn));

    }

    @RequestMapping(value = "/queryAtyRecord.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryAtyRecord(HttpServletRequest request, HttpServletResponse response, Integer memberId) {
        List<ActivityRecordVo> list = null;
        if (memberId != null) {
            list = activityRecordService.queryActivityRecordByMemberId(memberId);
            if (list != null && list.size() > 0) {
                request.setAttribute("list", JSON.toJSONString(list));
            } else {
                request.setAttribute("list", "");
            }

        }


        return "queryAtyRecord";
    }

    @RequestMapping(value = "/openLogin.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String openLogin(HttpServletRequest request, HttpServletResponse response) {
        return "openLogin";
    }

    @RequestMapping(value = "/memberLogin.do", method = {RequestMethod.POST})
    public void memberLogin(HttpServletRequest request, HttpServletResponse response, String phone, String pwd) {
        Map<String, Object> map = new HashedMap();
        Member member = memberService.memberLogin(phone, Md5PwdEncoder.encodePassword(pwd, null));
        if (member == null) {
            map.put("msg", "fail");
        } else {
            map.put("msg", "success");
            map.put("memberId", member.getId());
            map.put("memberName", member.getQqName());
            request.getSession().setAttribute("member", member);
        }

        String json = JSON.toJSONString(map);
        ResponseUtils.renderJson(response, json);
    }

    @RequestMapping(value = "/checkLogin.do", method = {RequestMethod.POST})
    public void checkLogin(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashedMap();
        Member member = (Member) request.getSession().getAttribute("member");
        if (member != null) {
            map.put("memberId", member.getId());
            map.put("memberName",member.getQqName());
        }
        String json = JSON.toJSONString(map);
        ResponseUtils.renderJson(response, json);
    }

    @RequestMapping(value = "/goBaoMing.do", method = {RequestMethod.GET})
    public String goBaoMing(HttpServletRequest request, HttpServletResponse response, Integer memberId, Integer atyId,String memberName) {

        if (atyId != null) {
            Activity activity = activityService.getActivityById(atyId);
            List<BaoMing> baomingRecord = baoMingService.queryBaoMingRecordByAtyId(atyId);
            BaoMing personalBaoMingRecord=baoMingService.queryPersonalBaoMingRecordByAtyIdAndMemberId(atyId,memberId);
            if (activity != null) {
                ActivityVo bean = parseAtyVo(activity);
                String json = JSON.toJSONString(bean);
                request.setAttribute("atyJson", json);
            }

            if (baomingRecord != null && baomingRecord.size() > 0) {
                String json = JSON.toJSONString(baomingRecord);
                request.setAttribute("recordJson", json);
            }
            if(personalBaoMingRecord!=null){
                String json = JSON.toJSONString(personalBaoMingRecord);
                request.setAttribute("personalRecordJson", json);
            }
        }
        if(memberName!=null){
            try {
                memberName=java.net.URLDecoder.decode(memberName,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        request.setAttribute("memberId", memberId);
        request.setAttribute("memberName", memberName);
        request.setAttribute("atyId", atyId);
        return "goBaoMing";

    }

    @RequestMapping(value = "/baomingSubmit.do", method = {RequestMethod.POST})
    public void baomingSubmit(HttpServletRequest request, HttpServletResponse response,Integer id,boolean isCancel, Integer memberId, Integer atyId, String friendNums, String friendNames,String memberName) {

        Map<String, Object> map = new HashedMap();
        if(id!= null && !"".equals(id)){
            BaoMing bean =baoMingService.getBaoMingRecordById(id);
            bean.setFriendName(friendNames);
            if(!"".equals(friendNums)){
                bean.setFriendNum(Integer.parseInt(friendNums));
            }else{
                bean.setFriendNum(0);
            }
            bean.setCancle(isCancel);
            bean.setUpdateTime(new Timestamp(new Date().getTime()));
            int i= baoMingService.updateBaoMing(bean);
            if(i>0){
                map.put("msg","success");

            }else{
                map.put("msg","fail");
            }
        }else{
            BaoMing bean = new BaoMing();
            bean.setAtyId(atyId);
            bean.setMemberId(memberId);
            if(!"".equals(friendNums)){
                bean.setFriendNum(Integer.parseInt(friendNums));
            }else{
                bean.setFriendNum(0);
            }
            bean.setFriendName(friendNames);
            bean.setCancle(isCancel);
            bean.setMemberName(memberName);
            int i= baoMingService.saveBaoMingRecord(bean);
            if(i>0){
                map.put("msg","success");
            }else{
                map.put("msg","fail");
            }
        }


        //报名信息更新成功之后要更新活动 总人数
        int totalPerson=activityService.getTotalPersonByAtyId(atyId);
        //boolean b=activityService.updateActivityByProperties(new String[]{"total_person"},new Object[]{1},new String[]{"id"},new Object[]{atyId},new int[]{Types.INTEGER,Types.INTEGER});
        boolean b=activityService.gengxinAtyTotalPerson(totalPerson+Integer.parseInt(friendNums),atyId);
        if(b){
            System.out.println("活动人数更新成功!");
        }
        String json=JSON.toJSONString(map);
        ResponseUtils.renderJson(response,json);

    }


    @RequestMapping(value = "/cancelBaomingSubmit.do", method = {RequestMethod.POST})
    public void cancelBaomingSubmit(HttpServletRequest request, HttpServletResponse response,Integer id) {
        Map<String, Object> map = new HashedMap();
        if(id!=null && id!=0){
            BaoMing bean =baoMingService.getBaoMingRecordById(id);
            bean.setCancle(true);
            int i= baoMingService.updateBaoMing(bean);
            if(i>0){
                map.put("msg","success");
            }else{
                map.put("msg","fail");
            }


            //报名信息更新成功之后要更新活动 总人数
            int totalPerson=activityService.getTotalPersonByAtyId(bean.getAtyId());
            //boolean b=activityService.updateActivityByProperties(new String[]{"total_person"},new Object[]{10},new String[]{"id"},new Object[]{bean.getAtyId()},new int[]{Types.INTEGER,Types.INTEGER});
            boolean b=activityService.gengxinAtyTotalPerson(totalPerson,bean.getAtyId());
            if(b){
                System.out.println("活动人数更新成功!");
            }

        }

        String json=JSON.toJSONString(map);
        ResponseUtils.renderJson(response,json);
    }


    /**
     * 结算活动
     * @param request
     * @param response
     * @param atyId
     */

    @RequestMapping(value = "/finishAty.do", method = {RequestMethod.POST})
    public void finishAty(HttpServletRequest request, HttpServletResponse response,Integer atyId,Integer siteNum, float timeNum, Integer badmintonNum) {
        List<ActivityRecordVo> returnList = new ArrayList<ActivityRecordVo>();
        Map<String, Object> mapReturn = new HashMap<String, Object>();

        List<BaoMingRecordVo> list=activityRecordService.queryActivityBaoMingRecordByAtyId(atyId);

        int totalPerson = 0;
        for(BaoMingRecordVo baoMingRecordVo:list){
            totalPerson += baoMingRecordVo.getFriendNum()+1;//1 是他自身
        }

        System.out.println("打球总人数:" + totalPerson);
        double totalCost = 30.00 * timeNum * siteNum + 5 * badmintonNum;//场地费一小时30,球费用一个5块
        double avgCost = new Double(Constant.decimalFormat.format(totalCost / totalPerson)).doubleValue();
        boolean updateSign = activityService.updateActivityByProperties(new String[]{"site_num", "bad_num", "time_num", "total_cost", "avg_cost", "total_person","aty_status"},
                new Object[]{siteNum, badmintonNum, timeNum, totalCost, avgCost, totalPerson,Activity.ATY_STATUS_YJS},
                new String[]{"id"},
                new Object[]{atyId},
                new int[]{Types.INTEGER, Types.INTEGER, Types.FLOAT, Types.DOUBLE, Types.DOUBLE, Types.INTEGER,Types.VARCHAR, Types.INTEGER});
        if (updateSign) {
            System.out.println("活动信息更新成功,开始更新个人活动记录");
            Activity atyBean=activityService.getActivityById(atyId);
            Member member = null;
            ActivityRecordVo activityRecordVo = null;
            Member chargeMember = memberService.getMemberById(atyBean.getChargeMember().getId());
            for (BaoMingRecordVo baoMingRecordVo:list) {
                member = memberService.getMemberById(baoMingRecordVo.getMemberId());
                int _friendNum = baoMingRecordVo.getFriendNum();
                member.setMoney(member.getMoney() - avgCost * (_friendNum + 1));
                boolean b = activityRecordService.savePersonalActivityRecord(member, chargeMember, atyBean, _friendNum);
                if (b) {
                    activityRecordVo = new ActivityRecordVo();
                    activityRecordVo.setAtyAddress(atyBean.getAddress());
                    activityRecordVo.setAtyDate(Constant.formatForYYYYMMDD.format(atyBean.getDate()));
                    activityRecordVo.setAtySiteNum(atyBean.getSiteNum());
                    activityRecordVo.setAtyTimeNum(atyBean.getTimeNum());
                    activityRecordVo.setAtyBadNum(atyBean.getBadmintonNum());
                    activityRecordVo.setAtyTotalPerson(atyBean.getTotalPerson());
                    activityRecordVo.setAtyTotalMoney(atyBean.getTotalCost());
                    activityRecordVo.setAtyAvgMoney(atyBean.getAvgCost());
                    activityRecordVo.setQqName(member.getQqName());
                    activityRecordVo.setFriendNum(_friendNum);
                    //BigDecimal   bd   =   new   BigDecimal("3.14159265");
                    //bd   =   bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                    activityRecordVo.setCurrentDayCost(new Double(df.format(atyBean.getAvgCost() * (_friendNum + 1))));
                    activityRecordVo.setCurrentDayLeft(new Double(df.format(member.getMoney())));
                    returnList.add(activityRecordVo);
                }
            }
            mapReturn.put("msg", "success");
            mapReturn.put("data", returnList);
            mapReturn.put("atyBean",atyBean);
        }
        ResponseUtils.renderJson(response, JSON.toJSONString(mapReturn));

    }




    private ActivityVo parseAtyVo(Activity aty) {


        ActivityVo bean = new ActivityVo();
        try {
            BeanUtils.copyProperties(bean, aty);
            bean.setStartTime(Constant.formatForYYYYMMDDHHMM.format(aty.getStartTime()));
            bean.setEndTime(Constant.formatForYYYYMMDDHHMM.format(aty.getEndTime()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return bean;
    }


}
