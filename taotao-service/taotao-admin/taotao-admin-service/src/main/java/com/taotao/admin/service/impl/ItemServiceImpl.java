package com.taotao.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.admin.mapper.ItemDescMapper;
import com.taotao.admin.mapper.ItemMapper;
import com.taotao.admin.pojo.Item;
import com.taotao.admin.pojo.ItemDesc;
import com.taotao.admin.service.ItemService;
import com.taotao.common.vo.DataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * Created by myname on 2017/9/27.
 */
@Service
@Transactional(readOnly = false,rollbackFor = RuntimeException.class)
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService{


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    /**
     * 新增商品
     * @param item
     * @param desc
     */
    @Override
    public void saveItem(Item item, String desc) {

        try {
            //设置创建时间
            item.setCreated(new Date());
            //设置跟新时间
            item.setUpdated(new Date());
            //设置商品状态
            item.setStatus(1);
            //保存商品
            itemMapper.insertSelective(item);

            //创建商品描述对象
            ItemDesc itemDesc = new ItemDesc();
            //设置创建时间
            itemDesc.setCreated(new Date());
            //设置跟新时间
            itemDesc.setUpdated(itemDesc.getCreated());
            //设置商品描述信息
            itemDesc.setItemDesc(desc);
            //设置商品id
            itemDesc.setItemId(item.getId());
            //保存描述信息
            itemDescMapper.insertSelective(itemDesc);

        }catch (Exception e){
            //保存不成功事务回滚
            throw new RuntimeException(e);
        }
    }

    /**
     * 多条件分页查询
     * @param item
     * @param page
     * @param rows
     * @return
     */
    @Override
    public DataGridResult findByPageWhere(Item item, Integer page, Integer rows) {

        try {
            //开始分页
            PageHelper.startPage(page, rows);
            //封装数据
            PageInfo<Map<String, Object>> pageInfo =
                    new PageInfo<Map<String, Object>>(itemMapper.findAll(item));

            //创建DataGridResult封装最终需要的数据
            DataGridResult dataGridResult = new DataGridResult();
            //设置总记录数
            dataGridResult.setTotal(pageInfo.getTotal());

            //设置表格中所有的行对应的数据
            dataGridResult.setRows(pageInfo.getList());
            return dataGridResult;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改商品
     * @param item
     * @param desc
     */
    @Override
    public void updateItem(Item item, String desc) {
        //修改商品信息
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKeySelective(item);

        //修改商品描述信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setUpdated(new Date());
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
    }
}
