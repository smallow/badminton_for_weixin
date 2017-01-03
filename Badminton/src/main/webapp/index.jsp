<%--
  Created by IntelliJ IDEA.
  User: smallow
  Date: 16/8/30
  Time: 上午10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    //String loginCode=(String)session.getAttribute("admin_login_code");
    //System.out.println(loginCode);
//    if(loginCode==null){
//        response.sendRedirect("/toAdminLogin.do");
//    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <jsp:include page="WEB-INF/layout/head.jsp"></jsp:include>
    <link href="//cdn.bootcss.com/ladda-bootstrap/0.9.4/ladda-themeless.min.css" rel="stylesheet">
    <script src="<%=path%>/static/jquery-spinner/js/jquery.spinner.min.js"></script>
    <link href="<%=path%>/static/jquery-spinner/css/bootstrap-spinner.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column" style="padding: 20px;">
                <div class="list-group">
                    <a class="list-group-item active">今日活动</a>
                    <div class="list-group-item">
                        <h4 id="aty_address" class="list-group-item-heading"></h4>
                        <p class="list-group-item-text">
                            时间:&nbsp;<span id="aty_start_time" ></span>&nbsp;到&nbsp;<span id="aty_end_time"></span>
                        </p>
                    </div>
                    <div class="list-group-item">
                        场地数:<span id="aty_site_num" class="list-group-item-text"></span>
                    </div>
                    <div class="list-group-item">
                        联系人:<span id="aty_charge_member_name" class="list-group-item-text"></span>
                    </div>
                    <div class="list-group-item">
                        联系电话:<span id="aty_charge_member_phone" class="list-group-item-text"></span>
                    </div>
                    <a class="list-group-item active"> <span class="badge">14</span> Help</a>
                </div>
            <button class="btn btn-primary ladda-button" data-style="zoom-in" onclick="test(this)"><span class="ladda-label">刷新今日活动</span></button>
            <button type="button" class="btn btn-default btn-success" onclick="baoming()">报名</button>
            <button type="button" class="btn btn-default btn-info" onclick="my()">我的</button>
            <button id="btn_publish_aty" type="button" class="btn btn-primary" onclick="add()">发布活动</button>
            <!--<button type="button" class="btn btn-default" onclick="addActivityRecord()">添加活动记录</button>-->
            <button type="button" class="btn btn-default" onclick="qqGroupManage()">群管理</button>

            <p></p>
            <div class="panel panel-default" style="">
                <div class="panel-heading">
                    今日活动报名
                </div>
                <div class="panel-body" id="todayAtyMemberList">
                    <div class="row show-grid" >
                        <div class="col-md-2">大梁</div>
                        <div class="col-md-2">大梁</div>
                        <div class="col-md-2">大梁</div>
                        <div class="col-md-2">大梁</div>
                        <div class="col-md-2">大梁</div>
                        <div class="col-md-2">大梁</div>
                    </div>
                    <div class="row show-grid" id="">
                        <div class="col-md-2">大梁</div>
                        <div class="col-md-2">大梁</div>
                        <div class="col-md-2">大梁</div>
                        <div class="col-md-2">大梁</div>
                        <div class="col-md-2">大梁</div>
                        <div class="col-md-2">大梁</div>
                    </div>
                    <!-- /.table-responsive -->
                </div>
                <!-- /.panel-body -->
            </div>



            <div class="modal fade" id="loginDialog" role="dialog"  >
                <div class="modal-dialog" style="width: 400px;">
                    <div class="modal-content">

                    </div>
                </div>
            </div>
            <div class="modal fade" id="myDialog" role="dialog"  >
                <div class="modal-dialog" style="width: 600px;">
                    <div class="modal-content">

                    </div>
                </div>
            </div>

        </div>
    </div>



</div>
<input type="hidden" id="activityId" value="" />
<input type="hidden" id="memberId" value="" />
<script>
    var _context='<%=path%>';

    $(function(){
        getTodayAty();

    });




    function baoming(){
        var atyId=$("#activityId").val();
        if(atyId==""){
            alert("今日活动还没发布请不要着急!");
            return false;
        }

        $.post(_context+"/checkLogin.do",{},function(msg){
            if(!msg.memberId){

                $("#loginDialog").modal({
                    remote: _context+"/openLogin.do"
                });
            }else{
                $("#memberId").val(msg.memberId);
                goBaoMing();
            }
        },'json');
    }


    function goBaoMing(){

        $.post(_context+"/baoming.do",{memberId:$("#memberId").val(),atyId:$("#activityId").val()},function (msg) {
            if(msg.msg=="success"){
                alert("报名成功!");
            }
        })
    }

    function setMemberId(memberId){
        if(memberId!=null && memberId!=undefined){
            $("#memberId").val(memberId);
        }

    }

    function my(){

    }



    function getTodayAty(){
        $.post(_context+"/getTodaysAty.do",{}, function (data) {
            if(data.msg && data.msg=="none"){
                alert("今天活动还没发布请不要着急!");
            }else{
                //$("#btn_publish_aty").attr("class","btn btn-primary disabled");


                var startDay1=new Date(parseInt(data.startTime));
                var endDay1=new Date(parseInt(data.endTime));
                // alert(startDay1.getHours());
                //alert(endDay1.getHours());
                $("#aty_address").html(data.address);
                $("#aty_site_num").html(data.siteNum);
                $("#aty_start_time").html(startDay1.Format("yyyy-MM-dd hh:mm"));
                $("#aty_end_time").html(endDay1.Format("yyyy-MM-dd hh:mm"));
                $("#aty_charge_member_name").html(data.chargeMember.qqName);
                $("#aty_charge_member_phone").html(data.chargeMember.phone);
                $("#activityId").val(data.id);
            }


        },'json');
    }

    function test(obj){

        var l = Ladda.create(obj);
        l.start();
        $.post(_context+"/getTodaysAty.do",{}, function (data) {
            l.stop();
            if(data.msg && data.msg=="none"){
                alert("今天活动还没发布请不要着急!");
            }else{
                $("#btn_publish_aty").attr("class","btn btn-primary disabled");
                var startDay1=new Date(parseInt(data.startTime));
                var endDay1=new Date(parseInt(data.endTime));
                // alert(startDay1.getHours());
                //alert(endDay1.getHours());
                $("#aty_address").html(data.address);
                $("#aty_site_num").html(data.siteNum);
                $("#aty_start_time").html(startDay1.Format("yyyy-MM-dd hh:mm"));
                $("#aty_end_time").html(endDay1.Format("yyyy-MM-dd hh:mm"));
                $("#aty_charge_member_name").html(data.chargeMember.qqName);
                $("#aty_charge_member_phone").html(data.chargeMember.phone);
                $("#activityId").val(data.id);
            }


        },'json');
    }

    function add(){
        var _context='<%=path%>';


        $("#myDialog").modal({
            remote: _context+"/openAddAty.do"
        });
    }

    /*function addActivityRecord(){

        var activityId=$("#activityId").val();
        if(activityId=="" || activityId==null){
            alert("今天还没有发布活动!");
            return false;
        }
        window.location.href=_context+"/addActivityRecord.do?activityId="+activityId;
    }*/

    function qqGroupManage(){
        window.location.href=_context+"/qqGroupManage.do?";
    }


</script>






<script src="//cdn.bootcss.com/ladda-bootstrap/0.9.4/spin.min.js"></script>
<script src="//cdn.bootcss.com/ladda-bootstrap/0.9.4/ladda.min.js"></script>

</body>
</html>
