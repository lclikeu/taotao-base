package com.taotao.admin.service;

import com.taotao.admin.pojo.Item;
import com.taotao.common.vo.DataGridResult;

/**
 * Created by myname on 2017/9/27.
 */
public interface ItemService extends BaseService<Item> {

    /**
     * 新增商品
     * @param item
     * @param desc
     */
    void saveItem(Item item, String desc);

    /**
     * 多条件分页查询
     */
    DataGridResult findByPageWhere(Item item,Integer page ,Integer rows);

    /**
     * 修改商品
     */
    void updateItem(Item item,String desc);
}
