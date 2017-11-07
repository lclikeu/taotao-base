package com.taotao.admin.service;

import com.taotao.admin.pojo.ItemCat;

import java.util.List;
import java.util.Map;

/**
 * Created by myname on 2017/9/26.
 */
public interface ItemCatService extends BaseService<ItemCat> {

    List<Map<String,Object>> selectItemCatParentId(Long parentId);
}