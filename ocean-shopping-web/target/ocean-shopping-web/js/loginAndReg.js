var flag = false;
var flag2= false;
$(window).scroll(function () {
    var t=document.documentElement.scrollTop;
    if(t>1100) {
        var $secondHand = $("#secondHand");
        if (flag === false) {
            flag = true;
            $.ajax({
                //获取5个人气二手商品
                url: "/item/getNItems?num=5&type=2",
                type: "GET",
                success: function (resData) {
                    if (resData.status == 200) {
                        var temp="";
                        for (var i = 0; i < resData.data.length; i++) {
                            temp += '<a href="javascript:proDetail(\'' + resData.data[i]["id"] + '\')" class="clearfix"><dl><dt>' +
                                '<span class="abl"></span><img src="' + resData.data[i]["image"] + '" width="356" height="356"/><span class="abr">' +
                                '</span></dt><dd>' + resData.data[i]["title"] + '</dd><dd><span>￥' + (resData.data[i]["price"] / 100)
                                + '</span></dd></dl></a>';
                        }
                        $secondHand.append(temp);
                    } else {
                    }
                }
            })
        }
    }
    if(t>1800) {
        var $divAppend=$("#divAppend");
        if(flag2 === false){
            flag2=true;
            $.ajax({
                //获取12个人气海大商城商品
                url:"/item/getNItems?num=12&type=1",
                type:"GET",
                success:function(resData){
                    if(resData.status==200){
                        var temp="";
                        for(var i=0;i<resData.data.length;i++){
                            if(i%4===0){
                                temp=temp+'<div class="pList clearfix tran">';
                            }
                            temp=temp+'<a href="javascript:proDetail(\''+resData.data[i]["id"]+'\')" class="clearfix"><dl><dt>' +
                                '<span class="abl"></span><img src="'+resData.data[i]["image"]+'" width="268" height="268"/><span class="abr">' +
                                '</span></dt><dd>'+resData.data[i]["title"]+'</dd><dd><span>￥'+(resData.data[i]["price"]/100)
                                +'</span></dd></dl></a>';
                            if(i%4===3){
                                temp=temp+'</div>';
                            }
                        }
                        $divAppend.append(temp);
                    }else {
                    }
                }
            })
        }
    }

})