$(function () {
    showOrders(0);
})
function showOrders(searchStatus) {
    $.ajax({
        url:"/order/findOrderByUserId/"+searchStatus,
        method:"GET",
        success:function (resData) {
            if(resData.status==200){
                var $myOrders=$("#myOrders");
                $myOrders.html("");
                var resArr=resData.data;
                var arrOn=[];
                for(var m=0;m<4;m++){
                    if(m==searchStatus){
                        arrOn[m]='class="on"';
                    }else {
                        arrOn[m]='';
                    }
                }
                var html='<div class="my clearfix"><h2 class="fl">我的订单</h2>'+
						'<a href="#" class="fl">请谨防钓鱼链接或诈骗电话，了解更多&gt;</a>'+
					'</div>'+
					'<div class="dlist clearfix">'+
						'<ul class="fl clearfix" id="wa">'+
							'<li '+arrOn[0]+'><a href="javascript:showOrders(0)">全部有效订单</a></li>'+
							'<li '+arrOn[1]+'><a href="javascript:showOrders(1)">待支付</a></li>'+
							'<li '+arrOn[2]+'><a href="javascript:showOrders(2)">待收货</a></li>'+
							'<li '+arrOn[3]+'><a href="javascript:showOrders(3)">已关闭</a></li>'+
						'</ul></div>';
                var statusArr=['未付款','已付款','已发货','确认收货'];
                var statusArr2=['去付款','待发货','确认收货','订单完成'];
                var statusHref=['javascript:myOrderPay()','#','#','#'];
                for(var i=0;i<resArr.length;i++){
                    html+='<div class="dkuang"><p class="one">'+statusArr[resArr[i].order.status-1]+'</p><div class="word clearfix"><ul class="fl clearfix">'+
                        '<li>'+resArr[i].order.createTime+'</li><li>'+resArr[i].order.buyerLinkman+
                        '</li><li>订单号:'+resArr[i].order.orderId+'</li><li>在线支付</li></ul><p class="fr">订单金额：<span>'+
                        resArr[i].order.payment/100+'</span>元</p></div>';
                    for(var j=0;j<resArr[i].orderItems.length;j++){
                        html+='<div class="shohou clearfix"><a href="javascript:proDetail(\''+resArr[i].orderItems[j].itemId+'\')" class="fl"><img style="width: 85px;height: 85px" src="'+resArr[i].orderItems[j].picPath+'"/></a>'+
                            '<p class="fl"><a href="javascript:proDetail(\''+resArr[i].orderItems[j].itemId+'\')">'+resArr[i].orderItems[j].title+'</a><a href="#">¥'+resArr[i].orderItems[j].price/100+
                            '×'+resArr[i].orderItems[j].num+'</a></p>';
                        if(j===0){
                            if(resArr[i].order.status===1){
                                html+='<p class="fr"><a id="'+resArr[i].order.orderId+'" href="javascript:myOrderPay(\''+resArr[i].order.orderId+'\',\''+resArr[i].order.payment/100+'\')">'+statusArr2[resArr[i].order.status-1]+'</a><a href="javascript:orderXq(\''+resArr[i].order.orderId+'\')">订单详情</a>' +
                                    '</p></div>';
                            }else {
                                html+='<p class="fr"><a id="'+resArr[i].order.orderId+'" href="#">'+statusArr2[resArr[i].order.status-1]+'</a><a href="javascript:orderXq(\''+resArr[i].order.orderId+'\')">订单详情</a>' +
                                    '</p></div>';
                            }
                        }else {
                            html+='</div>';
                        }
                    }
                    html+='</div>';
                }
                $myOrders.append(html);
            }
        }
    })
}

function orderXq(orderId) {
    sessionStorage.setItem("orderId",orderId);
    window.location.href="/orderxq.html";
}
