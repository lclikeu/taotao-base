package com.taotao.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


/**
 * Created by myname on 2017/10/6.
 * RESTful接口：增删改查
 */
@Controller
@RequestMapping("/item/restful")
public class ItemRestfulController {

    @Autowired
    private ItemService itemService;

    // 定义签名密钥 32长度(用MD5加密)
    private static final String SIGN_KEY = "04b29480233f4def5c875875b6bdc3b1";

    private ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 保存商品
     * @param
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveItem(@RequestBody Map<String,String> map){

        try {
            //获取签名参数
            String sign = map.remove("sign");
            //判断签名
            if (sign.equals(DigestUtils.md5Hex(SIGN_KEY + objectMapper.writeValueAsString(map)))) {
                Item item = objectMapper.readValue(objectMapper.writeValueAsBytes(map),Item.class);
                item.setCreated(new Date());
                item.setUpdated(item.getCreated());
                itemService.saveSelective(item);
                return ResponseEntity.status(HttpStatus.CREATED).body(null);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        //添加失败放回状态
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> selectItem(@PathVariable("id") Long id,@RequestParam("sign") String sign){
        try {
            //判断请求签名是否正确
            if (sign.equals(DigestUtils.md5Hex(SIGN_KEY + id))) {
                //判断参数是否存在
                if (id != null && id > 0) {
                    Item item = itemService.findOne(id);
                    if (item != null) {
                        //查询出来数据不为空，返回状态200
                        return ResponseEntity.ok(item);
                    } else {
                        //为空返回，404
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                    }
                }
            }
        }catch (Exception e){

        }
        //添加失败放回状态500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 修改商品
     */
    @PutMapping
    public ResponseEntity<Void> updateItem(@RequestBody Map<String,String> map){
        try {
            //获取签名参数
            String sign = map.remove("sign");
            //判断签名
            if (sign.equals(DigestUtils.md5Hex(SIGN_KEY + objectMapper.writeValueAsString(map)))){
                Item item = objectMapper.readValue(objectMapper.writeValueAsBytes(map),Item.class);
                //调用业务层完成修改
                item.setUpdated(new Date());
                itemService.updateSelective(item);
                //跟新成功返回状态 204（操作成功，没有信息返回）
                return ResponseEntity.noContent().build();
            }


        }catch (Exception e){

        }
        //修改失败返回状态500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    /**
     *删除
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteItem(@RequestParam("ids") Long[] ids,@RequestParam("sign") String sign){
        try {
            //判断签名
            if (sign.equals(DigestUtils.md5Hex(SIGN_KEY + ids))) {
                //判断id是否存在
                if (ids != null && ids.length > 0) {
                    //调用业务层完成删除功能
                    itemService.deleteAll("id", ids);
                    //跟新成功返回状态 204（操作成功，没有信息返回）
                    return ResponseEntity.noContent().build();
                }
            }
            //id不存在,返回400（请求参数不正确）
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }catch (Exception e){

        }
        //修改失败返回状态500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
