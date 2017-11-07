package com.taotao.admin.service.impl;

import com.taotao.admin.mapper.ContentCategoryMapper;
import com.taotao.admin.pojo.ContentCategory;
import com.taotao.admin.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by myname on 2017/10/1.
 */
@Service
@Transactional(readOnly = false)
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory>
        implements ContentCategoryService{


    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    /**
     * 查询内容分类
     * @param parentId
     * @return
     */
    @Override
    public List<Map<String, Object>> findContentCategoryByParentId(Long parentId) {
        List<Map<String,Object>> contentCategorys = contentCategoryMapper.getContentCategoryByParentId(parentId);
        for (Map<String,Object> map : contentCategorys){
            boolean state = (boolean)map.get("state");
            map.put("state", state ? "closed" : "open");
        }
        return contentCategorys;
    }

    /**
     * 新增内容分类
     */
    @Override
    public Long saveContentCategory(ContentCategory contentCategory) {
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(contentCategory.getCreated());
        //调用mapper方法新增
        contentCategoryMapper.insertSelective(contentCategory);


        //更新父级分类
        ContentCategory c = new ContentCategory();
        c.setId(contentCategory.getId());
        c.setIsParent(true);
        c.setUpdated(new Date());
        //调用mapper方法修改
        contentCategoryMapper.updateByPrimaryKeySelective(c);
        return contentCategory.getId();
    }

    /**
     * 删除内容分类
     * @param contentCategory
     */
    @Override
    public void deleteContentCategory(ContentCategory contentCategory) {
        //定义集合封装需要删除的子节点
        List<Long> ids = new ArrayList<>();
        //添加自己的id
        ids.add(contentCategory.getId());
        //递归查询需要删除的子节点，并添加到集合中
        findLeafNode(ids,contentCategory.getId());
        //删除所有树节点
        this.deleteAll("id",ids.toArray((new Long[ids.size()])));

        //查询它的父节点对应的所有子节点
        ContentCategory cc = new ContentCategory();
        cc.setParentId(contentCategory.getParentId());
        //统计查询所有子节点数量
        int count = this.countByWhere(cc);
        if (count == 0){
            //创建父节点
            ContentCategory parent = new ContentCategory();
            parent.setId(contentCategory.getParentId());
            parent.setIsParent(false);
            parent.setUpdated(new Date());
            //修改父节点
            this.updateSelective(parent);
        }

    }

    /**
     * 递归查询添加子节点id
     * @param ids
     * @param id
     */
    private void findLeafNode(List<Long> ids, Long id) {

        //创建内容分类对象
        ContentCategory c = new ContentCategory();
        //添加查询条件
        c.setParentId(id);
        //根据父节点Id查询子节点
        List<ContentCategory> lists = this.findAllByWhere(c);
        if (lists != null && lists.size() > 0){
            for (ContentCategory list : lists) {
                //添加需要删除的id
                ids.add(list.getId());
                //递归查询子节点的所有子节点
                findLeafNode(ids,list.getId());
            }
        }

    }


}
