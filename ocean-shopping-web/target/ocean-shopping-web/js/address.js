$(function () {
    var $showAddress=$("#showAddress");
    var $orderAddress=$("#orderAddress");
    $.ajax({
        url:"/address/getAllAddress",
        method:"GET",
        success:function (resData) {
            var html="";
            for(var i=0;i<resData.data.length;i++){
                html+='<div class="dizhi" id="'+resData.data[i].addressId+'" style="border: #D8D8D8 1px solid"><p style="margin-top: 20px">'+resData.data[i].receiverName+'</p><p>'+resData.data[i].receiverMobile
                +'</p><p>'+resData.data[i].receiverAddress+'</p></div>'
            }
            $showAddress.append(html);
            var html2="";
            for(var i=0;i<resData.data.length;i++){
                html2+='<div class="addre fl"><div class="tit clearfix"><p class="fl">'+resData.data[i].receiverName+
                    '</p><p class="fr"><a href="javascript:deleteOrderAddress(\''+resData.data[i].addressId+'\')">删除</a><span>|</span><a href="javascript:addOrderAddress()" class="edit">编辑</a>'+
                    '</p></div><div class="addCon"><p id="reAddress">'+resData.data[i].receiverAddress+'</p><p id="reMobile">'+resData.data[i].receiverMobile+
                    '</p></div></div>'
            }
            $showAddress.append(html);
            $orderAddress.append(html2);
            $(".addre").click(function () {
                $(".addre").attr("class","addre fl");
                $(this).attr("class","addre fl on");
            });
            address();

        }
    })

});
// var $addreMoren=;
$("#addOrderAddress").click(function () {
    $(".mask").show();
    $(".adddz").show();
})
function addOrderAddress() {
    $(".mask").show();
    $(".readd").show();
    address();
    $(".bc>input").click(function(){
        if($(this).val()=="保存"){
            $(".mask").hide();
            $(".readd").hide();
        }else{
            $(".mask").hide();
            $(".readd").hide();
        }
    });
    $(".addp").remove();
}

function address() {
    //	address
    $("#addxad").click(function(){
        $(".mask").show();
        $(".adddz").show();
    });
    $(".dizhi").hover(function(){
        var txt="";
        txt='<p class="addp"><a href="javascript:selectAddress(\''+ $(this).attr("id")+'\')"  id="readd" style="color:#A00000">修改&nbsp;</a><a href="javascript:deleteAddress(\''+ $(this).attr("id")+'\')"   id="deladd" style="color: #A00000">删除</a></p>'
        $(this).append(txt);
        // $("#readd").click(function(){
        //     $(".mask").show();
        //     $(".readd").show();
        // });
        $("#deladd").click(function(){
            $(this).parents(".dizhi").remove();
        });
    },function(){
        $(".bc>input").click(function(){
            if($(this).val()=="保存"){
                $(".mask").hide();
                $(".readd").hide();
            }else{
                $(".mask").hide();
                $(".readd").hide();
            }
        });
        $(".addp").remove();
    });

//	查看物流
    $(".vewwl").hover(function(){
        $(this).children(".wuliu").fadeIn(100);
    },function(){
        $(this).children(".wuliu").fadeOut(100);
    });
}
function deleteOrderAddress(addId) {
    $.ajax({
        url:"/address/deleteAddress/"+addId,
        method:"GET",
        success:function () {
            window.location.href="order.html";
        }
    })
}

function deleteAddress(addId) {
    $.ajax({
        url:"/address/deleteAddress/"+addId,
        method:"GET",
        success:function () {
            window.location.href="address.html";
        }
    })
}


function selectAddress(addId){
    $(".mask").show();
    $(".readd").show();
    $("#addressId").attr('value',addId);
    var $province=$("#provinceUpdate");
    var $city=$("#cityUpdate");
    var $area=$("#areaUpdate");
    $.ajax({
        url:"/area/getAllProvinces",
        method:"GET",
        success:function (resData) {
            var proHtml="";
            for(var i=0;i<resData.data.length;i++){
                proHtml+='<option value="'+resData.data[i].codeP+'">'+resData.data[i].name+'</option>';
            }
            $province.append(proHtml);
        }
    })
    $province.change(function () {
        var provinceId= $province.val(); // 获取选中的值
        // var provinceName= $province.find("option:selected").text();//获取选中的文本内容
        $city.empty();
        $area.empty();
        $.ajax({
            url:"/area/getCitiesByPId/"+provinceId,
            method:"GET",
            success:function (resData) {
                var cityHtml="";
                for(var i=0;i<resData.data.length;i++){
                    cityHtml+='<option value="'+resData.data[i].codeC+'">'+resData.data[i].name+'</option>';
                }
                $city.append(cityHtml);
            }
        })
    })
    $city.change(function () {
        var cityId= $city.val(); // 获取选中的值
        // var provinceName= $province.find("option:selected").text();//获取选中的文本内容
        $area.empty();
        $.ajax({
            url:"/area/getAreasByCId/"+cityId,
            method:"GET",
            success:function (resData) {
                var areaHtml="";
                for(var i=0;i<resData.data.length;i++){
                    areaHtml+='<option value="'+resData.data[i].codeA+'">'+resData.data[i].name+'</option>';
                }
                $area.append(areaHtml);
            }
        })
    })
}
