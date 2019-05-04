package com.shop.controller;

import com.shop.pojo.OsItemClass;
import com.shop.service.ItemClassService;
import com.shop.uiils.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Hpsyche
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemClassService itemClassService;

    /**
     * 获取父类别节点下的子类别节点
     */
    @RequestMapping(value = "/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemClassList(@RequestParam(value = "id",defaultValue = "0")Long parentId){
        return itemClassService.getItemClassList(parentId);
    }

    /**
     * 根据cid获取类别名称
     */
    @RequestMapping(value = "/item/cat/getName/{cid}")
    @ResponseBody
    public OsItemClass getItemClassById(@PathVariable Long cid){
        OsItemClass itemClass = itemClassService.getItemClassById(cid);
        return itemClass;
    }

    /**
     * 获取所有的类别名称
     */
    @RequestMapping(value = "/item/cat/getAllCatName")
    @ResponseBody
    public List<String> getAllCatName(){
        return itemClassService.getAllName();
    }

}
