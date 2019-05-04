$(function () {
        var jugUsername = 0;
        var judEmail = 0;
        var judPassword = 0;
        $("#username").blur(function () {
            var username = $("#username").val();
            if (username) {
                $.ajax({
                    url: "http://127.0.0.1:8080/user/findUserName/" + username + "?c=" + Math.random(),
                    type: "GET",
                    dataType: "text",
                    success: function (data) {
                        if (data == "true") {
                            var msg = document.getElementById("userMsg");
                            msg.innerHTML = "";
                            jugUsername = 1;
                        } else {
                            var msg = document.getElementById("userMsg");
                            msg.style.color = "red";
                            msg.innerHTML = "用户名已被使用";
                        }
                    }
                })
            }
        })
        $("#email").blur(function () {
            var email = $("#email").val();
            var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
            if (!reg.test(email)) { //正则验证不通过，格式不对
                var msg = document.getElementById("emailMsg");
                msg.innerHTML = "邮箱格式有误";
                msg.style.color = "red";
                return;
            } else {
                var msg = document.getElementById("emailMsg");
                msg.innerHTML = "";
            }
            if (email) {
                $.ajax({
                    url: "http://127.0.0.1:8080/user/findEmail",
                    type: "POST",
                    data: {email: email},
                    success: function (data) {
                        if (data == "true") {
                            var msg = document.getElementById("emailMsg");
                            msg.innerHTML = "";
                            judEmail = 1;
                        } else {
                            var msg = document.getElementById("emailMsg");
                            msg.style.color = "red";
                            msg.innerHTML = "邮箱已被使用";
                        }
                    }
                })
            }
        })
        $("#password1,#password2").blur(function () {
            var password1 = $("#password1").val();
            var password2 = $("#password2").val();
            if (password1 && password2) {
                if (password1 === password2) {
                    var msg = document.getElementById("passwordMsg");
                    msg.innerHTML = "";
                    judPassword = 1;
                } else {
                    var msg = document.getElementById("passwordMsg");
                    msg.style.color = "red";
                    msg.innerHTML = "请确保密码输入一致";
                }
            }
        })
        $("#toSubmit").click(function () {
            if (judEmail === 1 && jugUsername === 1 && judPassword === 1) {
                var form_d = $("#form1").serialize();
                $.ajax({
                    url: "http://127.0.0.1:8080/user/toRegister",
                    data: form_d,
                    type: "POST",
                    success: function (data) {
                        if (data == "true") {
                            window.location.href = "http://127.0.0.1:8080"
                        } else {
                        }
                    }
                })
            }
        })
        $("#butLogin").click(function () {
            var loginMsg = $("#loginMsg").val();
            var password = $("#password").val();
            var $returnMsg = $("#loginReturnMsg")[0];
            if (loginMsg === "") {
                $returnMsg.innerHTML = "用户名不能为空";
                $returnMsg.style.color = "red";
            }
            else if (password === "") {
                $returnMsg.innerHTML = "密码不能为空";
                $returnMsg.style.color = "red";
            }
            else {
                $returnMsg.innerHTML = "";
                var form_dd=$("#formLogin").serialize();
                $.ajax({
                    url:"/user/toLogin",
                    data:form_dd,
                    type:"POST",
                    success:function(getData){
                        var response=eval(getData);
                        if(response.status===200){
                            window.location.href="/";
                        }else {
                            $returnMsg.innerHTML = response.msg;
                            $returnMsg.style.color="red";
                        }
                    }
                })

            }
        })
    }
);
