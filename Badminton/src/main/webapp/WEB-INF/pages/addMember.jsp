<%@ page import="com.smallow.badminton.enity.Member" %><%--
  Created by IntelliJ IDEA.
  User: wanghuidong
  Date: 2016/12/20
  Time: 下午12:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String listJson = (String) request.getAttribute("list");
    Member member=(Member)request.getAttribute("member");
    String qqName="";
    String qqNum="";
    String phone="";
    String money="";
    String qqGroupNum="";
    int id=0;
    if(member!=null){
        //System.out.println("Member:"+ member.getQqName());
        id=member.getId().intValue();
        qqName=member.getQqName();
        qqNum=member.getQqNum();
        phone=member.getPhone();
        money=String.valueOf(member.getMoney());
        qqGroupNum=member.getQqGroupNum();
    }

%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
    <h4 class="modal-title" id="exampleModalLabel"><%=member==null?"添加会员信息":"修改会员信息"%></h4>
</div>

<div class="modal-body" style="height:400px;">
    <form class="form-horizontal">
        <input type="hidden" id="memberId" value="<%=id%>">
        <div class="box-body">
            <div class="form-group">
                <label for="qqName" class="col-sm-2 control-label">qq昵称</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="qqName" value="<%=qqName%>" placeholder="qq昵称">
                </div>
            </div>
            <div class="form-group">
                <label for="qqNum" class="col-sm-2 control-label">qq号</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" id="qqNum" value="<%=qqNum%>" placeholder="qq号">
                </div>
            </div>
            <div class="form-group">
                <label for="phone" class="col-sm-2 control-label">手机号</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="phone" value="<%=phone%>" placeholder="手机号">
                </div>
            </div>
            <div class="form-group">
                <label for="phone" class="col-sm-2 control-label">费用</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" value="<%=money%>" id="money" >
                </div>
            </div>
            <div class="form-group">
                <label for="add_member_group" class="col-sm-2 control-label">所属群</label>
                <div class="col-sm-10">
                    <select id="add_member_group" class="form-control">

                    </select>
                </div>
            </div>
        </div>

    </form>

</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button type="button" class="btn btn-primary" onclick="saveMember()">确认</button>
</div>

<script>

    $(function () {
        var str = '<%=listJson%>';
        var qqGroupNum='<%=qqGroupNum%>';
        var data = JSON.parse(str);
         //alert(data.length);
        if (data && data.length > 0) {
            $.each(data, function (index, obj) {
                //alert(obj.qqNumber);
                if(qqGroupNum!=""){
                    if(obj.qqNumber==qqGroupNum){
                        $("#add_member_group").append("<option value='" + obj.qqNumber + "' selected>" + obj.name + "</option>");
                    }else{
                        $("#add_member_group").append("<option value='" + obj.qqNumber + "'>" + obj.name + "</option>");
                    }
                }else{
                    $("#add_member_group").append("<option value='" + obj.qqNumber + "'>" + obj.name + "</option>");
                }

            });
        }


    });

    function saveMember() {
       // alert("dd");
        var qqName=$("#qqName").val();
        var qqNum=$("#qqNum").val();
        var phone=$("#phone").val();
        var money=$("#money").val();
        var qqQroupNum=$("#add_member_group option:selected").val();

        var qqGroupName=$("#add_member_group option:selected").text();

        if(!checkNull(qqName)){
            alert("QQ名字不能为空");
            return false;
        }
        if(!checkNull(qqNum)){
            alert("QQ号不能为空");
            return false;
        }
        if(!checkNull(phone)){
            alert("手机号不能为空");
            return false;
        }

        if(!checkNull(money)){
            alert("初始会费不能为空");
            return false;
        }

        var id='<%=id%>';

        var param={
            qqName:qqName,
            qqNum:qqNum,
            phone:phone,
            money:money,
            id:id,
            qqGroupNum:qqQroupNum,
            qqGroupName:qqGroupName
        }

        $.post(parent._context+"/saveMember.do",param,function (msg) {
            if(msg.msg=="success"){
                if(id!="0"){
                    alert("用户信息修改成功");
                }else{
                    alert("用户添加成功");
                }

                $("#addMemberDialog").modal("hide");
                parent.getMember();
            }else{
                alert('fail');
            }
        })
    }

    function checkNull(str){
        if(str=="" || str=="null"  || str==null || str==undefined){
            return false;
        }
        return true;
    }
</script>
</body>
</html>
