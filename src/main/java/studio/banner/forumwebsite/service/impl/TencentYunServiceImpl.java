package studio.banner.forumwebsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import studio.banner.forumwebsite.service.ITencentYunService;
import studio.banner.forumwebsite.utils.QCloudCosUtils;

import java.io.File;

/**
 * @Author: Ljx
 * @Date: 2021/11/28 13:57
 * @role: 上传文件服务层实现
 */
@Service
public class TencentYunServiceImpl implements ITencentYunService {

    @Autowired
    private QCloudCosUtils qCloudCosUtils;

    /**
     * 处理浏览器文件上传请求
     *
     * @param multipartFile 上传的文件
     * @return String
     */
    @Override
    public String upload(MultipartFile multipartFile) {
        return qCloudCosUtils.upload(multipartFile);
    }

    /**
     * 处理普通文件上传
     *
     * @param file 文件
     * @return String
     */
    @Override
    public String upload(File file) {
        return qCloudCosUtils.upload(file);
    }

    /**
     * 根据文件名删除文件
     *
     * @param fileName 文件名
     * @return boolean
     */
    @Override
    public boolean delete(String fileName) {
        return qCloudCosUtils.deleteFile(fileName);
    }
}
