<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: wanghuidong
  Date: 2016/12/20
  Time: 下午9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String listJson = (String) request.getAttribute("list");
    String path=request.getContextPath();

%>
<html>
<head>
    <title>会员参加的活动记录</title>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
    <h4 class="modal-title" id="exampleModalLabel">会员活动记录</h4>
</div>

<div class="modal-body" style="height:400px;">
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>
                时间
            </th>
            <th>
                qq昵称
            </th>
            <th>
                qq号
            </th>
            <th>
                带人数
            </th>
            <th>
                平均A费
            </th>
            <th>
                当天消费
            </th>

            <th>
                当天结余
            </th>



        </tr>
        </thead>
        <tbody id="records">


        </tbody>
    </table>

</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button type="button" class="btn btn-primary" onclick="saveMember()">确认</button>
</div>

<script>
    var _context = '<%=path%>';
    $(function () {
        var str = '<%=listJson%>';
        var data = JSON.parse(str);
         //alert(data.length);
        $("#records").html("");
        if (data && data.length > 0) {

                $.each(data, function (index, row) {
                    //var spinnerStr=$("<div class='input-group spinner' data-trigger='spinner'><input type='text' class='form-control text-center' value='0' id='spinner_"+row.id+"' data-rule='quantity'><span class='input-group-addon'><a href='javascript:;' class='spin-up' data-spin='up'><i class='fa fa-caret-up'></i></a><a href='javascript:;' class='spin-down' data-spin='down'><i class='fa fa-caret-down'></i></a></span></div>");
                    $("#records").append("<tr><td>" + row.atyDate + "</td><td>" + row.qqName + "</td><td>" + row.qqNum + "</td><td>"+row.friendNum+"</td><td>"+row.atyAvgMoney+"</td><td>" + row.currentDayCost + "</td><td width='120px;' >" + row.currentDayLeft + "</td></tr>");
                })



        }

    });
</script>
</body>
</html>
