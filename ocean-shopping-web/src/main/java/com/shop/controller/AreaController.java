package com.shop.controller;

import com.shop.pojo.Area;
import com.shop.pojo.City;
import com.shop.pojo.Province;
import com.shop.service.AreaService;
import com.shop.uiils.OceanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hpsyche
 */
@Controller
public class AreaController {
    @Autowired
    private AreaService areaService;
    @RequestMapping(value = "/area/getAllProvinces",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult getAllProvinces(){
        List<Province> list=areaService.getAllProvinces();
        return OceanResult.build(list,"获取成功",200);
    }
    @RequestMapping(value = "/area/getCitiesByPId/{provinceId}",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult getCitiesByPId(@PathVariable String provinceId){
        List<City> list=areaService.selectCitiesByPId(provinceId);
        return OceanResult.build(list,"获取成功",200);
    }
    @RequestMapping(value = "/area/getAreasByCId/{cid}",method = RequestMethod.GET)
    @ResponseBody
    public OceanResult getAreasByCId(@PathVariable String cid){
        List<Area> list=areaService.selectAreasByCId(cid);
        return OceanResult.build(list,"获取成功",200);
    }
}
