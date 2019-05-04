$(function () {
    var temp=getUserInfo();
    $("#username").html("用户名："+temp.username);
    $("#username2").html(temp.username);
    $("#username3").html(temp.username);
})
