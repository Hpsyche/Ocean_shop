package com.shop.service.impl;

import com.shop.mapper.ItemMapper;
import com.shop.pojo.OsItem;
import com.shop.service.ItemService;
import com.shop.uiils.*;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Hpsyche
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    public OceanResult saveItem(OsItem item) {
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        itemMapper.insertItem(item);
        return OceanResult.ok();
    }
    @Override
    public OceanResult getItemsByNumOrderBySellCount(Integer num, Byte type) {
        SelectItemsByNumConditionBean bean=new SelectItemsByNumConditionBean();
        bean.setNum(num);
        bean.setType(type);
        List<OsItem> list = itemMapper.getItemsByNumOrderBySellCount(bean);
        return OceanResult.build(list,"查询成功",200);
    }
    @Override
    public OsItem selectItemById(long itemId) {
        List<OsItem> list=itemMapper.selectById(itemId);
        if(list.size()==0){
            return null;
        }else {
            return list.get(0);
        }
    }
    @Override
    public List<OsItem> getByCidAndNum(Integer num, long cid) {
        GetItemsByCidAndNum bean=new GetItemsByCidAndNum(num,cid);
        return itemMapper.getByCidAndNum(bean);
    }
    @Override
    public void importAll() throws IOException, SolrServerException {
        //1.查询所由商品
        List<OsItem> osItems = itemMapper.selectAllItems();
        //2.导入索引库
        SolrServer solrServer=new HttpSolrServer("http://192.168.25.128:8080/solr");
        for(OsItem item:osItems){
            SolrInputDocument document=new SolrInputDocument();
            document.addField("id",item.getId());
            document.addField("item_title",item.getTitle());
            document.addField("item_sell_point", item.getSellPoint());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCid());
            solrServer.add(document);
        }
        //提交修改
        solrServer.commit();
    }
    @Override
    public PageUtils search(String context, Integer page, Integer rows,Long cid) throws SolrServerException {
        SolrQuery query=new SolrQuery();
        if(cid!=0){
            query.setQuery("item_category_name:"+cid);
        }else {
            if (!StringUtils.isEmpty(context)) {
                query.setQuery(context);
            } else {
                query.setQuery("*:*");
            }
        }
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //设置默认的搜索域
        query.set("df","item_keywords");
        //设置高亮
        query.setHighlight(true);
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        query.addHighlightField("item_title");
        PageUtils utils=searchItems(query,context);
        //计算条目数
        long pageCount=utils .getRecordCount()/rows;
        if(utils.getRecordCount()%rows>0){
            pageCount++;
        }
        //设置计算的页数
        utils.setPageCount(pageCount);
        return utils;
    }

    @Override
    public long selectPriceByItemId(Long itemId) {
        return itemMapper.selectPriceByItemId(itemId);
    }

    private PageUtils searchItems(SolrQuery query,String context) throws SolrServerException {
        PageUtils utils=new PageUtils();
        SolrServer solrServer=new HttpSolrServer("http://192.168.25.128:8080/solr");
        QueryResponse response=solrServer.query(query);
        SolrDocumentList list=response.getResults();
        //设置结果总数
        utils.setRecordCount(list.getNumFound());
        List<ItemSearch> itemList=new ArrayList<ItemSearch>();
        //title取高亮
        Map<String, Map<String,List<String>>> highlighting=response.getHighlighting();
        for(SolrDocument document:list){
            //将查询结果封装入itemList中
            ItemSearch item=new ItemSearch();
            item.setId(Long.parseLong(document.get("id").toString()));
            item.setCategoryName(document.get("item_category_name").toString());
            item.setImage(document.get("item_image").toString());
            item.setPrice((Long)document.get("item_price"));
            item.setSellPoint(document.get("item_sell_point").toString());
            item.setTitle(document.get("item_title").toString());
            itemList.add(item);
        }
        utils.setItemList(itemList);
        return utils;
    }


}
