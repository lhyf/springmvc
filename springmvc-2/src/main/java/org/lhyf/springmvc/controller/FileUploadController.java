package org.lhyf.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

    @RequestMapping("/file")
    public String upload(String desc, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        String filename = file.getOriginalFilename();
        String realPath = request.getServletContext().getRealPath("/tem/image");
        File fileSave = new File(realPath,filename);
        file.transferTo(fileSave);

        long size = file.getSize();
        System.out.println(size);
        return "success";
    }

    @RequestMapping("/files")
    public String MultipartFileupload(
            @RequestParam("file") MultipartFile[] file, HttpServletRequest request) throws IOException {

        return "success";
    }
}
