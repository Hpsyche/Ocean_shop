$(function () {
    // var cookie = getCookie("COOKIE_USER_INFO");
    $.ajax({
        url:"/cart/findItemsByUserId",
        method:"GET",
        success:function (resData) {
            if (resData.status == 200) {
                var temp="";
                for(var i=0;i<resData.data.length;i++){
                    if(resData.data[i].title.length>18){
                        resData.data[i].title=resData.data[i].title.substring(0,18)+"...";
                    }
                    temp+='<div class="th"><div class="pro clearfix"><label class="fl"><input name="myCheckBox" checkboxId="'+i+'" itemId="'+resData.data[i].id+'" itemNum="'+resData.data[i].num+'" type="checkbox"/><span></span></label>'+
                        '<a class="fl" href="#"><dl class="clearfix"><dt class="fl"><img src="'+resData.data[i].image+
                        '" style="width: 120px;height: 120px"></dt><dd class="fl">'+
                        '<p>'+resData.data[i].title+'</p><p>详情信息:</p><p>'+resData.data[i].sellPoint+'</p></dd></dl></a></div>'+
                        '<div id="price'+i+'" class="price" style="text-align: center">￥'+resData.data[i].price/100+'</div><div class="number">' +
                        '<p class="num clearfix"><img id="sub" itemIndex="'+i+'" itemId="'+resData.data[i].id+'"  class="fl sub" src="../../img/temp/sub.jpg">'+
                        '<span id="num'+i+'" class="fl">'+resData.data[i].num+'</span><img id="add" itemIndex="'+i+'" itemId="'+resData.data[i].id+
                        '" class="fl add" src="../../img/temp/add.jpg"></p></div><div id="priceAll'+i+'" class="price sAll" style="text-align: center">￥'+
                        resData.data[i].price*resData.data[i].num/100+'</div><div class="price" style="text-align: center"><a class="del" href="#2" itemId="'+resData.data[i].id+'">删除</a></div></div>'
                    // totalNum+=parseInt(resData.data[i].num);
                    // totalAllPrice+=parseInt(resData.data[i].price*resData.data[i].num/100);
                }
                temp+='<div class="tr clearfix">\n' +
                    '<label class="fl">\n' +
                    '<input class="checkAll" type="checkbox"/>\n' +
                    '<span></span>\n' +
                    '</label>\n' +
                    '<p class="fl">\n' +
                    '</p>\n' +
                    '<p class="fr">\n' +
                    '<span>共<small id="sl">'+0+'</small>件商品</span>\n' +
                    '<span>合计:&nbsp;<small id="all">'+0+'</small></span>\n' +
                    '<a href="javascript:toOrder()" class="count">结算</a>\n' +
                    '</p>\n' +
                    '</div>';
                var $cartItems=$("#cartItems");
                var row=$(temp);
                $cartItems.append(row);
                myCart();
            } else {
            }
        }
    });
});
function toOrder(){
    var $sl=$("#sl");
    if($sl.html()==0){
        return;
    }
    var listMap=[];
    var i=0;
    $("input:checkbox[name=myCheckBox]:checked").each(function(){
        var map={};
        map['id']=$(this).attr('itemId');
        map['num']=$(this).attr('itemNum');
        listMap[i]=map;
        i++;
    });
    sessionStorage.setItem("myMap",JSON.stringify(listMap));
    sessionStorage.setItem("ifOrder","true");
    window.location.href="/order.html";
}

/**************数量加减***************/
function myCart() {
    $(".num .sub").click(function(){
        var itemId=$(this).attr('itemId');
        var num = parseInt($(this).siblings("span").text());
        if(num<=1){
            $(this).attr("disabled","disabled");
        }else{
            num--;
            $("#maxCountMsg").html("");
            $(this).siblings("span").text(num);
            //获取除了货币符号以外的数字
            var price = $(this).parents(".number").prev().text().substring(1);
            //单价和数量相乘并保留两位小数
            $(this).parents(".th").find(".sAll").text('￥'+(num*price).toFixed(2));
            jisuan();
            zg();
        }
        $.ajax({
            url:"/cart/subItemNum/"+itemId,
            method:"GET",
            success:function () {

            }
        })
    });
    $(".num .add").click(function(){
        var itemId=$(this).attr('itemId');
        var num = parseInt($(this).siblings("span").text());
        if(num>=5){
            // $("#msg").html("限购5件");
            // $("#maxCountMsg").html("限购5件");
            alert("限购5件");
        }else {
            num++;
            $(this).siblings("span").text(num);
            var price = $(this).parents(".number").prev().text().substring(1);
            $(this).parents(".th").find(".sAll").text('￥' + (num * price).toFixed(2));
            jisuan();
            zg();
        }
            $.ajax({
                url:"/cart/addItemNum/"+itemId,
                method:"GET",
                success:function () {

                }
            })
    });
//计算总价
    function jisuan(){
        var all=0;
        var len =$(".th input[type='checkbox']:checked").length;
        if(len==0){
            $("#all").text('￥'+parseFloat(0).toFixed(2));
        }else{
            $(".th input[type='checkbox']:checked").each(function(){
                //获取小计里的数值
                var sAll = $(this).parents(".pro").siblings('.sAll').text().substring(1);
                //累加
                all+=parseFloat(sAll);
                //赋值
                $("#all").text('￥'+all.toFixed(2));
            })
        }
    }
//计算总共几件商品
    function zg(){
        var zsl = 0;
        var index = $(".th input[type='checkbox']:checked").parents(".th").find(".num span");
        var len =index.length;
        if(len==0){
            $("#sl").text(0);
        }else{
            index.each(function(){
                zsl+=parseInt($(this).text());
                $("#sl").text(zsl);
            })
        }
        if($("#sl").text()>0){
            $(".count").css("background","#c10000");
        }else{
            $(".count").css("background","#8e8e8e");
        }
    }
    /*****************商品全选***********************/
    $("input[type='checkbox']").on('click',function(){
        var sf = $(this).is(":checked");
        var sc= $(this).hasClass("checkAll");
        if(sf){
            if(sc){
                $("input[type='checkbox']").each(function(){
                    this.checked=true;
                });
                zg();
                jisuan();
            }else{
                $(this).checked=true;
                var len = $("input[type='checkbox']:checked").length;
                var len1 = $("input").length-1;
                if(len==len1){
                    $("input[type='checkbox']").each(function(){
                        this.checked=true;
                    });
                }
                zg();
                jisuan();
            }
        }else{
            if(sc){
                $("input[type='checkbox']").each(function(){
                    this.checked=false;
                });
                zg();
                jisuan();
            }else{
                $(this).checked=false;
                var len = $(".th input[type='checkbox']:checked").length;
                var len1 = $("input").length-1;
                if(len<len1){
                    $('.checkAll').attr("checked",false);
                }
                zg();
                jisuan();
            }
        }

    });
    /****************************proDetail 加入购物车*******************************/
    $(".btns .cart").click(function(){
        if($(".categ p").hasClass("on")){
            var num = parseInt($(".num span").text());
            var itemId=$("#itemId").text();
            //存入商品和数量
            $.ajax({
                url:"/cart/addItems/"+itemId+"/"+num,
                method:"GET",
                success:function (resData) {
                }
            })
            var num1 = parseInt($(".goCart span").text());
            $(".goCart span").text(num+num1);
        }
    });

//删除购物车商品
    $('.del').click(function(){
        var itemId=$(this).attr('itemId');
        //单个删除
        if($(this).parent().parent().hasClass("th")){
            $(".mask").show();
            $(".tipDel").show();
            index = $(this).parents(".th").index()-1;
            $('.cer').click(function(){
                $(".mask").hide();
                $(".tipDel").hide();
                $(".th").eq(index).remove();
                $('.cer').off('click');
                if($(".th").length==0){
                    $(".table .goOn").show();
                }
                $.ajax({
                    url:"/cart/deleteItems/"+itemId,
                    method:"GET",
                    success:function (resData) {
                    }
                })
            }
        )
        }else{
            //选中多个一起删除
            if($(".th input[type='checkbox']:checked").length==0){
                $(".mask").show();
                $(".pleaseC").show();
            }
            else{
                $(".mask").show();
                $(".tipDel").show();
                $('.cer').click(function(){
                    $(".th input[type='checkbox']:checked").each(function(j){
                        index = $(this).parents('.th').index()-1;
                        $(".th").eq(index).remove();
                        if($(".th").length==0){
                            $(".table .goOn").show();
                        }
                    })
                    $(".mask").hide();
                    $(".tipDel").hide();
                    zg();
                    jisuan();
                })
            }
        }
    })
    $('.cancel').click(function(){
        $(".mask").hide();
        $(".tipDel").hide();
    })
}