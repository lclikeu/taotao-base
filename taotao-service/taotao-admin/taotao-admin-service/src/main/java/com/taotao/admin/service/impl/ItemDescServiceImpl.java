package com.taotao.admin.service.impl;

import com.taotao.admin.mapper.ItemDescMapper;
import com.taotao.admin.pojo.ItemDesc;
import com.taotao.admin.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by myname on 2017/9/27.
 */
@Service
@Transactional(readOnly = false)
public class ItemDescServiceImpl extends BaseServiceImpl<ItemDesc> implements ItemDescService {

    @Autowired
    private ItemDescMapper itemDecMapper;
}
