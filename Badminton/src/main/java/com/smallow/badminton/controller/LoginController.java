package com.smallow.badminton.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by wanghuidong on 2016/12/22.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/toAdminLogin.do", method = {RequestMethod.GET})
    public String toAdminLogin(){
        return "adminLogin";
    }

    @RequestMapping(value = "/login.do",method = {RequestMethod.POST})
    public String adminLogin(HttpServletRequest request, HttpServletResponse response,String loginCode,String pwd){
        if(loginCode!=null && pwd!=null){
            if(loginCode.equals("13603456869") || loginCode.equals("13673379480") || loginCode.equals("15036022292")){
                if(pwd.equals("fyymq_7788521")){
                    request.getSession().setAttribute("admin_login_code",loginCode);
                    return "redirect:/";
                }
            }
        }

        return "redirect:/toAdminLogin.do";
    }



}
