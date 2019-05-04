package com.shop.service;

import com.shop.pojo.Area;
import com.shop.pojo.City;
import com.shop.pojo.Province;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface AreaService {
    /**
     * 获取所有省份
     * @return
     */
    List<Province> getAllProvinces();

    /**
     * 根据省份id查询旗下所有城市
     * @param pid
     * @return
     */
    List<City> selectCitiesByPId(String pid);

    /**
     * 根据城市id查询旗下所有区县
     * @param cid
     * @return
     */
    List<Area> selectAreasByCId(String cid);

    /**
     * 通过区县id查询区县
     * @param aid
     * @return
     */
    Area selectAreaByAId(String aid);

    /**
     * 通过城市id查询城市
     * @param cid
     * @return
     */
    City selectCityByCId(String cid);

    /**
     * 通过省份id查询省份
     * @param pid
     * @return
     */
    Province selectProvinceByPId(String pid);
}
