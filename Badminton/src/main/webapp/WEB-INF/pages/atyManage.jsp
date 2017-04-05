<%--
  Created by IntelliJ IDEA.
  User: wanghuidong
  Date: 2016/12/28
  Time: 下午3:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    String loginCode=(String)session.getAttribute("admin_login_code");
    //System.out.println(loginCode);
    if(loginCode==null){
        response.sendRedirect("/toAdminLogin.do");
    }
String path=request.getContextPath();
String qqGroupId=request.getParameter("qqGroupId");
%>
<html>
<head>
    <title>群活动管理</title>
    <jsp:include page="../layout/head.jsp"></jsp:include>

</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <h3><i class="glyphicon glyphicon-dashboard"></i>活动管理</h3>
            <div class="row">
                <div style="float: right;">
                    <button type="button" class="btn btn-default btn-success" onclick="addAtyRecord()">添加活动记录
                    </button>
                </div>
            </div>

            <hr>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>
                        选择
                    </th>
                    <th>
                        时间
                    </th>
                    <th>
                        活动地点
                    </th>
                    <th>
                        开始时间
                    </th>
                    <th>
                        结束时间
                    </th>
                    <th>
                        场地数
                    </th>

                    <th>
                        负责人
                    </th>

                    <th>
                        操作
                    </th>

                </tr>
                </thead>
                <tbody id="activitys">


                </tbody>

            </table>
            <ul class="pagination" id="fenye">

            </ul>

            <div class="modal fade" id="mydialog" role="dialog"  >
                <div class="modal-dialog" style="width: 90%;">
                    <div class="modal-content">

                    </div>
                </div>
            </div>

            <div style="height: 50px;">&nbsp;</div>
        </div>
    </div>
</div>

<script>
    var _context='<%=path%>';
$(function(){
    initPagination();
    query(1);
    $("#mydialog").on("hidden.bs.modal", function() {
        $(this).removeData("bs.modal");
    });
})

    function initPagination() {
        for(var i=0;i<10;i++){
            var a=i+1;
            $("#fenye").append("<li><a href=\"javascript:void(0);\" onclick=\"query('"+(i+1)+"')\">"+a+"</a></li>");
        }
    }

    function query(pageNum) {

        var qqGroupNum='<%=qqGroupId%>';
        //alert(qqGroupId);
        $.post(_context+"/queryAtyByQQGroupId.do",{qqGroupNum:qqGroupNum,pageNum:pageNum},function(data){
            $("#activitys").html("");
            if (data.length > 0) {
                $.each(data, function (index, row) {
                    //var spinnerStr=$("<div class='input-group spinner' data-trigger='spinner'><input type='text' class='form-control text-center' value='0' id='spinner_"+row.id+"' data-rule='quantity'><span class='input-group-addon'><a href='javascript:;' class='spin-up' data-spin='up'><i class='fa fa-caret-up'></i></a><a href='javascript:;' class='spin-down' data-spin='down'><i class='fa fa-caret-down'></i></a></span></div>");
                    $("#activitys").append("<tr><td><input name=\"atyId\" type=\"checkbox\" value=\"" + row.id + "\"/></td><td>" + row.date + "</td><td>" + row.address + "</td><td>" + row.startTime + "</td><td>" + row.endTime + "</td><td width='50px;' >" + row.siteNum + "</td><td>"+row.chargeMember.qqName+"</td><td><a  href='javascript:void(0);' onclick=\"queryAtyDetail('"+row.id+"')\">查看详情</a></td></tr>");
                })
            }
        },'json');
    }

    function queryAtyDetail(id) {
        $("#mydialog").modal({
            remote: _context+"/queryAtyDetail.do?atyId="+id
        });
    }

    function addAtyRecord(){
        var length=$("input[name='atyId']:checked").length;
        if(length!=1){
            alert("请选择一条信息进行活动记录添加");
            return false;
        }

        var atyId=$("input[name='atyId']:checked").val();
        //alert(atyId);
        window.location.href=_context+"/addActivityRecord.do?activityId="+atyId;
    }

</script>
</body>
</html>
