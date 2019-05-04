package com.shop.service.impl;

import com.shop.mapper.AreaMapper;
import com.shop.mapper.CityMapper;
import com.shop.mapper.ProvinceMapper;
import com.shop.pojo.Area;
import com.shop.pojo.City;
import com.shop.pojo.Province;
import com.shop.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hpsyche
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Override
    public List<Province> getAllProvinces() {
        return provinceMapper.getAllProvinces();
    }

    @Override
    public List<City> selectCitiesByPId(String pid) {
        return cityMapper.selectCitiesByPId(pid);
    }

    @Override
    public List<Area> selectAreasByCId(String cid) {
        return areaMapper.selectAreasByCId(cid);
    }

    @Override
    public Area selectAreaByAId(String aid) {
        return areaMapper.selectAreasByAId(aid);
    }

    @Override
    public City selectCityByCId(String cid) {
        return cityMapper.selectCityByCid(cid);
    }

    @Override
    public Province selectProvinceByPId(String pid) {
        return provinceMapper.selectProvinceByPid(pid);
    }

}
