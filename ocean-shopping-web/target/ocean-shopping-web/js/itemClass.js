$(function () {
    $.ajax({
        url:"/item/cat/getAllCatName",
        type:"GET",
        success:function (resData) {
            var $bott=$("#bott");
            var cid=110;
            for(var i=0;i<resData.length;i++){
                $bott.append('<li><a href="/search?cid='+(cid+i)+'&page=1">'+resData[i]+'</a></li>');
            }
            var $itemClass=$("#itemSearchClass");
            for(var i=0;i<resData.length;i++){
                $itemClass.append('<a href="search?cid='+(cid+i)+'&page=1">'+resData[i]+'</a>');
            }
        }
    })
})