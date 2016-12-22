<%--
  Created by IntelliJ IDEA.
  User: smallow
  Date: 16/9/28
  Time: 下午9:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String loginCode=(String)session.getAttribute("admin_login_code");
    System.out.println(loginCode);
    if(loginCode==null){
        response.sendRedirect("/toAdminLogin.do");
    }
    String listJson=(String)request.getAttribute("list");
    String path=request.getContextPath();
    String activityId=(String)request.getAttribute("activityId");

%>
<!DOCTYPE html>
<head>
    <title>添加活动记录</title>
    <jsp:include page="../layout/head.jsp"></jsp:include>
    <script src="<%=path%>/static/jquery-spinner/js/jquery.spinner.min.js"></script>
    <link href="<%=path%>/static/jquery-spinner/css/bootstrap-spinner.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <h3><i class="glyphicon glyphicon-dashboard"></i> 群管理后台</h3>
            <hr>
            <div class="row">
                <div class="col-sm-6 form-group">
                    <select id="group" class="form-control">

                    </select>
                </div>

                <div class="col-sm-6" >
                    <%--<button class="btn btn-default" onclick="search()">搜索</button>--%>
                    <div class="form-group input-group">
                        <input type="text" class="form-control" placeholder="请输入qq号或者昵称查找">
                        <%--<span class="input-group-addon">搜索</span>--%>
                         <span class="input-group-btn">
                               <button class="btn btn-default" type="button" onclick="search()"><i class="fa fa-search"></i>
                               </button>
                            </span>
                    </div>

                </div>
            </div>

            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>
                        选择
                    </th>
                    <th>
                        qq昵称
                    </th>
                    <th>
                        qq号
                    </th>
                    <th>
                        余额
                    </th>
                    <th>
                        所属群
                    </th>
                    <th>
                        带人数
                    </th>
                </tr>
                </thead>
                <tbody id="members">


                </tbody>
            </table>
            <div class="row">
                <div class="col-sm-3" >
                    <%--<button class="btn btn-default" onclick="search()">搜索</button>--%>
                    <div class="form-group">
                        <input type="text" id="siteNum" class="form-control" placeholder="场地数">
                    </div>

                </div>
                <div class="col-sm-3" >
                    <div class="form-group">
                        <input type="text" id="badmintonNum"  class="form-control" placeholder="用球数">
                    </div>
                </div>
                <div class="col-sm-3" >
                    <div class="form-group">
                        <input type="text" id="timeNum" class="form-control" placeholder="时间数">
                    </div>
                </div>
                <div class="col-sm-3" >
                    <div class="form-group">
                        <button class="btn btn-primary" onclick="addActivityRecordSubmit()">提交活动记录</button>
                    </div>
                </div>

            </div>




            <div class="panel panel-default" id="atyRecord" style="display: none;">
                <div class="panel-heading">
                    活动记录
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>名称</th>
                                <th>带人数</th>
                                <th>活动人均A费</th>
                                <th>总消费</th>
                                <th>余额</th>
                            </tr>
                            </thead>
                            <tbody id="atyRecordBody">

                            </tbody>
                        </table>
                    </div>
                    <!-- /.table-responsive -->
                </div>
                <!-- /.panel-body -->
            </div>


        </div>
    </div>
</div>

<script>
    var _context="<%=path%>";
    $(function(){
        var str='<%=listJson%>';
        var data=JSON.parse(str);
        // alert(data.length);
        if(data && data.length>0){
            $.each(data,function(index,obj){
                $("#group").append("<option value='"+obj.qqNumber+"'>"+obj.name+"</option>");
            });

            $.post(_context+"/getMemberByGroupNum.do",{qqNum:$("#group").val()},function(data){
                //alert(data.length);
                if(data.length>0){
                    $.each(data, function (index, row) {
                        var spinnerStr=$("<div class='input-group spinner' data-trigger='spinner'><input type='text' class='form-control text-center' value='0' id='spinner_"+row.id+"' data-rule='quantity'><span class='input-group-addon'><a href='javascript:;' class='spin-up' data-spin='up'><i class='fa fa-caret-up'></i></a><a href='javascript:;' class='spin-down' data-spin='down'><i class='fa fa-caret-down'></i></a></span></div>");

                        $("#members").append("<tr><td><input name=\"memberIds\" type=\"checkbox\" value=\""+row.id+"\"/></td><td>"+row.qqName+"</td><td>"+row.qqNum+"</td><td>"+row.money+"</td><td>"+row.qqGroupName+"</td><td width='120px;' id='"+row.id+"' ></td></tr>");
                       //alert(spinnerStr);
                        $("#"+row.id).append(spinnerStr);
                        $('.spinner').spinner();
                    })
                }
            },'json');
        }


    });
    function search(){

    }

    function addActivityRecordSubmit(){
        var activityId='<%=activityId%>';
        var list=$("input:checkbox[name='memberIds']:checked");
        var ids="";
        var friendNums="";
        $.each(list,function(index,obj){
            ids+=$(obj).val()+",";
            friendNums+=$("#spinner_"+$(obj).val()).val()+",";
        });
        var reg=/,$/;
        ids=ids.replace(reg,"");
        friendNums=friendNums.replace(reg,"");
//        alert(friendNums);
//        return false;
        var siteNum=$("#siteNum").val();
        var badmintonNum=$("#badmintonNum").val();
        var timeNum=$("#timeNum").val();
        $.post(_context+"/addActivityRecordSubmit.do",{
            activityId:activityId,
            memberIds:ids,
            siteNum:siteNum,
            timeNum:timeNum,
            friendNums:friendNums,
            badmintonNum:badmintonNum
        },function(data){
            if(data.msg=="success"){
                alert("提交活动记录信息成功!");
                $("#atyRecord").show();
                loadAtyRecord(data.data);
            }
        },'json');
    }

    function  loadAtyRecord(data){
        $.each(data,function(index,obj){
            $("#atyRecordBody").append("<tr><td>"+obj.qqName+"</td><td>"+obj.friendNum+"</td><td>"+obj.atyAvgMoney+"</td><td>"+obj.money+"</td><td width='120px;'  >"+obj.leftMoney+"</td></tr>");
        });
    }
</script>
</body>
</html>
