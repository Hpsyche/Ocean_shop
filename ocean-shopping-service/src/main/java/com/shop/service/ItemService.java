package com.shop.service;

import com.shop.pojo.OsItem;
import com.shop.uiils.GetItemsByCidAndNum;
import com.shop.uiils.OceanResult;
import com.shop.uiils.PageUtils;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

/**
 * @author Hpsyche
 */
public interface ItemService {
    /**
     * 保存商品信息
     * @param item
     * @return
     */
    OceanResult saveItem(OsItem item);

    /**
     * 根据销售量的排序，获取指定数量的商品
     * @param num
     * @param type
     * @return
     */
    OceanResult getItemsByNumOrderBySellCount(Integer num, Byte type);

    /**
     * 通过id查询商品详情信息
     * @param itemId
     * @return
     */
    OsItem selectItemById(long itemId);

    /**
     * 通过cid查询前num个商品
     * @param num
     * @param cid
     * @return
     */
    List<OsItem> getByCidAndNum(Integer num, long cid);

    /**
     * 导入所有商品
     */
    void importAll() throws IOException, SolrServerException;

    /**
     * 分页查询i条商品
     * @param context
     * @param page
     * @param rows
     * @return
     */
    PageUtils search(String context, Integer page, Integer rows,Long cid) throws SolrServerException;

    /**
     * 通过id查询商品价格
     * @param itemId
     * @return
     */
    long selectPriceByItemId(Long itemId);
}
