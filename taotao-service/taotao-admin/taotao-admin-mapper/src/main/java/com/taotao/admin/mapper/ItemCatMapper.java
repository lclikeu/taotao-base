package com.taotao.admin.mapper;

import com.taotao.admin.pojo.ItemCat;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by myname on 2017/9/26.
 */
public interface ItemCatMapper extends Mapper<ItemCat> {

    /**
     * 根据父级ID查询商品类目
     * @param parentId
     * @return
     */
    @Select("select id, name as text, is_parent as state from" +
            " tb_item_cat where parent_id = #{parentId} order by id asc")
    List<Map<String ,Object>> selectItemCatParentId(Long parentId);
}
