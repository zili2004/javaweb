<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>登陆 - 学生成绩管理系统</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/particle-bg.umd.min.js"></script>

<style type="text/css">
html, body {
      margin: 0;
      padding: 0;
      width: 100%;
      height: 100%;
      background-image: linear-gradient(to bottom, #243949 0%, #517fa4 100%);
      overflow: hidden;
    }
canvas{
    position: absolute;
    top: 0;
    z-index: -1;
}
.div_body {
    height: 400px;
    width: 30%;
    margin: 10% auto;
    position: relative;
    background: #fff;
    border-radius: 6px;
    z-index: 2;
    padding-bottom: 20px;
}
#_body {
    width: 100%;
    text-align: center;
}
.msg{
    padding-top: 20px;
    color: red; /* 错误提示建议用红色醒目一些 */
    height: 40px;
    font-size: 16px;
}
.sys-title {
    font-size: 24px;
    color: #517fa4;
    margin-bottom: 20px;
    border-bottom: 2px solid #517fa4;
    padding-bottom: 10px;
}
table{
    margin: 0 auto;
    width: 80%;
}
table tr{
    height: 50px;
}
.input{
    width: 100%;
    height: 40px;
    border-radius: 10px;
    text-align: center;
    font-size: 15px;
    border:1px solid #ccc;
    color: #333; /* 改为深色，否则打字看不清 */
}
.btn {
    color: #ffffff;
    background-color: #40586d;
    border-color: #374b5d;
    width: 25%;
    margin: 0 2%;
    cursor: pointer;
}
.btn:hover {
    background-color: #517fa4;
}
.check{
    margin: 0 5px 0 15px;
}
</style>

<script type="text/javascript">
// 1. 初始化背景粒子
window.onload = function() {
    particleBg('body', {
    color: 'rgba(255, 255, 255, 0.5)'
  });
}

// 2. 修复：补充缺失的表单提交校验函数
function checkedForm(form) {
    if(form.ID.value.trim() === "") {
        alert("请输入账号！");
        form.ID.focus();
        return false;
    }
    if(form.pwd.value.trim() === "") {
        alert("请输入密码！");
        form.pwd.focus();
        return false;
    }
    return true;
}
</script>
</head>

<body>
    <div class="div_body">
        <div id="_body">
            <h3 class="msg" style="cursor:default">${msg}</h3>
            
            <div class="sys-title">学生成绩管理系统</div>
            
            <form action="ServletLogin" method="post" onsubmit="return checkedForm(this)">
                <table>
                    <tr>
                        <td><input class="input" type="text" name="ID" placeholder="请输入账号"></td>
                    </tr>
                    <tr>
                        <td><input class="input" type="password" name="pwd" placeholder="请输入密码"></td>
                    </tr>
                    <tr>
                        <td style="text-align: center; padding: 10px 0;">
                            <input class="check" type="radio" name="role" value="0" checked="checked">学生
                            <input class="check" type="radio" name="role" value="1">教师
                            <input class="check" type="radio" name="role" value="2">管理员
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: center; padding-top: 15px;">
                            <input class="btn" type="submit" value="登录">
                            <input class="btn" type="button" value="注册" onclick="window.location.href='register/register.jsp'">
                            <input class="btn" type="reset" value="重置">
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</body>
</html>