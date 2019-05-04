$(function () {
    var itemData=sessionStorage.getItem("resData");
    var data2=JSON.parse(itemData);
    var getObj=data2["data"];
    var $top2=$("#top2");
    var $top3=$("#top3");
    var $img1=$("#img1");
    var $img2=$("#img2");
    var $itemName=$("#itemName");
    var $itemSellPoint=$("#itemSellPoint");
    var $itemPrice=$("#itemPrice");
    var $itemClass=$("#itemClass");
    var $num=$("#num");
    var $sellCount=$("#sellCount");
    var $msgImgs=$("#msgImgs");
    var $itemId=$("#itemId");
    $top3.append(getObj["title"]);
    $img1.attr('src',getObj["image"]);
    $img2.attr('src',getObj["image"]);
    if(getLength(getObj["title"])>50){
        $itemName.append(getObj["title"].substring(0,50)+"...");
    }else {
        $itemName.append(getObj["title"]);
    }
    $itemSellPoint.append(getObj["sellPoint"]);
    $itemPrice.append('￥'+getObj["price"]/100);
    $itemId.append(getObj["id"]);
    var imageAlt=getObj["title"];
    if(getLength(getObj["title"])>10){
        imageAlt=getObj["title"].substring(0,10)+"...";
    }
    $itemClass.append('<img src="'+getObj["image"]+'" alt="'+imageAlt+'" data-src="'+getObj["image"]+'">');
    $num.append(getObj["num"]);
    $sellCount.append(getObj["sellCount"]);
    $msgImgs.append('<img src="'+getObj["image"]+'">');
    $.ajax({
        url:"/item/cat/getName/"+getObj["cid"],
        type:"GET",
        success:function (resData) {
            var myClass=resData["name"];
            $top2.append(myClass);
        }
    })
    var $scroll1=$("#scroll1");
    var $scroll2=$("#scroll2");
    $.ajax({
        url:"/item/getLikeItems/10/"+getObj["cid"],
        type:"GET",
        success:function (resData) {
            var data=resData.data;
            for(var i=0;i<data.length;i++){
                var myHtml = '<a href="javascript:proDetail(\'' + data[i].id + '\')">' +
                    '<dl>' +
                    '<dt><img src="' + data[i].image + '"></dt>' +
                    '<dd>' + data[i].title + '</dd>' +
                    '<dd>￥' + data[i].price / 100 + '</dd>' +
                    '</dl></a>';
                if(i<5){
                    $scroll1.append(myHtml);
                }
            }
        }
    })

})