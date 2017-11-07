package com.taotao.admin.mapper;

import com.taotao.admin.pojo.Item;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by myname on 2017/9/27.
 */
public interface ItemMapper extends Mapper<Item> {

    /**
     * 多条件商品查询
     * @param item
     * @return
     */
    List<Map<String,Object>> findAll(@Param("item") Item item);
}
