<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SSM框架后台管理员登录</title>
    <meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
    <meta name="author" content="Vincent Garreau">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<body>

<div id="particles-js">
    <div class="login" style="display: block;">
        <div class="login-top">
            登录
        </div>
        <div class="login-center-input-text">用户名</div>
        <input type="text" name="username" id="username" value="" placeholder="请输入您的用户名">
        <div class="login-center-input-text">密码</div>
        <input type="password" name="password" id="password" value="" placeholder="请输入您的密码">
        <br/>
        <button id="btn">登录</button>
        <button id="rigister">注册</button>
    </div>
    <script src="../resources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript">
        document.querySelector("#rigister").onclick = function () {
            window.location.href = "rigisters";
        }
        document.querySelector("#btn").onclick = function () {
            var username = $("#username").val();
            var password = $("#password").val();
            if(isEmpty(username)){
                alert('没有填写用户名');
                return;
            }
            if(isEmpty(password)){
                alert('没有填写密码');
                return;
            }
            $.ajax({
                url: 'login',
                data: {username: username, password: password},
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    if (data.type === 'success') {
                        //登录成功
                        alert(data.msg);
                        setTimeout(function () {
                            window.location.href = "main";
                        },1000);
                    } else if (data.type === 'pwdError') {
                        //密码错误
                        alert(data.msg);
                    } else if (data.type === 'error') {
                        //密码错误
                        alert(data.msg);
                    }else if(data.type === 'noSearch'){
                        var truthBeTold = window.confirm("您未注册，点击确定跳转注册页面！");
                        if (truthBeTold) {
                            window.location.href = "rigisters";
                        }
                    }
                },
                error: function(){
                    alert('系统忙，请稍后再试');
                    window.location.reload();
                }
            });
        }
        function isEmpty(s){
            if (s == null || s == "") {
                return true;
            }
            return false;
        }
    </script>
</body>
</html>
