$(function(){
    $("#addressFormSubmitUpdate").click(function () {
        var formData=$("#addressFormUpdate").serialize();
        $.ajax({
            url:"/address/updateAddress",
            method:"POST",
            data:formData,
            success:function (resData) {
                if(resData.status==200){
                    window.location.href="address.html";
                    // var html="";
                    // var $showAddress=$("#showAddress");
                    // html+='<div class="dizhi" style="border: #D8D8D8 1px solid"><p style="margin-top: 20px">'+resData.data.receiverName+'</p><p>'+resData.data.receiverMobile
                    //     +'</p><p>'+resData.data.receiverAddress+'</p></div>';
                    // $showAddress.append(html);
                }
            }
        })
    })
	$("#addressFormSubmit").click(function () {
		var formData=$("#addressForm").serialize();
		$.ajax({
			url:"/address/saveAddress",
			method:"POST",
			data:formData,
			success:function (resData) {
				if(resData.status==200){
                    var html="";
                    var $showAddress=$("#showAddress");
                    html+='<div class="dizhi" style="border: #D8D8D8 1px solid"><p style="margin-top: 20px">'+resData.data.receiverName+'</p><p>'+resData.data.receiverMobile
                            +'</p><p>'+resData.data.receiverAddress+'</p></div>';
                    $showAddress.append(html);
                    var $orderAddress=$("#orderAddress");
                    var html2="";
                    html2+='<div class="addre fl"><div class="tit clearfix"><p class="fl">'+resData.data.receiverName+
                        '</p><p class="fr"><a href="javascript:deleteOrderAddress(\''+resData.data.addressId+'\')">删除</a><span>|</span><a href="javascript:addOrderAddress()" class="edit">编辑</a>' +
                        '</p></div><div class="addCon"><p id="reAddress">'+resData.data.receiverAddress+'</p><p id="reMobile">'+resData.data.receiverMobile+
                        '</p></div></div>';
                    $orderAddress.append(html2);
                    $(".addressTo,.addre").click(function () {
                    	$(".addre,.addressTo").attr("class","addre fl");
                        $(this).attr("class","addre fl on");
                    });

                }
            }
		})
    })
    $("#searchContext").keyup(function(event){
        if(event.keyCode ==13){
        	event.preventDefault();
            var context=$("#searchContext").val().trim();
            window.location.href="/search?context="+context+"&page=1";
        }
    });
    $("#search").click(function () {
            var context=$("#searchContext").val().trim();
            window.location.href="/search?context="+context+"&page=1";
        }
    );

//	nav
	$(".head ul>li").hover(function(){
		var aa=$(this).children().length;
		if(aa!=1){
			$(this).children("div").stop().slideToggle(200).end().siblings().children("div").hide();
		}else{
			$(this).children("div").hide();
		}
	});
	$("a.er1").mouseover(function(){
		$(this).siblings("p").slideDown(100);
	}).mouseout(function(){
		$(this).siblings("p").slideUp(100);
	});
	
//	回到顶部	
	$(".gotop a").hover(function(){
		var aa=$(this).hasClass("dh");
		if(aa==true){
			$(this).find("dt").hide().siblings("dd").fadeIn().parents("a").siblings("p").show().animate({left:"-110px"});
		}else{
			$(this).find("dt").hide().siblings("dd").fadeIn().parents("a").siblings("p").hide().animate({left:"-130px"});
		}
	},function(){
		$(this).find("dt").fadeIn().siblings("dd").hide().parents("a").siblings("p").hide();
	});
	
	$(window).scroll(function(){
		var wh=$(window).scrollTop();
		if(wh>100){
			$(".toptop").fadeIn();
		}else{
			$(".toptop").fadeOut();
		}
		$(".toptop").click(function(){
			$(window).scrollTop(0);
		});
	});
	

//登陆注册
	$("#page").click(function(){
		$(".page").show();
		$(".msk").show();
	});
	$("#reg").click(function(){
		$(".reg").show();
		$(".msk").show();
	});
	$(".off").click(function(){
		$(".page").hide();
		$(".reg").hide();
		$(".msk").hide();
	});
	$('.goReg').click(function(){
		$(".page").hide();
		$(".reg").show();
	});
	$('.goLogin').click(function(){
		$(".reg").hide();
		$(".page").show();
	});
});
function proDetail(itemId) {
    $.ajax({
        url:"/item/selectItemById/"+itemId,
        type:"GET",
        success:function (resData) {
            if (resData.status == 200) {
                var objData=JSON.stringify(resData);
                sessionStorage.setItem("resData", objData);
                window.location.href = "/proDetail";
            }
        }
    })
}
function getCookie(name) {
    var cookies = document.cookie;
    var list = cookies.split("; ");          // 解析出名/值对列表
    for (var i = 0; i < list.length; i++) {
        var arr = list[i].split("=");          // 解析出名和值
        if (arr[0] == name)
            return decodeURIComponent(arr[1]);   // 对cookie值解码
    }
    return "";
}

//获得输入框中字符长度
function getLength(val) {
    var str = new String(val);
    var bytesCount = 0;
    for (var i = 0 ,n = str.length; i < n; i++) {
        var c = str.charCodeAt(i);
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
            bytesCount += 1;
        } else {
            bytesCount += 2;
        }
    }
    return bytesCount;
}

//获取用户信息
function getUserInfo() {
    var user="as";
    $.ajax({
        url:"/user/findUserByUserId",
        method:"GET",
        async:false,
        success:function (resData) {
            // console.log(resData.data);
            user=resData.data;
        }
    });
    return user;
}

function myOrderPay(orderId,price) {
    var params={"orderId":orderId,"price":price};
    postExcelFile(params,"/order/toAliPay");
}

function postExcelFile(params, url) { //params是post请求需要的参数，url是请求url地址
    var form = document.createElement("form");      //创建一个隐藏表单
    form.style.display = 'none';
    form.action = url;
    form.method = "post";
    document.body.appendChild(form);
    for (var key in params) {       //将data数组中的值装进隐藏表单，然后提交隐藏表单到后台
        var input = document.createElement("input");
        input.type = "hidden";
        input.name = key;
        input.value = params[key];
        form.appendChild(input);
    }
    form.submit();
    form.remove();
}


function goCart() {
    window.location.href="/cart";

}