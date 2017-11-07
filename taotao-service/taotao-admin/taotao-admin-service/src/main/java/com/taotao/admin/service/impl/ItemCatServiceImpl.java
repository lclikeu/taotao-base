package com.taotao.admin.service.impl;

import com.taotao.admin.mapper.ItemCatMapper;
import com.taotao.admin.pojo.ItemCat;
import com.taotao.admin.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by myname on 2017/9/26.
 */
@Service
@Transactional(readOnly = false,rollbackFor = RuntimeException.class)
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 根据父类ID查询商品类目
     * @param parentId
     * @return
     */
    @Override
    public List<Map<String, Object>> selectItemCatParentId(Long parentId) {

        List<Map<String, Object>> maps = itemCatMapper.selectItemCatParentId(parentId);
        for (Map<String, Object> map : maps) {
            boolean state = (boolean) map.get("state");
            map.put("state",state ? "closed" : "open");
        }
        return maps;
    }
}
