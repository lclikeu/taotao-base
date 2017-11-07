package com.taotao.admin.mapper;

import com.taotao.admin.pojo.ContentCategory;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by myname on 2017/10/1.
 */
public interface ContentCategoryMapper extends Mapper<ContentCategory> {

    @Select("SELECT id,name as text, is_parent as state, parent_id as parentId FROM "
            + "`tb_content_category` where parent_id = #{parentId} order by id asc")
    List<Map<String,Object>> getContentCategoryByParentId(Long parentId);
}
