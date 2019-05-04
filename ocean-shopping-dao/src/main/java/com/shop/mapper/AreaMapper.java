package com.shop.mapper;

import com.shop.pojo.Area;
import com.shop.pojo.City;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface AreaMapper {
    /**
     * 通过城市id查询所有区县
     * @param cid
     * @return
     */
    List<Area> selectAreasByCId(String cid);

    /**
     * 通过区县id查询区县
     * @param aid
     * @return
     */
    Area selectAreasByAId(String aid);
}
