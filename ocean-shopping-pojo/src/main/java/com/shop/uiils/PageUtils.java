package com.shop.uiils;

import com.shop.pojo.OsItem;

import java.util.List;

/**
 * @author Hpsyche
 */
public class PageUtils {
    private List<ItemSearch> itemList;
    private long recordCount;
    private long pageCount;

    public List<ItemSearch> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemSearch> itemList) {
        this.itemList = itemList;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }
}
