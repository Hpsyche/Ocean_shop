$(function () {
    var $regAndLoginUI = $("#regAndLoginUI");
    var cookie = getCookie("COOKIE_USER_INFO");
    $.ajax({
        url: "/user/findUserInRedis?cookie="+cookie,
        type: "GET",
        success: function (resData) {
            if (resData.status == 404) {
                $regAndLoginUI.append('<a href="javascript:login()" id="login">登录</a>');
                $regAndLoginUI.append('<a href="javascript:register()" id="reg">注册</a>');
            } else {
                var resName = resData.data.username;
                $regAndLoginUI.append('欢迎您，' + resName +'！ '+'<span id="exit"><a href="javascript:exit()" id="exit">退出登录</a></span>');
            }
        }
    })
})
function exit() {
    var cookie = getCookie("COOKIE_USER_INFO");
    $.ajax({
        url:"/user/exit/"+cookie,
        type: "GET",
        success: function (resData) {
            window.location.href="/";
        }


    })
}



