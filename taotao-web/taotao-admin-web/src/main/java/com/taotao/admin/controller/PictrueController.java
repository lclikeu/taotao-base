package com.taotao.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by myname on 2017/9/29.
 * 文件上传
 */
@RestController
public class PictrueController {

    //注入分布式文件请求地址
    @Value("${fastdfsUrl}")
    private String fastdfsUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(path = "/pic/upload",produces = MediaType.TEXT_HTML_VALUE)
    public String upload(
            @RequestParam("pic") MultipartFile multipartFile ) throws Exception {

        Map<String ,Object> data = new HashMap<String,Object>();
        data.put("error",500);

        try {
            //加载配置文件，得到路径
            String conf_filename = this.getClass().getResource("/fastdfs_client.conf").getPath();

            //调用客户端全局类的初始化方法加载配置文件
            ClientGlobal.init(conf_filename);

            //创建存储客户对象
            StorageClient storageClient = new StorageClient();

            //上传文件到分布式文件系统
            String[] strings = storageClient.upload_file(multipartFile.getBytes(),
                    FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
            //拼接请求地址
            StringBuffer sb = new StringBuffer(fastdfsUrl);
            for (String string : strings) {
                sb.append("/"+string);
            }

            data.put("url",sb.toString());

            data.put("error",0);


        }catch (Exception e){
            e.getStackTrace();
        }
        return objectMapper.writeValueAsString(data);
    }
}
