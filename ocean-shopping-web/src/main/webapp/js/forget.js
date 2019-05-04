$(function () {
    var $verifyCode=$("#verifyCode");
    $verifyCode.attr('src','/code/getCode');

    var $submitOne=$("#submitOne");
    $submitOne.click(function () {
        var username=$("#username").val();
        var code=$("#code").val();
        check(username,code);
    })

    var $submitTwo=$("#submitTwo");
    $submitTwo.click(function () {
        var verify=$("#verify").val();
        if(verify == '' || verify == undefined || verify == null){
            var $verifyMsg=$("#verifyMsg");
            $verifyMsg.text("请输入验证码");
        }else {
            var userId=$("#userId").text();
            checkVerify(verify,userId);
        }
    });

    var $submitThree=$("#submitThree");
    $submitThree.click(function () {
        var userId=$("#userId").text();
        var passwordOne=$("#passwordOne").val();
        var passwordTwo=$("#passwordTwo").val();
        if(passwordOne===passwordTwo){
            $.ajax({
                url:"/user/forgetAndUpdatePassword?userId="+userId+"&password="+passwordOne,
                method:"GET",
                success:function (resData) {
                    if(resData.status==200){
                        window.location.href="/index.html";
                    }
                }
            })
        }else{
            var $updatePassword=$("#updatePassword");
            $updatePassword.text("密码输入不一致！");
        }
    })
});
function myReload() {
    var $verifyCode=$("#verifyCode");
    $verifyCode.attr('src','/code/getCode?nocache='+new Date().getTime());
}
function check(username,code) {
    var $email=$("#email");
    $email.text("加载中......");
    var $msg=$("#msg");
    $msg.text("稍等片刻，努力加载中......");
    $.ajax({
        url:"/code/check?signcode="+code+"&username="+username,
        method:"GET",
        success:function (resData) {
            var $msg=$("#msg");
            if(resData.status==400){
                $(".two").css('display','none');
                $msg.text(resData.msg);
            }
            else{
                $(".one").css('display','none');
                $(".two").css('display','block');
                $email.text(resData.data.email);
                var $userId=$("#userId");
                $userId.text(resData.data.id);
            }

        }
    })
}
function checkVerify(verifyCode,userId) {
    $.ajax({
        url:"/code/verifyEmailCode?verifyCode="+verifyCode+"&userId="+userId,
        method:"GET",
        success:function (resData) {
            if(resData.status==200){
                $(".two").css('display','none');
                $(".three").css('display','block');
            }else{
                var $verifyMsg=$("#verifyMsg");
                $verifyMsg.text("验证码错误！");
            }
        }
    })
}