package com.shop.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.shop.pojo.CreateAliOrder;
import com.shop.pojo.OsItem;
import com.shop.service.ItemService;
import com.shop.uiils.JsonUtils;
import com.shop.uiils.OceanResult;
import com.shop.uiils.PageUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.solr.client.solrj.SolrServerException;
import org.noggit.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Hpsyche
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 保存商品
     */
    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public OceanResult saveItem(OsItem item) {
        return itemService.saveItem(item);
    }

    /**
     * 首页的热门商品
     * 获取type类型的num个商品，按销量排序
     */
    @RequestMapping(value = "/item/getNItems", method = RequestMethod.GET)
    @ResponseBody
    public OceanResult getItemsByNum(Integer num, Byte type) {
        return itemService.getItemsByNumOrderBySellCount(num, type);
    }

    /**
     * 根据id查询商品
     */
    @RequestMapping(value = "/item/selectItemById/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public OceanResult getItemById(@PathVariable long itemId) {
        OsItem osItem = itemService.selectItemById(itemId);
        if (osItem == null) {
            return OceanResult.build(osItem, "查询失败", 400);
        } else {
            return OceanResult.build(osItem, "查询成功", 200);
        }
    }

    /**
     * 相似推荐：
     * 查询相同cid类别的num个商品（也是按销售量排序）
     */
    @RequestMapping(value = "/item/getLikeItems/{num}/{cid}", method = RequestMethod.GET)
    @ResponseBody
    public OceanResult getLikeItemsByCid(@PathVariable Integer num, @PathVariable long cid) {
        List<OsItem> osItems = itemService.getByCidAndNum(num, cid);
        return OceanResult.build(osItems, "查询成功", 200);
    }

    /**
     * 导入所有商品条目入solr索引库
     */
    @RequestMapping(value = "/item/importAll", method = RequestMethod.GET)
    @ResponseBody
    public OceanResult importAllItems() throws IOException, SolrServerException {
        itemService.importAll();
        return OceanResult.ok();
    }

    /**
     * 搜索功能：从solr索引库中查询商品
     */
    @RequestMapping(value = "/item/selectItemsBySolr", method = RequestMethod.GET)
    @ResponseBody
    public OceanResult selectItemsBySolr(@RequestParam String context, @RequestParam Integer page) throws SolrServerException {
        //todo:重构代码，实现类别商品的索引
        PageUtils search = itemService.search(context, page, 16,0L);
        OceanResult result = OceanResult.build(search, "成功", 200);
        return result;
    }

    @RequestMapping(value = "/item/selectItemsByCidAndSolr", method = RequestMethod.GET)
    @ResponseBody
    public OceanResult selectItemsBySolr(@RequestParam Long cid, @RequestParam Integer page) throws SolrServerException {
        PageUtils search = itemService.search(null, page, 16,cid);
        OceanResult result = OceanResult.build(search, "成功", 200);
        return result;
    }

}
