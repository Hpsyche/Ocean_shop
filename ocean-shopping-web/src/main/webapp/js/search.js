$(function () {
    var obj={};
    location.search.split("?")[1].split("&").forEach(function (value) {
        var item=value.split("=");
        obj[item[0]]=item[1];
    });
    if(obj.cid!=null){
        $.ajax({
            url:"/item/selectItemsByCidAndSolr?cid="+obj.cid+"&page="+obj.page,
            type:"GET",
            success:function (resData) {
                if(resData.status==200) {
                    var getData=resData.data;
                    var myPage=sessionStorage.getItem("page");
                    if(myPage==null) {
                        myPage = 1;
                    }
                    var $searchResult=$("#searchResult");
                    var itemList=getData.itemList;
                    var temp="";
                    for(var i=0;i<itemList.length;i++ ){
                        var highLight=itemList[i].title.substring(0,35);
                        if(highLight.concat(decodeURI(obj.context))){
                            highLight=highLight.replace(decodeURI(obj.context),'<em style="color: red">'+decodeURI(obj.context)+'</em>');
                        }
                        temp+= '<li><a href="javascript:proDetail(\''+itemList[i].id+'\')"><dl><dt><img src="'+itemList[i].image+'"></dt>'+
                            '<dd style="line-height: 25px">'+highLight+'</dd><br/><dd>￥'+itemList[i].price/100+'</dd></dl></a></li>'
                    }
                    $searchResult.append(temp);
                    $('#box').paging({
                        initPageNo: myPage, // 初始页码
                        totalPages: getData["pageCount"], //总页数
                        totalCount: '合计' + getData["recordCount"] + '条数据', // 条目总数
                        slideSpeed: 600, // 缓动速度。单位毫秒
                        jump: true, //是否支持跳转
                        callback: function(page) { // 回调函数
                            if(page!==myPage) {
                                sessionStorage.setItem("page",page);
                                window.location.href="http://127.0.0.1:8080/search?cid="+obj.cid+"&page="+page;
                            }
                        }
                    });
                }
            }
        });
        return;
    }
    var $keyword=$("#keyword");
    $keyword.append(decodeURI(obj.context));
    $.ajax({
        url:"/item/selectItemsBySolr?context="+decodeURI(obj.context)+"&page="+obj.page,
            type:"GET",
            success:function (resData) {
                if(resData.status==200) {
                    var getData=resData.data;
                    var myPage=sessionStorage.getItem("page");
                    if(myPage==null) {
                        myPage = 1;
                    }
                    $('#box').paging({
                        initPageNo: myPage, // 初始页码
                        totalPages: getData["pageCount"], //总页数
                        totalCount: '合计' + getData["recordCount"] + '条数据', // 条目总数
                        slideSpeed: 600, // 缓动速度。单位毫秒
                        jump: true, //是否支持跳转
                        callback: function(page) { // 回调函数
                            if(page!==myPage) {
                                sessionStorage.setItem("page",page);
                                window.location.href="http://127.0.0.1:8080/search?context="+decodeURI(obj.context)+"&page="+page;
                            }
                        }
                    })
                    var $searchResult=$("#searchResult");
                    var itemList=getData.itemList;
                    var temp="";
                    for(var i=0;i<itemList.length;i++ ){
                        var highLight=itemList[i].title.substring(0,35);
                        if(highLight.concat(decodeURI(obj.context))){
                            highLight=highLight.replace(decodeURI(obj.context),'<em style="color: red">'+decodeURI(obj.context)+'</em>');
                        }
                        temp+= '<li><a href="javascript:proDetail(\''+itemList[i].id+'\')"><dl><dt><img src="'+itemList[i].image+'"></dt>'+
                        '<dd style="line-height: 25px">'+highLight+'</dd><br/><dd>￥'+itemList[i].price/100+'</dd></dl></a></li>'
                    }
                    $searchResult.append(temp);
                }
            }
    });

})