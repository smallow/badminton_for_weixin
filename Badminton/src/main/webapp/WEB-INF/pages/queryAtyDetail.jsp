<%--
  Created by IntelliJ IDEA.
  User: wanghuidong
  Date: 2016/12/28
  Time: 下午4:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String loginCode=(String)session.getAttribute("admin_login_code");
    //System.out.println(loginCode);
    if(loginCode==null){
        response.sendRedirect("/toAdminLogin.do");
    }


String list=(String)request.getAttribute("list");
String activity=(String)request.getAttribute("activity");
Object tmp=request.getAttribute("atyId");
int atyId=0;
if(tmp!=null){
    try{
        atyId=Integer.parseInt(String.valueOf(tmp));
    }catch (Exception e){
        e.printStackTrace();
    }
}

%>
<html>
<head>
    <title>活动详情</title>
</head>
<body>
<div class="modal-header">
    <h4 class="modal-title" >活动详情</h4>
</div>

<div class="modal-body" style="height:520px;">

    <div class="container">
        <div class="row clearfix">
            <div class="col-md-6 column">
                <div class="list-group">
                    <a class="list-group-item active">日期:&nbsp;&nbsp;<span id="aty_date"></span></a>
                    <div class="list-group-item">
                        <h4 id="aty_address" class="list-group-item-heading"></h4>
                        <p class="list-group-item-text">
                            时间:&nbsp;&nbsp;&nbsp;<span id="aty_start_time" style="color: #eb9316;"></span>&nbsp;到&nbsp;<span id="aty_end_time" style="color:#eb9316;"></span>
                        </p>
                    </div>
                    <div class="list-group-item">
                        场地数:&nbsp;&nbsp;&nbsp;<span id="aty_site_num" class="list-group-item-text" style="color: #d43f3a;font-size: 20px;"></span>
                    </div>
                    <div class="list-group-item">
                        时间数:&nbsp;&nbsp;&nbsp;<span id="aty_time_num" class="list-group-item-text" style="color: #d43f3a;font-size: 20px;"></span>
                    </div>
                    <div class="list-group-item" id="div_bad_num" style="display: none;">
                        用球数:&nbsp;&nbsp;&nbsp;<span id="aty_bad_num" class="list-group-item-text" style="color: #d43f3a;font-size: 20px;"></span>
                    </div>
                    <div class="list-group-item">
                        总人数:&nbsp;&nbsp;&nbsp;<span id="aty_total_person" class="list-group-item-text" style="color: #d9534f;font-size: 20px;"></span>
                    </div>
                    <div class="list-group-item" id="div_total_cost" style="display: none;">
                        总消费:&nbsp;&nbsp;&nbsp;<span id="aty_total_cost" class="list-group-item-text" style="color: #d9534f;font-size: 20px;"></span>&nbsp;(元)
                    </div>
                    <div class="list-group-item" id="div_avg_cost" style="display: none;">
                        人均消费:&nbsp;&nbsp;&nbsp;<span id="aty_avg_cost" class="list-group-item-text" style="color: #d9534f;font-size: 20px;"></span>&nbsp;(元)
                    </div>
                    <div class="list-group-item">
                        联系人:&nbsp;&nbsp;&nbsp;<span id="aty_charge_member_name" class="list-group-item-text"></span>
                    </div>
                    <div class="list-group-item">
                        联系电话:&nbsp;&nbsp;&nbsp;<span id="aty_charge_member_phone" class="list-group-item-text"></span>
                    </div>

                    <div class="row" id="jieSuanBtn" style="display: none;">
                        <div class="col-sm-3" >
                            <%--<button class="btn btn-default" onclick="search()">搜索</button>--%>
                            <div class="form-group"style="padding: 15px;">
                                <input type="text" id="aty_siteNum" class="form-control" placeholder="场地数">
                            </div>

                        </div>
                        <div class="col-sm-3" style="padding: 15px;">
                            <div class="form-group">
                                <input type="text" id="aty_badmintonNum"  class="form-control" placeholder="用球数">
                            </div>
                        </div>
                        <div class="col-sm-3" style="padding: 15px;">
                            <div class="form-group">
                                <input type="text" id="aty_timeNum" class="form-control" placeholder="时间数">
                            </div>
                        </div>


                        <div class="col-sm-3" style="padding: 15px;">
                            <button type="button" class="btn btn-danger" onclick="finishAty()">结算活动</button>
                        </div>

                    </div>

                </div>
            </div>
            <div class="col-md-6 column">
                <div class="table-responsive">
                    <table class="table table-condensed table-bordered table-hover" style="font-size: 10px;">
                        <thead>
                        <tr style="display: none;" id="atyHead1">
                            <th>名称</th>
                            <th>带人数</th>
                            <th>带人名称</th>
                            <th>报名时间</th>
                            <th>是否请假</th>
                        </tr>
                        <tr style="display: none;" id="atyHead2">
                            <th>名称</th>
                            <th>带人数</th>
                            <th>活动人均A费(元)</th>
                            <th>当天消费(元)</th>
                            <th>当天结余(元)</th>
                        </tr>
                        </thead>
                        <tbody id="atyRecordBody">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>



</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>

</div>

<script>

    $(function(){
        var str = '<%=list%>';
        var atyStr='<%=activity%>';

        var data = JSON.parse(str);
        var aty=JSON.parse(atyStr);


        if(aty){
            $("#aty_start_time").html(aty.startTime);
            $("#aty_end_time").html(aty.endTime);
            $("#aty_date").html(aty.date);
            $("#aty_site_num").html(aty.siteNum);
            $("#aty_charge_member_name").html(aty.chargeMember.qqName);
            $("#aty_charge_member_phone").html(aty.chargeMember.phone);
            $("#aty_time_num").html(aty.timeNum);
            $("#aty_total_person").html(aty.totalPerson);
            //alert(aty.atyStatus);
            $("#atyRecordBody").html("");
            if(aty.atyStatus=="01"){
                $("#jieSuanBtn").show();
                $("#atyHead2").hide();
                $("#atyHead1").show();
                $("#div_bad_num").hide();
                $("#div_total_cost").hide();
                $("#div_avg_cost").hide();
                if (data && data.length > 0) {
                    $.each(data, function (index, obj) {
                        $("#atyRecordBody").append("<tr  ><td>"+obj.memberName+"</td><td>"+obj.friendNum+"</td><td>"+obj.friendNames+"</td><td>"+obj.baomingTime+"</td><td width='120px;'  >"+obj.isCancle+"</td></tr>");
                    });
                }

            }else if(aty.atyStatus=="03"){
                $("#jieSuanBtn").hide();
                $("#div_bad_num").show();
                $("#div_total_cost").show();
                $("#div_avg_cost").show();
                $("#aty_bad_num").html(aty.badmintonNum);
                $("#aty_total_cost").html(aty.totalCost);
                $("#aty_avg_cost").html(aty.avgCost);
                $("#atyHead1").hide();
                $("#atyHead2").show();

                if (data && data.length > 0) {

                    $.each(data, function (index, obj) {
                        if(obj.currentDayLeft<0){
                            $("#atyRecordBody").append("<tr style='background-color:#d43f3a;color: #fff;' ><td>"+obj.qqName+"</td><td>"+obj.friendNum+"</td><td>"+obj.atyAvgMoney+"</td><td>"+obj.currentDayCost+"</td><td width='120px;'  >"+obj.currentDayLeft+"</td></tr>");
                        }else{
                            $("#atyRecordBody").append("<tr  ><td>"+obj.qqName+"</td><td>"+obj.friendNum+"</td><td>"+obj.atyAvgMoney+"</td><td>"+obj.currentDayCost+"</td><td width='120px;'  >"+obj.currentDayLeft+"</td></tr>");
                        }

                    });
                }
            }

        }

    });


    function finishAty(){
        var atyId='<%=atyId%>';
        //alert(atyId);
        var siteNum=$("#aty_siteNum").val();
        var badmintonNum=$("#aty_badmintonNum").val();
        var timeNum=$("#aty_timeNum").val();
        $.post(parent._context+"/finishAty.do",{atyId:atyId,siteNum:siteNum,timeNum:timeNum,badmintonNum:badmintonNum},function (msg) {
            if(msg.msg=="success"){
                alert("活动结算成功");
                //$("#mydialog").modal("hide");
                reloadMemberRecord(msg.data);
                reloadAtyInfo(msg.atyBean);
            }
        })

    }

    function  reloadMemberRecord(data){
        $("#atyHead1").hide();
        $("#atyHead2").show();
        $("#atyRecordBody").html("");
        if (data && data.length > 0) {
            $.each(data, function (index, obj) {
                if(obj.currentDayLeft<0){
                    $("#atyRecordBody").append("<tr style='background-color:#d43f3a;color: #fff;' ><td>"+obj.qqName+"</td><td>"+obj.friendNum+"</td><td>"+obj.atyAvgMoney+"</td><td>"+obj.currentDayCost+"</td><td width='120px;'  >"+obj.currentDayLeft+"</td></tr>");
                }else{
                    $("#atyRecordBody").append("<tr  ><td>"+obj.qqName+"</td><td>"+obj.friendNum+"</td><td>"+obj.atyAvgMoney+"</td><td>"+obj.currentDayCost+"</td><td width='120px;'  >"+obj.currentDayLeft+"</td></tr>");
                }

            });
        }
    }

    function reloadAtyInfo(atyBean){
        $("#jieSuanBtn").hide();
        $("#div_bad_num").show();
        $("#div_total_cost").show();
        $("#div_avg_cost").show();
        $("#aty_bad_num").html(atyBean.badmintonNum);
        $("#aty_total_cost").html(atyBean.totalCost);
        $("#aty_avg_cost").html(atyBean.avgCost);
    }


</script>
</body>
</html>
