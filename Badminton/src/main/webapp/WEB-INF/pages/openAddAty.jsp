<%--
  Created by IntelliJ IDEA.
  User: wanghuidong
  Date: 2016/12/28
  Time: 下午1:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加活动</title>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span>
    </button>
    <h4 class="modal-title" id="exampleModalLabel">添加活动</h4>
</div>

<div class="modal-body" style="height:500px;">
    <form class="form-horizontal">
        <div class="box-body">
            <div class="form-group">
                <label for="address" class="col-sm-2 control-label">活动地点:</label>
                <div class="col-sm-10">
                    <select id="address">
                        <option value="qianyu" selected>沙口路千羽</option>
                        <option value="gongye" >园田路河南工业设计学校</option>
                        <option value="ligong" >茂花路河南理工学校</option>
                        <option value="caiyuan" >文化路财院</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="startTime" class="col-sm-2 control-label">开始时间:</label>
                <div class="col-sm-10">
                    <div class="input-group date form_datetime col-md-8"   data-date-format="yyyy-mm-dd hh:ii" >
                        <input class="form-control" size="16" type="text" id="startTime" value="" readonly>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="endTime" class="col-sm-2 control-label">结束时间:</label>
                <div class="col-sm-10">
                    <div class="input-group date form_datetime col-md-8"  data-date-format="yyyy-mm-dd hh:ii" >
                        <input class="form-control" size="16" type="text" id="endTime" value="" readonly>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="siteNum" class="col-sm-2 control-label">场地数:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="siteNum" value="" placeholder="场地数"/>
                </div>
            </div>
            <div class="form-group">
                <label for="chargePersonCode" class="col-sm-2 control-label">负责人:</label>
                <div class="col-sm-10">
                    <select id="chargePersonCode">
                        <option value="7" selected>桃乐比</option>
                        <option value="8">小彬</option>
                        <option value="9">星星</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="phone" class="col-sm-2 control-label">联系电话:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="phone" value="" placeholder="联系方式"/>
                </div>
            </div>
        </div>
    </form>

</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button type="button" class="btn btn-primary" onclick="publish()">确认发布</button>
</div>
<script>
    $(function(){
        $('#startTime').datetimepicker({
            language:  'zh-CN',
            autoclose:true,
            todayBtn:true

        });
        $('#endTime').datetimepicker({
            language:  'zh-CN',
            autoclose:true,
            todayBtn:true

        });
    })


    function publish(){
        var address=$("#address").find("option:selected").text();
        var siteNum=$("#siteNum").val();
        var phone=$("#phone").val();
        var charegePersonCode=$("#chargePersonCode").val();
        var charegePersonName=$("#chargePersonCode").find("option:selected").text();
        var startTime=$("#startTime").val();
        var endTime=$("#endTime").val();

       // alert(charegePersonName +"\n"+startTime+"\n"+endTime);
        $.post(parent._context+"/publishAty.do",{
            address:address,
            siteNum:siteNum,
            phone:phone,
            chargePersonCode:charegePersonCode,
            chargePersonName:charegePersonName,
            startTime:startTime,
            endTime:endTime

        },function(msg){
            if(msg.msg=="success"){
                alert("活动发布成功!");
                parent.getTodayAty();
            }
        },'json');
    }
</script>

</body>
</html>
