package com.cb.web.zuitoutiao.controller;

import com.cb.web.zuitoutiao.service.UserService;
import com.cb.web.zuitoutiao.utils.FileUploadUtil;
import com.cb.web.zuitoutiao.utils.ImgCut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @Description: 负责上传头像
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-23 17:06
 **/
@CrossOrigin(origins = "http://localhost:8081")
@Controller
@ApiIgnore
@RequestMapping("/Upload")
public class UploadImgController {
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UploadImgController.class);

    @RequestMapping(value = "/uploadHeadImage", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public void uploadHeadImage(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "x") String x,
            @RequestParam(value = "y") String y,
            @RequestParam(value = "h") String h,
            @RequestParam(value = "w") String w,
            @RequestParam(value = "imgFile") MultipartFile imageFile
    ) throws Exception {
        logger.info("==========Start=============");
        logger.info("需要截取的坐标：X:>>" + x + ">>Y:>>" + y + ">>H:>>" + h + ">>W:>>" + w);
        String resourcePath = System.getProperty("user.dir") + "/src/main/resources/static/images/";
        if (imageFile != null) {
            if (FileUploadUtil.allowUpload(imageFile.getContentType())) {
                String fileName = FileUploadUtil.rename(imageFile.getOriginalFilename());
                int end = fileName.lastIndexOf(".");
                String saveName = fileName.substring(0, end);
                File dir = new File( resourcePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                logger.info("---文件保存目录--" + dir);
                File file = new File(dir, saveName + "_src.jpg");
                imageFile.transferTo(file);
                String srcImagePath =   resourcePath + saveName;
                int imageX = Integer.valueOf(x);
                int imageY = Integer.valueOf(y);
                int imageH = Integer.valueOf(h);
                int imageW = Integer.valueOf(w);
                //这里开始截取操作
                logger.info("==========imageCutStart=============");
                ImgCut.imgCut(srcImagePath, imageX, imageY, imageW, imageH);
                logger.info("==========imageCutEnd=============");
                logger.info("==========将数据持久化=============");
                userService.updateImage(id, "/images/" + saveName + "_cut.jpg");
            }
        }
    }
}

