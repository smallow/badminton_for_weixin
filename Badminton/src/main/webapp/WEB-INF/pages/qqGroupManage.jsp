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
    String path = request.getContextPath();
    String loginCode=(String)session.getAttribute("admin_login_code");
    System.out.println(loginCode);
    if(loginCode==null){
        response.sendRedirect("/toAdminLogin.do");
    }
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
                <div class="col-sm-3 form-group">
                    <select id="group" class="form-control" onchange="getMember()">

                    </select>
                </div>
                <div class="col-sm-3 form-group">
                    <input type="text" class="form-control" placeholder="qq昵称" id="query_qq_name">
                </div>
                <div class="col-sm-3 form-group">
                    <input type="text" class="form-control" placeholder="手机号" id="query_phone">
                </div>
                <div class="col-sm-3">
                    <%--<button class="btn btn-default" onclick="search()">搜索</button>--%>
                    <div class="form-group input-group">
                        <input type="text" class="form-control" placeholder="qq号" id="query_qq_num">
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


            <button type="button" class="btn btn-default btn-success" onclick="addMember()">添加会员
            </button>
            <button type="button" class="btn btn-default btn-info" onclick="editMember()">修改会员
            </button>
            <button type="button" class="btn btn-default btn-danger" onclick="delMember()">删除会员
            </button>
            <button type="button" class="btn btn-default btn-warning" onclick="queryAtyRecord()">查看消费详情
            </button>
            <div class="modal fade" id="addMemberDialog" role="dialog"  >
                <div class="modal-dialog" style="width: 700px;">
                    <div class="modal-content">

                    </div>
                </div>
            </div>

            <div style="height: 50px;">&nbsp;</div>


        </div>
    </div>
</div>

<script>
    var _context = '<%=path%>';
    $(function () {
        var str = '<%=listJson%>';
        var data = JSON.parse(str);
        // alert(data.length);
        if (data && data.length > 0) {
            $.each(data, function (index, obj) {
                $("#group").append("<option value='" + obj.qqNumber + "'>" + obj.name + "</option>");
            });

            getMember();
        }
        $("#addMemberDialog").on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });
    });

    function getMember() {
        //jQuery("#group").empty();
        $.post(_context + "/getMemberByGroupNum.do", {qqNum: $("#group").val()}, function (data) {
            $("#members").html("");
            if (data.length > 0) {
                $.each(data, function (index, row) {
                    //var spinnerStr=$("<div class='input-group spinner' data-trigger='spinner'><input type='text' class='form-control text-center' value='0' id='spinner_"+row.id+"' data-rule='quantity'><span class='input-group-addon'><a href='javascript:;' class='spin-up' data-spin='up'><i class='fa fa-caret-up'></i></a><a href='javascript:;' class='spin-down' data-spin='down'><i class='fa fa-caret-down'></i></a></span></div>");
                    $("#members").append("<tr><td><input name=\"memberIds\" type=\"checkbox\" value=\"" + row.id + "\"/></td><td>" + row.qqName + "</td><td>" + row.qqNum + "</td><td>" + row.money + "</td><td>" + row.qqGroupName + "</td><td width='120px;' >" + row.phone + "</td></tr>");
                })
            }
        }, 'json');
    }

    function search() {
        $.post(_context + "/searchMember.do", {
            qqGroupNum: $("#group").val(),
            qqNum:$("#query_qq_num").val(),
            qqName:$("#query_qq_name").val(),
            phone:$("#query_phone").val()

        }, function (data) {
            $("#members").html("");
            if (data.length > 0) {
                $.each(data, function (index, row) {
                    //var spinnerStr=$("<div class='input-group spinner' data-trigger='spinner'><input type='text' class='form-control text-center' value='0' id='spinner_"+row.id+"' data-rule='quantity'><span class='input-group-addon'><a href='javascript:;' class='spin-up' data-spin='up'><i class='fa fa-caret-up'></i></a><a href='javascript:;' class='spin-down' data-spin='down'><i class='fa fa-caret-down'></i></a></span></div>");
                    $("#members").append("<tr><td><input name=\"memberIds\" type=\"checkbox\" value=\"" + row.id + "\"/></td><td>" + row.qqName + "</td><td>" + row.qqNum + "</td><td>" + row.money + "</td><td>" + row.qqGroupName + "</td><td width='120px;' >" + row.phone + "</td></tr>");
                })
            }
        }, 'json');
    }

    function addMember() {
        $("#addMemberDialog").modal({
            remote: _context+"/addMember.do"
        });
    }
    function editMember() {
        var length=$("input[name='memberIds']:checked").length;
        if(length!=1){
            alert("请选择一条信息进行修改");
            return false;
        }

        var checkedMemberId=$("input[name='memberIds']:checked").val();
        //alert(checkedMemberId)
        $("#addMemberDialog").modal({
            remote: _context+"/editMember.do?id="+checkedMemberId
        });

    }
    function delMember() {
        var length=$("input[name='memberIds']:checked").length;
        if(length<1){
            alert("请选择至少一条信息进行删除");
            return false;
        }


        var ids=[];
        $('input[name="memberIds"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数
            ids.push($(this).val());
        });

        $.post(_context + "/delMember.do",{ids:ids.toString()},function(msg){
            if(msg.msg=="success"){
                alert("会员信息删除成功!");
                getMember();
            }
        })

    }

    function queryAtyRecord() {
        var length=$("input[name='memberIds']:checked").length;
        if(length!=1){
            alert("请选择一条信息进行查看");
            return false;
        }
        var checkedMemberId=$("input[name='memberIds']:checked").val();
        $("#addMemberDialog").modal({
            remote: _context+"/queryAtyRecord.do?memberId="+checkedMemberId
        });
    }
</script>
</body>
</html>
