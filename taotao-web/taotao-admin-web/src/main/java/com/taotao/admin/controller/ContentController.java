package com.taotao.admin.controller;

import com.taotao.admin.pojo.Content;
import com.taotao.admin.service.ContentService;
import com.taotao.common.vo.DataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by myname on 2017/10/2.
 * 内容管理
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 分页查询
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @GetMapping
    public DataGridResult selectContentByCategoryId(
            @RequestParam(value = "categoryId",defaultValue = "0") Long categoryId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "10") Integer rows){

        try{
            return contentService.findContentByCategoryId(categoryId, page, rows);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 新增数据
     */
    @PostMapping("/save")
    public void saveContent(Content content){
        try {
            contentService.saveContent(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 修改
     */
    @PostMapping("update")
    public void updateContent(Content content){
        try {
            content.setUpdated(new Date());
            contentService.updateSelective(content);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除
     */
    @PostMapping("delete")
    public void deleteContent(@RequestParam("ids") String ids){
        try {
            contentService.deleteAll("id",ids.split(","));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
