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
        <h1>
            注册
        </h1>
        <div>用户名(不能低于6位)</div>
        <input type="text" name="username" id="username" value="" placeholder="请输入您的用户名">
        <div>密码(不能低于6位)</div>
        <input type="password" name="password" id="password" value="" placeholder="请输入您的密码">
        <div>确认密码(不能低于6位)</div>
        <input type="password" name="repassword" id="repassword" value="" placeholder="请输入您的确认密码">
        <div>手机号</div>
        <input type="text" name="phone" id="phone" value="" placeholder="请输入您的手机号">
        <input type="button" name="getVc" id="getVc" value="获取验证码" placeholder="获取验证码">
        <div>验证码</div>
        <input type="text" name="Vc" id="Vc" value="" placeholder="请输入您的手机号验证码">
        <br/>
        <button id="btn">注册</button>
        <br/>
        <button id="login">回登录页面</button>
    </div>
    <script src="../resources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript">
        //跳转登录页面
        document.querySelector("#login").onclick = function () {
            window.location.href = "index";
        }
        //获取短信验证码
        document.querySelector("#getVc").onclick = function () {
            var phone = $("#phone").val();
            if(isEmpty(phone)){
                alert('没有填写手机号');
                return;
            }
            $.ajax({
                url: 'getVc',
                data: {phone:phone},
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    if(data.type === "success"){
                        alert("您的验证码是："+data.msg);
                    }else{
                        alert(data.msg);
                    }
                },
                error: function(){
                    alert('系统忙，请稍后再试');
                }
            });
        }
        //注册
        document.querySelector("#btn").onclick = function () {
            var username = $("#username").val();
            var password = $("#password").val();
            var repassword = $("#repassword").val();
            var phone = $("#phone").val();
            var Vc = $("#Vc").val();
            if(isEmpty(username)){
                alert('没有填写用户名');
                return;
            }
            if(isEmpty(password)){
                alert('没有填写密码');
                return;
            }
            if(isEmpty(repassword)){
                alert('没有填写确认密码');
                return;
            }
            if(isEmpty(phone)){
                alert('没有填写手机号');
                return;
            }
            if(isEmpty(Vc)){
                alert('没有填写验证码');
                return;
            }
            if (password !== repassword) {
                alert("密码与确认密码不一致！");
                return;
            }
            $.ajax({
                url: 'rigister',
                data: {username: username, password: password,repassword:repassword,phone:phone,Vc:Vc},
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    if(data.type === 'error'){
                        alert(data.msg);
                    }
                    if(data.type === "success") {
                        //注册成功
                        alert(data.msg);
                        setTimeout(function () {
                            window.location.href = "index";
                        }, 1000)
                    }
                },
                error: function(){
                    //$(".tm_btn_primary").text('登录');
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
