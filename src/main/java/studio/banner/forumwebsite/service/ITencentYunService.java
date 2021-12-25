package studio.banner.forumwebsite.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Author: Ljx
 * @Date: 2021/11/28 13:56
 * @role: 上传文件服务层接口
 */
public interface ITencentYunService {
    /**
     * 处理浏览器文件上传请求
     *
     * @param multipartFile 上传的文件
     * @return String
     */
    String upload(MultipartFile multipartFile);

    /**
     * 处理普通文件上传
     *
     * @param file 文件
     * @return String
     */
    String upload(File file);

    /**
     * 根据文件名删除文件
     *
     * @param fileName 文件名
     * @return boolean
     */
    boolean delete(String fileName);
}