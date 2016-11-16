package com.smallow.badminton.controller;

import com.alibaba.fastjson.JSON;
import com.smallow.badminton.Constant;
import com.smallow.badminton.enity.*;
import com.smallow.badminton.service.ActivityService;
import com.smallow.badminton.service.BadmintonService;
import com.smallow.badminton.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
    SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd");
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
            chargeMember.setId(3);
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
}
