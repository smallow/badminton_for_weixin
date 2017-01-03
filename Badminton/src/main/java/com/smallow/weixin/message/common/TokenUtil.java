package com.smallow.weixin.message.common;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by smallow o
 * n 16/8/22.
 */
@Component
public class TokenUtil {

    @Resource
    private DataSource dataSource;

    public Map<String, Object> getToken() throws SQLException {
        Connection con = null;

        String sql = "select * from t_token order by createTime desc limit 0,1";
        String access_token = "";
        Map<String, Object> map = new HashMap<String, Object>();
        Integer expires_in = 0;
        try {
            con = dataSource.getConnection();
            QueryRunner qr = new QueryRunner();
            List<Map<String, Object>> list = qr.query(con, sql, new MapListHandler());
            if (list != null && list.size() > 0) {
                map = list.get(0);
                access_token = (String) map.get("access_token");
                expires_in = Integer.parseInt(String.valueOf(map.get("expires_in")));
                map.put("access_token", access_token);
                map.put("expires_in", expires_in);
            }

        } catch (Exception ex) {
            System.out.println("数据库操作异常：" + ex.getMessage());
        } finally {
            DbUtils.close(con);
        }
        return map;
    }

    public void saveToken(Token token) {
        //存入数据库中
        Connection con = null;
        String sql = "insert into t_token(access_token,expires_in,createTime)values(?,?,?)";
        try {
            con = dataSource.getConnection();
            QueryRunner qr = new QueryRunner();
            int i = qr.update(con, sql, token.getAccessToken(), token.getExpiresIn(), new Date());
            if (i > 0) {
                System.out.println("token保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(con);
        }
    }

    protected final Logger log = Logger.getLogger(TokenUtil.class);
    //private static Logger log = LoggerFactory.getLogger(TokenUtil.class);
    public static Token accessToken = null;
    // 第三方用户唯一凭证
    public static String appid = "wx046dc37216ababdb";//测试号
    //public static String appid = "wx87c983d7bbda6f41";//飞羽正式号

    // 第三方用户唯一凭证密钥
    public static String appsecret = "d233413f231c1290e0b52240f8d01d85";//测试号
    //public static String appsecret = "bb981d3b5adb6b9251653e1e010f3735";//飞羽正式号


    @Scheduled(cron = "0 0 */2 * * ?")
    public void run() {
        log.info("开始执行定时刷新token任务!");

        try {
            accessToken = CommonUtil.getToken(appid, appsecret);
            saveToken(accessToken);
            log.info("获取access_token成功，有效时长{}秒 token:{}" + accessToken.getExpiresIn() + accessToken.getAccessToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
