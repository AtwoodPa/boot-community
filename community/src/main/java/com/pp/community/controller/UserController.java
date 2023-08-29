package com.pp.community.controller;

import com.pp.community.annotation.LoginRequired;
import com.pp.community.entity.User;
import com.pp.community.service.UserService;
import com.pp.community.utils.CommunityUtil;
import com.pp.community.utils.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/29 10:57
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    // 文件上传路径
    @Value("${community.path.upload}")
    private String uploadPath;
    // 域名地址
    @Value("${community.path.domain}")
    private String domain;
    // 项目访问路径
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    @LoginRequired// 表明该方法必须登录才可以使用
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }
    @LoginRequired// 表明该方法必须登录才可以使用
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model){
        if (headerImage == null){
            model.addAttribute("error","您还没有选择图片！");
            return "/site/setting";
        }
        // 执行上传
        String fileName = headerImage.getOriginalFilename();
        // 1获取图片的后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件的格式不正确！！");
            return "/site/setting";
        }
        // 2生成随机文件名
        String newFileName = CommunityUtil.generateUUID() + suffix;
        // 3确定文件存放的路径
        File dest = new File(uploadPath+"/"+newFileName);
        try {
            // 存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败："+e.getMessage());
            throw new RuntimeException("上传文件失败："+e.getMessage());
        }
        // 更新当前用户的头像的路径（Web访问路径）
        // http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain +contextPath + "/user/header/" + newFileName;
        userService.updateHeader(user.getId(),headerUrl);

        return "redirect:/index";

    }
    @RequestMapping(value = "/header/{fileName}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response){
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 响应图片
        response.setContentType("image/"+suffix);
        try(
                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(fileName)
                ) {
            // 创建文件输出流
            byte[] buffer = new byte[1024];
            int len = 0;
            // fis.read(buffer) -> 不断得从文件中读取数据
            while ((len = fis.read(buffer)) != -1){
                // 通过output对象，将图片数据写出到浏览器
                os.write(buffer,0,len);
            }
        } catch (IOException e) {
            logger.error("读取头像失败："+e.getMessage());
            throw new RuntimeException(e);
        }


    }


    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String getProfilePage() {
        return "/site/profile";
    }
}
