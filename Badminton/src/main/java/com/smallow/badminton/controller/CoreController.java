package com.smallow.badminton.controller;

import com.smallow.badminton.Constant;
import com.smallow.weixin.message.common.CoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by smallow on 16/8/19.
 */
@Controller
public class CoreController {

    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @RequestMapping(value = "/CoreServlet", method = {RequestMethod.GET, RequestMethod.POST})
    public void WeiXinCore(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getMethod().toLowerCase();
        if (method != null) {
            if ("get".equals(method)) {
                String signature = request.getParameter("signature");
                String timestamp = request.getParameter("timestamp");
                String nonce = request.getParameter("nonce");
                String echostr = request.getParameter("echostr");
                access(signature, timestamp, nonce, echostr, response);
            } else if ("post".equals(method)) {
                //System.out.println("enter post");
                try {
                    // 接收消息并返回消息
                    String respXml = CoreService.processRequest(request);
                    System.out.println("respXml:"+respXml);
                    // 响应消息
                    PrintWriter out = response.getWriter();
                    out.print(respXml);
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }




    /**
     * 验证URL是否来自微信
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @param response
     */
    private void access(String signature, String timestamp, String nonce, String echostr, HttpServletResponse response) {
        System.out.println(format.format(new Date())+" 开始验证...");
        ArrayList<String> list = new ArrayList<String>();
        list.add(nonce);
        list.add(timestamp);
        list.add(Constant.TOKEN);
        Collections.sort(list);
        String strEncode = SHA1(list.get(0) + list.get(1) + list.get(2));
        System.out.println("strEncode:" + strEncode);
        if (strEncode.equals(signature)) {
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.write(echostr);
                System.out.println("成功返回 echostr：" + echostr);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                    out = null;
                }
            }
        } else {
            System.out.println("非法请求");
        }
    }


    private static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toLowerCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
