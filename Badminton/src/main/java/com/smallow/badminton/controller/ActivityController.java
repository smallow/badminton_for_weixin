package com.smallow.badminton.controller;

import com.alibaba.fastjson.JSON;
import com.smallow.badminton.Constant;
import com.smallow.badminton.enity.*;
import com.smallow.badminton.service.ActivityRecordService;
import com.smallow.badminton.service.ActivityService;
import com.smallow.badminton.service.BadmintonService;
import com.smallow.badminton.util.ResponseUtils;
import com.smallow.badminton.vo.ActivityRecordVo;
import com.smallow.badminton.vo.ActivityVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by smallow on 16/8/30.
 */
@Controller
public class ActivityController {

    @Autowired
    private BadmintonService badmintonService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRecordService activityRecordService;

    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
    SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat format3=new SimpleDateFormat("yyyy-MM-dd hh:ss");


    @RequestMapping(value = "/openAddAty.do", method = {RequestMethod.GET})
    public String openAddAty(HttpServletRequest request, HttpServletResponse response, String activityId) {

        return "openAddAty";
    }
    @RequestMapping(value = "/publishAty.do",method = {RequestMethod.POST})
    public void publishAty(HttpServletRequest request, HttpServletResponse response,String address,String phone,int siteNum,String startTime,String endTime,String chargePersonCode,String chargePersonName){
        Activity activity=new Activity();
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            activity.setStartTime(new Timestamp(format3.parse(startTime).getTime()));
            activity.setEndTime(new Timestamp(format3.parse(endTime).getTime()));
            activity.setDate(new Date(activity.getStartTime().getTime()));

            activity.setAddress(address);
            int hours = (int) ((activity.getEndTime().getTime() - activity.getStartTime().getTime())/(1000 * 60 * 60));
            activity.setTimeNum(hours);
            Member chargeMember=new Member();
            chargeMember.setId(Integer.parseInt(chargePersonCode));
            chargeMember.setQqName(chargePersonName);
            chargeMember.setPhone(phone);
            activity.setChargeMember(chargeMember);
            activity.setSiteNum(siteNum);
            activity.setQqGroupNum("33501640");
            Activity _activity=activityService.publishActivity(activity);
            if(_activity!=null){
                System.out.println("保存成功:"+_activity.getId());
                map.put("msg","success");
            }else{
                System.out.println("添加活动失败:"+ Constant.formatForYYYYMMDDHHMMSS.format(new Date()));
                map.put("msg","fail");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ResponseUtils.renderJson(response,JSON.toJSONString(map));
    }

    @RequestMapping(value = "/saveActivity.do",method = {RequestMethod.POST})
    public void saveActivity(HttpServletRequest request, HttpServletResponse response){
        Activity activity=new Activity();
        Map<String,Object> map=new HashMap<String, Object>();
        activity.setAddress("千羽羽毛球馆");
        Calendar calendar=Calendar.getInstance();
        try {
            calendar.set(Calendar.HOUR_OF_DAY,18);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            activity.setStartTime(new Timestamp(calendar.getTimeInMillis()));
            calendar.set(Calendar.HOUR_OF_DAY,20);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            activity.setEndTime(new Timestamp(calendar.getTimeInMillis()));

            activity.setDate(calendar.getTime());
            //activity.setBadmintonNum(10);
            activity.setTimeNum(2);
            activity.setSiteNum(2);
            Member chargeMember=new Member();
            chargeMember.setId(7);
            chargeMember.setQqName("喜洋洋");
            chargeMember.setPhone("13603456869");
            activity.setChargeMember(chargeMember);
            //activity.setTotalCost(180.00);
            Activity _activity=activityService.publishActivity(activity);
            if(_activity!=null){
                System.out.println("保存成功:"+_activity.getId());
                map.put("msg","success");
            }else{
                System.out.println("添加活动失败:"+ Constant.formatForYYYYMMDDHHMMSS.format(new Date()));
                map.put("msg","fail");
            }


        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg","fail");
        }

        ResponseUtils.renderJson(response,JSON.toJSONString(map));
    }

    @RequestMapping(value = "/getTodaysAty.do",method = {RequestMethod.POST,RequestMethod.GET})
    public void getTodaysAty(HttpServletRequest request,HttpServletResponse response){
        String jsonData="";
        try{
            Activity activity=activityService.getTodayActivity();
            if(activity!=null){
                jsonData= JSON.toJSONString(activity);
            }
        }catch (Exception e){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("msg","none");
            jsonData=JSON.toJSONString(map);
        }

        ResponseUtils.renderJson(response,jsonData);
    }

    @RequestMapping(value = "/getAtyDemo.do",method = {RequestMethod.POST,RequestMethod.GET})
    public void getAtyDemo(int page,HttpServletResponse response){
        System.out.println("开始下载第 "+page+" 页");
        List<News> list=new ArrayList<News>();
        for(int i=10*(page-1);i<10;i++){
            News news=new News();
            news.setTitle("标题__"+i);
            list.add(news);
        }
        ResponseUtils.renderJson(response,JSON.toJSONString(list));
    }
    @RequestMapping(value = "/qqGroupManage.do",method = {RequestMethod.GET})
    public String qqGroupManage(HttpServletRequest request){
        List<Group> list = badmintonService.getAllGroup();

        request.setAttribute("list", JSON.toJSON(list).toString());
        return "qqGroupManage";
    }

    @RequestMapping(value = "/atyManage.do",method = {RequestMethod.GET})
    public String atyManage(HttpServletRequest request){

        return "atyManage";
    }

    @RequestMapping(value = "/queryAtyDetail.do",method = {RequestMethod.GET})
    public String queryAtyDetail(HttpServletRequest request,Integer atyId){
        if(atyId!=null && atyId!=0){
            Activity atyBean=activityService.getActivityById(atyId);
            List<ActivityRecordVo> list=activityRecordService.queryActivityRecordByAtyId(atyId);
            if(list!=null && list.size()>0){
                String listJson=JSON.toJSONString(list);

                request.setAttribute("list",listJson);

            }
            if(atyBean!=null){
                ActivityVo bean=parseAtyVo(atyBean);
                String atyJson=JSON.toJSONString(bean);
                request.setAttribute("activity",atyJson);
            }

            request.setAttribute("atyId",atyId);
        }
        return "queryAtyDetail";
    }

    @RequestMapping(value = "/queryAtyByQQGroupId.do",method = {RequestMethod.POST})
    public void queryAtyByQQGroupId(HttpServletRequest request,HttpServletResponse response,String qqGroupNum,Integer pageNum){
        if(qqGroupNum!=null){
            //int _qqGroupId=Integer.parseInt(qqGroupId);
            List<Activity> list=activityService.queryAtysByQQGroupNum(qqGroupNum,pageNum);
            List<ActivityVo> _list=new ArrayList<ActivityVo>();
            if(list!=null && list.size()>0){
                for(Activity aty:list){
                    _list.add(parseAtyVo(aty));
                }
                String json = JSON.toJSONString(_list);
                ResponseUtils.renderJson(response, json);
            }

        }


    }

    private ActivityVo parseAtyVo(Activity aty){


        ActivityVo bean =new ActivityVo();
        try {
            BeanUtils.copyProperties(bean,aty);
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
