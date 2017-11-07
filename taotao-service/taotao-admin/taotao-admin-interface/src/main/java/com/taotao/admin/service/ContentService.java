package com.taotao.admin.service;

import com.taotao.admin.pojo.Content;
import com.taotao.common.vo.DataGridResult;

/**
 * Created by myname on 2017/10/1.
 */
public interface ContentService extends BaseService<Content> {


    DataGridResult findContentByCategoryId(Long categoryId, Integer page, Integer rows);

    void saveContent(Content content);
}
