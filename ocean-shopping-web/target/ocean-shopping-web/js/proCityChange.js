$(function () {
    var $province=$("#province");
    var $city=$("#city");
    var $area=$("#area");
    getProvinces();
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
})
function getProvinces() {
    var $province=$("#province");
    var $city=$("#city");
    var $area=$("#area");
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
}