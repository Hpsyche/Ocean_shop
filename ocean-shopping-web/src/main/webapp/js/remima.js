var flag=false;
$("#password1,#password2").blur(function () {
    var password1 = $("#password1").val();
    var password2 = $("#password2").val();
    if (password1 && password2) {
        if (password1 === password2) {
            flag=true;
            var msg = document.getElementById("passwordMsg");
            msg.innerHTML = "";
        } else {
            flag=false;
            var msg = document.getElementById("passwordMsg");
            msg.style.color = "red";
            msg.innerHTML = "请确保密码输入一致";
        }
    }
})
$("#remimaSubmit").click(function () {
    var firstPassword=$("#firstPassword");
    var password1=$("#password1");
    // var map={};
    // map['originalPassword']=firstPassword.val();
    // map['updatePassword']=password1.val();
    if(flag==true&&firstPassword.val()!=""){
        $.ajax({
            url:"/user/updatePassword",
            data:{
                "originalPassword":firstPassword.val(),
                "updatePassword":password1.val()
            },
            // JSON.stringify(map),
            method:"POST",
            success:function (resData) {
                if(resData.status==200){
                    alert("更新成功");
                }else {
                    var $oriPasswordMsg=$("#oriPasswordMsg");
                    $oriPasswordMsg.html("<span style='color: red'>原密码错误</span>");
                }
            }
        })
    }
})