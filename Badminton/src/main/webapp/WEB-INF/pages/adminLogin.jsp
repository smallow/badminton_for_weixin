<%--
  Created by IntelliJ IDEA.
  User: wanghuidong
  Date: 2016/12/22
  Time: 上午11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理登录</title>
    <jsp:include page="../layout/head.jsp"></jsp:include>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column" style="padding: 20px;">
            <form class="form-horizontal" role="form" action="/login.do" method="post">
                <div class="form-group">
                    <label for="loginCode" class="col-sm-2 control-label">账号</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="loginCode" id="loginCode" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="pwd" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" name="pwd" id="pwd" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <div class="checkbox">
                            <label><input type="checkbox" />Remember me</label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" >Sign in</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
