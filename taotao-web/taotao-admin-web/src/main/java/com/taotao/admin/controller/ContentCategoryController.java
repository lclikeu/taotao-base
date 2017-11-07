package com.taotao.admin.controller;

import com.taotao.admin.pojo.ContentCategory;
import com.taotao.admin.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by myname on 2017/10/1.
 * 商品分类管理
 */
@RestController
@RequestMapping("/contentcategory")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 查询内容分类
     * @param parentId
     * @return
     */
    @GetMapping
    public List<Map<String ,Object>> selectContentCategoryByParentId(
            @RequestParam(value="id", defaultValue="0")Long parentId){
        try{
            return contentCategoryService.findContentCategoryByParentId(parentId);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 新增内容分类
     */
    @PostMapping("/save")
    public Long saveContentCategory(ContentCategory contentCategory){
        try{
            return contentCategoryService.saveContentCategory(contentCategory);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }


    /**
     * 修改内容分类
     */
    @PostMapping("/update")
    public void updateContentCategory(ContentCategory contentCategory){
        try {
            contentCategory.setUpdated(new Date());

            contentCategoryService.updateSelective(contentCategory);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 删除内容分类
     */
    @PostMapping("/delete")
    public void deleteContentCategory(ContentCategory contentCategory){
        try{
            contentCategoryService.deleteContentCategory(contentCategory);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
