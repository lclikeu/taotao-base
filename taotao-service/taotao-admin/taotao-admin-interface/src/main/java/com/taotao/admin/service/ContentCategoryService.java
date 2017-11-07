package com.taotao.admin.service;

import com.taotao.admin.pojo.ContentCategory;

import java.util.List;
import java.util.Map;

/**
 * Created by myname on 2017/10/1.
 */
public interface ContentCategoryService extends BaseService<ContentCategory> {


    List<Map<String,Object>> findContentCategoryByParentId(Long parentId);

    Long saveContentCategory(ContentCategory contentCategory);

    void deleteContentCategory(ContentCategory contentCategory);
}
