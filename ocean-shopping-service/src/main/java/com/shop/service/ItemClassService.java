package com.shop.service;

import com.shop.pojo.OsItemClass;
import com.shop.uiils.EasyUITreeNode;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface ItemClassService {
    /**
     * 根据parentId得到list
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getItemClassList(Long parentId);

    /**
     * 根据id查询ItemClass
     * @param cid
     * @return
     */
    OsItemClass getItemClassById(Long cid);

    /**
     * 返回所有类别的name
     * @return
     */
    List<String> getAllName();
}
