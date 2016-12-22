package com.smallow.badminton.controller;

import com.alibaba.fastjson.JSON;
import com.smallow.badminton.enity.Group;
import com.smallow.badminton.enity.Member;
import com.smallow.badminton.service.BadmintonService;
import com.smallow.badminton.service.MemberService;
import com.smallow.badminton.util.Md5PwdEncoder;
import com.smallow.badminton.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghuidong on 2016/12/19.
 */
@Controller
public class MemberController {
    @Autowired
    private BadmintonService badmintonService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/searchMember.do", method = {RequestMethod.POST})
    public void searchMember(HttpServletRequest request, HttpServletResponse response, String qqNum, String qqName,String qqGroupNum,String phone) {

        List<Member> list = memberService.searchMember(qqNum,qqName,qqGroupNum,phone);
        String json = JSON.toJSONString(list);
        ResponseUtils.renderJson(response, json);
    }
    @RequestMapping(value = "/addMember.do", method = {RequestMethod.POST,RequestMethod.GET})
    public String addMember(HttpServletRequest request, HttpServletResponse response){
        List<Group> list = badmintonService.getAllGroup();
        request.setAttribute("list", JSON.toJSON(list).toString());
        return "addMember";
    }
    @RequestMapping(value = "/saveMember.do", method = {RequestMethod.POST})
    public void saveMember(HttpServletRequest request,HttpServletResponse response,String id,String qqName,String qqNum,String phone,String money,String qqGroupName,String qqGroupNum){

        Map<String,String> map=new HashMap<String, String>();
        if(id!=null && !"0".equals(id)){
//            Member member=memberService.getMemberById(Integer.parseInt(id));
//            member.setQqName(qqName);
//            member.setQqNum(qqNum);
//            member.setPhone(phone);
//            member.setMoney(Double.valueOf(money));
//            member.setQqGroupName(qqGroupName);
//            member.setQqGroupNum(qqGroupNum);
             boolean b=memberService.updateMemberByProperties(new String[]{"qq_name","qq_num","phone","money","qq_group_num","qq_group_name"},new Object[]{qqName,qqNum,phone,Double.valueOf(money==null?"0":money),qqGroupNum,qqGroupName},new String[]{"id"},new Object[]{Integer.parseInt(id)},new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DOUBLE,Types.VARCHAR,Types.VARCHAR,Types.INTEGER});
             if(b){
                 map.put("msg","success");
             }else{
                 map.put("msg","fail");
             }
        }else{
            Member member=new Member();
            member.setQqName(qqName);
            member.setQqNum(qqNum);
            member.setPhone(phone);
            member.setMoney(Double.valueOf(money));
            member.setQqGroupName(qqGroupName);
            member.setQqGroupNum(qqGroupNum);
            member.setPwd(Md5PwdEncoder.encodePassword("123",null));
            int i=memberService.saveMember(member);
            if(i>0){
                map.put("msg","success");
            }else{
                map.put("msg","fail");
            }


        }

        String json=JSON.toJSONString(map);
        ResponseUtils.renderJson(response,json);
    }
    @RequestMapping(value = "/editMember.do", method = {RequestMethod.GET})
    public String editMember(Integer id,HttpServletRequest request){
        List<Group> list = badmintonService.getAllGroup();
        request.setAttribute("list", JSON.toJSON(list).toString());
        Member member=memberService.getMemberById(id);
        request.setAttribute("member",member);
        return "addMember";
    }


    @RequestMapping(value = "/delMember.do", method = {RequestMethod.POST})
    public void delMember(String ids,HttpServletRequest request, HttpServletResponse response){
        Map<String,String> map=new HashMap<String, String>();
        if(ids!=null && !"".equals(ids)){
            String []_ids=ids.split(",");
            int [] __ids=new int[_ids.length];
            int i=0;
            for(String id:_ids){
                __ids[i]=Integer.parseInt(id);
                i++;
            }
            int[] result=memberService.deleteMemberByIds(__ids);
            if(result.length>0){
                map.put("msg","success");
            }else{
                map.put("msg","fail");
            }
        }



        String json=JSON.toJSONString(map);
        ResponseUtils.renderJson(response,json);
    }

}
