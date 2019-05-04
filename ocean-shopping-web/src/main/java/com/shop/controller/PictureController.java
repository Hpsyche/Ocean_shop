package com.shop.controller;

import com.shop.controller.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hpsyche
 */
@Controller
public class PictureController {
    @Value("${OS_IMAGE_SERVER_URL}")
    private String OS_IMAGE_SERVER_URL;

    /**
     * 上传图片入服务器
     */
    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map fileUpload(MultipartFile uploadFile){
        //1.取文件的拓展名
        String originalFileName=uploadFile.getOriginalFilename();
        String extName=originalFileName.substring(originalFileName.lastIndexOf(".")+1);
        //2.创建一个FastDFS的客户端
        try {
            FastDFSClient fastDFSClient=new FastDFSClient("classpath:resource/client.conf");
            //3.执行上传处理
            String path=fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
            //4.拼接返回的url和ip地址，拼装成完整的url
            String url=OS_IMAGE_SERVER_URL+path;
            //5.返回url
            Map result=new HashMap();
            result.put("error",0);
            result.put("url",url);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //5、返回map
            Map result = new HashMap<>();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return result;
        }
    }
}
