package com.smallow.badminton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by smallow on 16/8/17.
 */
public class Constant {

    public static final String TOKEN="smallow521";
    public static final SimpleDateFormat formatForYYYYMMDD=new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat formatForYYYYMMDDHHMMSS=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat formatForYYYYMMDDHHMM=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final DecimalFormat decimalFormat=new DecimalFormat("0.00");
    public static void main(String args[]){
        String str="update badminton_activity a set  a.address='千羽羽毛球馆',  a.bad_num=2,  a.charge_member_id=3, a.charge_member_name='星星',a.charge_member_phone='15036022292',  a.date='2016-10-12',  a.end_time='2016-10-12 08:00:00',  a.site_num=1,  a.start_time='2016-10-12 06:00:00',  a.time_num=2,  a.total_cost=180.0, ";
        str=str.replaceAll("\\,\\s+$","");
        System.out.println(str);
    }
}
