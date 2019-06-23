package org.java.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class InfController {

    @PostMapping("up")
    public String upload(@RequestParam("myfile") MultipartFile myfile) throws IOException {

        if(myfile.isEmpty()){
            return "上传失败";
        }

        String path="c:/j59";

        String filename = myfile.getOriginalFilename();

        File newfile=new File(path,filename);

        if(!newfile.getParentFile().exists()){
            newfile.getParentFile().mkdirs();
        }
        //将上传文件中的数据，写入到新文件中
        myfile.transferTo(newfile);
        return "ok";
    }

    @PostMapping("up2")
    public String upload2(HttpServletRequest req) throws IOException {

        //获得所有上传文件
        MultipartHttpServletRequest request = (MultipartHttpServletRequest) req;
        List<MultipartFile> files = request.getFiles("myfile");
        if(files.isEmpty()){
            return "多个文件上传失败";
        }

        String path="c:/j59";
        for(MultipartFile mf:files){
            //获得文件名称
            String filename = mf.getOriginalFilename();
            if(!mf.isEmpty()){
                File newFile=new File(path,filename);
                if(!newFile.getParentFile().exists()){
                    newFile.getParentFile().mkdirs();
                }
                //将上传文件中的数据，写入到新文件中
                mf.transferTo(newFile);
            }
        }
        return "多个文件上传成功";
    }
}
