$(function () {
    var judge=sessionStorage.getItem("ifOrder");
    if(judge!=="true"){
        window.location.href="myorderq";
    }
    var itemMap=sessionStorage.getItem("myMap");
    $.ajax({
        url:"/order/toBalance",
        data:{listMap:JSON.stringify(itemMap)},
        method:"POST",
        success:function (resData) {
            var mOrder=resData.data.order;
            var mOrderItems=resData.data.orderItems;
            var $orderAndItems=$("#orderAndItems");
            var mHtml="";
            $orderAndItems.css({height:40+110*mOrderItems.length});
            for(var i=0;i<mOrderItems.length;i++){
                if(mOrderItems[i].title.length>18){
                    mOrderItems[i].title=mOrderItems[i].title.substring(0,18)+"...";
                }
                mHtml+='<ul class="clearfix" itemId="'+mOrderItems[i].id+'" num="'+mOrderItems[i].num+'"><li class="fl"><img src="'+mOrderItems[i].picPath+'" style="width: 87px;height: 87px"></li>'+
                    '<li class="fl"><p>'+mOrderItems[i].title+'</p><p>数量：'+mOrderItems[i].num+'</p></li>'+
                    '<li class="fr">￥'+mOrderItems[i].price*mOrderItems[i].num/100+'</li></ul>';
            }
            $orderAndItems.append(mHtml);
            var $fees=$("#fees");
            var feeHtml="";
            feeHtml+='<p><span class="fl">商品金额：</span><span class="fr">￥'+mOrder.payment/100+'</span></p>'+
                '<p><span class="fl">优惠金额：</span><span class="fr">￥0.0</span></p>'+
                '<p><span class="fl">运费：</span><span class="fr">免运费</span></p>';
            $fees.append(feeHtml);
            var $totalFee=$("#totalFee");
            $totalFee.attr("orderId",mOrder.orderId);
            var totalFeeHtml="";
            totalFeeHtml+='<p><span class="fl">合计：</span><span class="fr" id="totalFeePrice">￥'+mOrder.payment/100+'</span></p>';
            $totalFee.append(totalFeeHtml);
        }
    })


})