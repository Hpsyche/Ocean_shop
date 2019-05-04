package com.shop.mapper;

import com.shop.pojo.OsItemClass;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface ItemClassMapper {
    /**
     * 通过id查询OsItemClass
     * @param parentId
     * @return
     */
    List<OsItemClass> selectByParentId(Long parentId);

    /**
     * 根据id查询itemClass
     * @param cid
     * @return
     */
    List<OsItemClass> selectById(Long cid);

    /**
     * 获取所有的name
     * @return
     */
    List<OsItemClass> getAllName();
}
