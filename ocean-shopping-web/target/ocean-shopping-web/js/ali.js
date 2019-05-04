function pay() {
    var $address=$(".on");
    var $address2=$($($address).find('.clearfix'));
    if($address2.length==0){
        alert("请选择收货信息！")
        return;
    }
    var $msg=$(".msg").children();
    var listMap=[];
    for(var i=1;i<$msg.length;i++){
        var map={};
        map['id']=$($msg[i]).attr('itemid');
        map['num']=$($msg[i]).attr('num');
        listMap[i-1]=map;
    }
    $.ajax({
        url:"/order/createOrder",
        data:{listMap:JSON.stringify(listMap)},
        method:"POST",
        success:function (resData) {
            if(resData.status==200){
                sessionStorage.setItem("ifOrder","false");
                var $address3=$($($address).find('.addCon'));
                var myMap={};
                myMap["id"]=resData.data;
                myMap["name"]=$address2.find('.fl').html();
                myMap["address"]=$address3.find('#reAddress').html();
                myMap["mobile"]=$address3.find('#reMobile').html();
                myMap["money"]=$("#totalFeePrice").html().substring(1);
                console.log(myMap);
                $.ajax({
                    url:"/order/updateOrder",
                    contentType:"application/json",
                    data:JSON.stringify(myMap),
                    method:"POST",
                    success:function (resData) {
                        var arr=resData.split(":");
                        myOrderPay(arr[0],arr[1]);
                    }
                });
            }
        }
    })
}