package com.shop.mapper;

import com.shop.pojo.Area;
import com.shop.pojo.City;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface CityMapper {
    /**
     * 通过省份id查询所有城市
     * @param pid
     * @return
     */
    List<City> selectCitiesByPId(String pid);

    /**
     * 通过城市id查询城市
     * @param cid
     * @return
     */
    City selectCityByCid(String cid);
}
