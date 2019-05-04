package com.shop.service.impl;

import com.shop.mapper.ItemClassMapper;
import com.shop.pojo.OsItemClass;
import com.shop.service.ItemClassService;
import com.shop.uiils.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hpsyche
 */
@Service
public class ItemClassServiceImpl implements ItemClassService {
    @Autowired
    private ItemClassMapper itemClassMapper;
    @Override
    public List<EasyUITreeNode> getItemClassList(Long parentId) {
        List<OsItemClass> list = itemClassMapper.selectByParentId(parentId);
        List<EasyUITreeNode> easyUITreeNodeList=new ArrayList<EasyUITreeNode>();
        for(OsItemClass osItemClass:list){
            EasyUITreeNode node=new EasyUITreeNode();
            node.setId(osItemClass.getId());
            node.setText(osItemClass.getName());
            node.setState(osItemClass.getIsParent()?"close":"open");
            easyUITreeNodeList.add(node);
        }
        return easyUITreeNodeList;
    }

    @Override
    public OsItemClass getItemClassById(Long cid) {
        return itemClassMapper.selectById(cid).get(0);
    }

    @Override
    public List<String> getAllName() {
        List<OsItemClass> classes = itemClassMapper.getAllName();
        List<String> res=new ArrayList<String>();
        for(OsItemClass osItemClass:classes){
            res.add(osItemClass.getName());
        }
        return res;
    }
}
