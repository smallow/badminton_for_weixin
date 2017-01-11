<%@ page import="com.smallow.badminton.vo.ActivityVo" %><%--
  Created by IntelliJ IDEA.
  User: wanghuidong
  Date: 2017/1/5
  Time: 上午9:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path=request.getContextPath();
    String memberIds=String.valueOf(request.getAttribute("memberId"));
    String atyIds=String.valueOf(request.getAttribute("atyId"));
    String memberName=(String)request.getAttribute("memberName");
    int memberId=0;
    if(memberIds!=null && !"null".equals(memberIds)){
        memberId = Integer.parseInt(memberIds);
    }
    int atyId=0;
    if(atyIds!=null && !"null".equals(atyIds)){
        atyId=Integer.parseInt(atyIds);
    }

    Object atyObject=request.getAttribute("atyJson");
    String atyJson="";
    if(atyObject!=null){
        atyJson=(String)atyObject;
    }


    Object recordObject=request.getAttribute("recordJson");
    String recordJson="";
    if(recordObject!=null){
        recordJson=(String)recordObject;
    }
   // System.out.println("recordObject:"+recordObject);

    Object personalRecordObject=request.getAttribute("personalRecordJson");
    String personalRecordJson="";
    if(personalRecordObject!=null){
        personalRecordJson=(String)personalRecordObject;
    }



%>
<html>
<head>
    <title>报名</title>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span>
    </button>
    <h4 class="modal-title" id="exampleModalLabel">报名</h4>
</div>

<div class="modal-body" style="height:500px;">
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-6 column">
                <div class="list-group"  >
                    <a class="list-group-item active">日期:&nbsp;&nbsp;<span id="baoming_aty_date"></span></a>
                    <div class="list-group-item">
                        <h4 id="aty_address" class="list-group-item-heading"></h4>
                        <p class="list-group-item-text">
                            时间:&nbsp;&nbsp;&nbsp;<span id="baoming_aty_start_time" style="color: #eb9316;"></span>&nbsp;到&nbsp;<span id="baoming_aty_end_time" style="color:#eb9316;"></span>
                        </p>
                    </div>
                    <div class="list-group-item">
                        场地数:&nbsp;&nbsp;&nbsp;<span id="baoming_aty_site_num" class="list-group-item-text" style="color: #d43f3a;font-size: 20px;"></span>&nbsp;&nbsp;(个)
                    </div>
                    <div class="list-group-item">
                        时间数:&nbsp;&nbsp;&nbsp;<span id="baoming_aty_time_num" class="list-group-item-text" style="color: #d43f3a;font-size: 20px;"></span>&nbsp;&nbsp;(小时)
                    </div>
                    <div class="list-group-item">
                        总人数:&nbsp;&nbsp;&nbsp;<span id="baoming_aty_total_person" class="list-group-item-text" style="color: #d9534f;font-size: 20px;"></span>
                    </div>

                    <div class="list-group-item">
                        联系人:&nbsp;&nbsp;&nbsp;<span id="baoming_aty_charge_member_name" class="list-group-item-text"></span>
                    </div>
                    <div class="list-group-item">
                        联系电话:&nbsp;&nbsp;&nbsp;<span id="baoming_aty_charge_member_phone" class="list-group-item-text"></span>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            报名名单
                        </h3>
                    </div>
                    <div class="panel-body" id="baomingRecord">

                    </div>

                </div>



                <div class="row">
                    <div class="col-sm-3 form-group">
                        <input type="text" class="form-control" placeholder="带人数目" id="go_baoming_friend_nums" value="" />
                    </div>
                    <div class="col-sm-3 form-group">
                        <input type="text" class="form-control" placeholder="带人名称" id="go_baoming_friend_names" value="" />
                    </div>
                </div>
                <input type="hidden" id="go_baoming_id" />
                <input type="hidden" id="go_baoming_isCancel" />

            </div>

        </div>
    </div>

</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button type="button" class="btn btn-primary" onclick="baomingSubmit()" id="submitBtn"></button>
    <button type="button" class="btn btn-danger" onclick="cancelBaomingSubmit()" id="cancelBtn" style="display: none;">取消报名</button>
</div>

<script>
    var _context='<%=path%>';
    $(function() {

        var atyStr = '<%=atyJson%>';
        var recordStr='<%=recordJson%>';
        var personalRecordJson='<%=personalRecordJson%>';

        var aty=JSON.parse(atyStr);

        if(aty){
            $("#baoming_aty_start_time").html(aty.startTime);
            $("#baoming_aty_end_time").html(aty.endTime);
            $("#baoming_aty_date").html(aty.date);
            $("#baoming_aty_site_num").html(aty.siteNum);
            $("#baoming_aty_total_person").html(aty.totalPerson);
            $("#baoming_aty_time_num").html(aty.timeNum);

            $("#baoming_aty_charge_member_name").html(aty.chargeMember.qqName);
            $("#baoming_aty_charge_member_phone").html(aty.chargeMember.phone);
        }

        if(recordStr!=""){
           // alert(recordStr);
            var recordList=JSON.parse(recordStr);
            $.each(recordList, function (index, obj) {
                if(obj.friendNum!=""){
                    $("#baomingRecord").append(obj.memberName).append("<span style='color:red;'>(+"+obj.friendNum+")</span>&nbsp;&nbsp;&nbsp;");
                }else{
                    $("#baomingRecord").append(obj.memberName).append("&nbsp;&nbsp;");
                }


            });
        }

        if(personalRecordJson!=""){
            var personalRecord=JSON.parse(personalRecordJson);
            $("#go_baoming_friend_nums").val(personalRecord.friendNum);
            $("#go_baoming_friend_names").val(personalRecord.friendName);
            $("#go_baoming_id").val(personalRecord.id);
            //alert(personalRecord.cancle);
            if(personalRecord.cancle){
                $("#submitBtn").html("确认报名");
                $("#cancelBtn").hide();
                $("#go_baoming_isCancel").val(false);
            }else{
                $("#submitBtn").html("更新报名");
                $("#cancelBtn").show();
                $("#go_baoming_isCancel").val(false);
            }

        }else{
            $("#submitBtn").html("确认报名");
            $("#cancelBtn").hide();
            $("#go_baoming_isCancel").val(false);
        }
    });

    function baomingSubmit(){
        var atyId='<%=atyId%>';
        var memberId='<%=memberId%>';
        var memberName='<%=memberName%>';
        var friendNums=$("#go_baoming_friend_nums").val();
        var friendNames=$("#go_baoming_friend_names").val();
        var isCalcel=$("#go_baoming_isCancel").val();
        var id=$("#go_baoming_id").val();
        $.post(_context+"/baomingSubmit.do",{isCalcel:isCalcel,id:id,atyId:atyId,memberId:memberId,memberName:memberName,friendNums:friendNums,friendNames:friendNames},function(msg){
            //alert(msg.msg);
            if(msg.msg=="success"){
                alert("报名成功!");
                $("#myDialog").modal("hide");
            }
        });
    }

    function cancelBaomingSubmit() {
        var id=$("#go_baoming_id").val();
        $.post(_context+"/cancelBaomingSubmit.do",{id:id},function(msg){
            if(msg.msg=="success"){
                alert("取消成功!");
                $("#myDialog").modal("hide");
            }
        });
    }
</script>
</body>
</html>
