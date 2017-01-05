<%--
  Created by IntelliJ IDEA.
  User: wanghuidong
  Date: 2016/12/21
  Time: 下午4:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会员登录</title>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span>
    </button>
    <h4 class="modal-title" id="exampleModalLabel">会员登录</h4>
</div>

<div class="modal-body" style="height:300px;">
    <form class="form-horizontal">
        <div class="box-body">
        <div class="form-group">
            <label for="phone" class="col-sm-2 control-label">账号:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="phone" value="" placeholder="手机号"/>
            </div>
        </div>
        <div class="form-group">
            <label for="pwd" class="col-sm-2 control-label">密码:</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="pwd" value=""  placeholder="密码"/>
            </div>
        </div>
        </div>
    </form>

</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button type="button" class="btn btn-primary" onclick="login()">登录</button>
</div>

<script>
    function login() {
        var phone=$("#phone").val();
        var pwd=$("#pwd").val();
        if(phone=="" || phone==null){
            alert("手机号不能为空!");
            return false;
        }

        if(pwd=="" || pwd==null){
            alert("密码不能为空!")
            return false;
        }
        $.post(parent._context+"/memberLogin.do",{phone:phone,pwd:pwd},function (msg) {
            if(msg.msg=="success"){
                alert("登录成功!");
                $("#loginDialog").modal("hide");
                parent.setMemberId(msg.memberId);
                parent.setMemberName(msg.memberName);
               // alert(memberName);
                parent.goBaoMing();
            }else{
                alert("用户名密码不正确!");
            }

        })
    }

</script>
</body>
</html>
