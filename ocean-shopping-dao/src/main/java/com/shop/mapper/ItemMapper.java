package com.shop.mapper;

import com.shop.pojo.OsItem;
import com.shop.uiils.GetItemsByCidAndNum;
import com.shop.uiils.ItemSearch;
import com.shop.uiils.SelectItemsByNumConditionBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface ItemMapper {
    /**
     * 插入商品
     * @param item
     */
    void insertItem(OsItem item);

    /**
     * 查询指定数量和类型的人气商品
     * @param bean
     * @return
     */
    List<OsItem> getItemsByNumOrderBySellCount(SelectItemsByNumConditionBean bean);

    /**
     * 根据id查询商品
     * @param itemId
     * @return
     */
    List<OsItem> selectById(long itemId);

    /**
     * 通过cid查询前num个商品
     * @param beam
     * @return
     */
    List<OsItem> getByCidAndNum(GetItemsByCidAndNum beam);

    /**
     * 查询所有
     * @return
     */
    List<OsItem> selectAllItems();

    /**
     * 通过id查询商品价格
     * @param itemId
     * @return
     */
    long selectPriceByItemId(long itemId);

}
