$(function () {
    var orderId=sessionStorage.getItem("orderId");
    $.ajax({
        url:"/order/selectOrderById/"+orderId,
        method:"GET",
        success:function (resData) {
            $orderPro=$("#orderPro");
            var statusArr=['未付款','已付款','已发货','确认收货'];
            var html="";
            html+='<div class="my clearfix"><h2>订单详情<a href="#">请谨防钓鱼链接或诈骗电话，了解更多&gt;</a></h2><h3>订单号：<span>'+
                resData.data.order.orderId+'</span></h3></div><div class="orderList"><div class="orderList1"><h3>'+statusArr[resData.data.order.status-1]+'</h3>';
            for(var i=0;i<resData.data.orderItems.length;i++){
                html+='<div class="clearfix"><a href="#" class="fl"><img style="width: 65px;height: 65px" src="'+resData.data.orderItems[i].picPath+'"/></a>'+
                    '<p class="fl"><a href="#">'+resData.data.orderItems[i].title+'</a><a href="#">¥'+resData.data.orderItems[i].price/100+
                    '×'+resData.data.orderItems[i].num+'</a></p></div>';
            }
            html+='</div><div class="orderList1"><h3>收货信息</h3><p>姓 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：<span>'+resData.data.order.buyerLinkman+'</span></p>'+
                '<p>联系电话：<span>'+resData.data.order.buyerTel+'</span></p><p>收货地址：<span>'+resData.data.order.buyerAddress+
                '</span></p></div><div class="orderList1"><h3>支付方式及送货时间</h3><p>支付方式：<span>在线支付</span></p><p>送货时间：<span>不限送货时间</span></p>'+
                '</div><div class="orderList1 hei"><h3><strong>商品总价：</strong><span>¥'+resData.data.order.payment/100+'</span></h3>'+
                '<p><strong>运费：</strong><span>¥0</span></p><p><strong>订单金额：</strong><span>¥'+resData.data.order.payment/100+'</span></p>'+
                '<p><strong>实付金额：</strong><span>¥'+resData.data.order.payment/100+'</span></p></div></div>';
            $orderPro.append(html);
        }
    })

})