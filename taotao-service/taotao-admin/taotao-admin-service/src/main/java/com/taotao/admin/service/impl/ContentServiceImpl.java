package com.taotao.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.admin.mapper.ContentMapper;
import com.taotao.admin.pojo.Content;
import com.taotao.admin.service.ContentService;
import com.taotao.common.vo.DataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * Created by myname on 2017/10/1.
 * 内容管理
 */
@Service
@Transactional
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    /**
     * 分页查询内容
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @Override
    public DataGridResult findContentByCategoryId(Long categoryId, Integer page, Integer rows) {

        Example example = new Example(Content.class);
        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();
        //添加查询条件
        criteria.andEqualTo("categoryId",categoryId);
        //设置分页查询
        PageHelper.startPage(page,rows);
        //根据条件查询
        PageInfo<Content> pageInfo = new PageInfo<>(contentMapper.selectByExample(example));
        DataGridResult dataGridResult = new DataGridResult();
        dataGridResult.setTotal(pageInfo.getTotal());
        dataGridResult.setRows(pageInfo.getList());

        return dataGridResult;
    }

    /**
     * 新增
     * @param content
     */
    @Override
    public void saveContent(Content content) {
        try {
            content.setCreated(new Date());
            content.setUpdated(content.getCreated());
            contentMapper.insertSelective(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
