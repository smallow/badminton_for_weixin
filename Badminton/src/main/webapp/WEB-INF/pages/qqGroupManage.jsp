<%--
  Created by IntelliJ IDEA.
  User: smallow
  Date: 16/10/26
  Time: 下午3:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String listJson = (String) request.getAttribute("list");
    String path=request.getContextPath();
%>
<!DOCTYPE html>
<head>
    <title>群管理后台</title>
    <jsp:include page="../layout/head.jsp"></jsp:include>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <h3><i class="glyphicon glyphicon-dashboard"></i> 群管理后台</h3>
            <hr>
            <div class="row">
                <div class="col-sm-6 form-group">
                    <select id="group" class="form-control" onchange="getMember()">

                    </select>
                </div>

                <div class="col-sm-6">
                    <%--<button class="btn btn-default" onclick="search()">搜索</button>--%>
                    <div class="form-group input-group">
                        <input type="text" class="form-control" placeholder="请输入qq号或者昵称查找">
                        <%--<span class="input-group-addon">搜索</span>--%>
                         <span class="input-group-btn">
                               <button class="btn btn-default" type="button" onclick="search()"><i
                                       class="fa fa-search"></i>
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
                        手机号
                    </th>
                </tr>
                </thead>
                <tbody id="members">


                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    var _context='<%=path%>';
    $(function(){
        var str='<%=listJson%>';
        var data=JSON.parse(str);
        // alert(data.length);
        if(data && data.length>0){
            $.each(data,function(index,obj){
                $("#group").append("<option value='"+obj.qqNumber+"'>"+obj.name+"</option>");
            });

            getMember();
        }
    });

    function getMember(){
        //jQuery("#group").empty();
        $.post(_context+"/getMemberByGroupNum.do",{qqNum:$("#group").val()},function(data){
            $("#members").html("");
            if(data.length>0){
                $.each(data, function (index, row) {
                    //var spinnerStr=$("<div class='input-group spinner' data-trigger='spinner'><input type='text' class='form-control text-center' value='0' id='spinner_"+row.id+"' data-rule='quantity'><span class='input-group-addon'><a href='javascript:;' class='spin-up' data-spin='up'><i class='fa fa-caret-up'></i></a><a href='javascript:;' class='spin-down' data-spin='down'><i class='fa fa-caret-down'></i></a></span></div>");
                    $("#members").append("<tr><td><input name=\"memberIds\" type=\"checkbox\" value=\""+row.id+"\"/></td><td>"+row.qqName+"</td><td>"+row.weixinNum+"</td><td>"+row.money+"</td><td>"+row.qqGroupName+"</td><td width='120px;' >"+row.phone+"</td></tr>");



                })
            }
        },'json');
    }
</script>
</body>
</html>
