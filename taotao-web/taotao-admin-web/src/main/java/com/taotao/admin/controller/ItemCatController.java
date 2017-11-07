package com.taotao.admin.controller;

import com.taotao.admin.pojo.ItemCat;
import com.taotao.admin.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by myname on 2017/9/26.
 * 商品类目
 */
@RestController
@RequestMapping("/itemcat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 分页查询商品类目
     */
    @GetMapping("/{page}")
    public List<ItemCat> selectItemCatByPage(
            @PathVariable("page") Integer page,
            @RequestParam(value="rows", defaultValue="20")Integer rows) {

            return itemCatService.findByPage(page, rows);

    }


    /**
     * 根据父级ID查询商品类目子节点
     */
    @GetMapping
    public List<Map<String,Object>> selectItemCatParentId(
            @RequestParam(value = "id",defaultValue = "0") Long parentId){

            return itemCatService.selectItemCatParentId(parentId);
    }
}
