package com.taotao.admin.controller;

import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import com.taotao.common.vo.DataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by myname on 2017/9/27.
 */
@RestController
@RequestMapping("/item")
public class itemController {

    @Autowired
    private ItemService itemService;

    /**
     * 新增商品功能
     */
    @PostMapping("/save")
    public void saveItem(Item item, @RequestParam("desc") String desc){
        try {
            itemService.saveItem(item,desc);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 分页查询商品
     */
    @GetMapping
    public DataGridResult selectItem(Item item,
                         @RequestParam("page") Integer page,
                         @RequestParam("rows") Integer rows) throws UnsupportedEncodingException {

        try{
            if (item != null &&
                    !StringUtils.isEmpty(item.getTitle())){
                item.setTitle(URLDecoder.decode(item.getTitle(), "utf-8"));
            }
            return itemService.findByPageWhere(item,page,rows);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 修改商品
     */
    @PostMapping("/update")
    public void updateItem(Item item,@RequestParam("desc") String desc){
        itemService.updateItem(item,desc);
    }
}
