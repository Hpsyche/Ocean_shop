package com.shop.mapper;

import com.shop.pojo.City;
import com.shop.pojo.Province;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface ProvinceMapper {
    /**
     * 获取省份列表
     * @return
     */
    List<Province> getAllProvinces();

    /**
     * 通过省份id查询省份
     * @param pid
     * @return
     */
    Province selectProvinceByPid(String pid);
}
