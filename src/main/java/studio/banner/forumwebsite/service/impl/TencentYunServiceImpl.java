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
 * @role:
 */
@Service
public class TencentYunServiceImpl implements ITencentYunService {

    @Autowired
    private QCloudCosUtils qCloudCosUtils;

    @Override
    public String upload(MultipartFile multipartFile) {
        return qCloudCosUtils.upload(multipartFile);
    }

    @Override
    public String upload(File file) {
        return qCloudCosUtils.upload(file);
    }

    @Override
    public boolean delete(String fileName) {
        return qCloudCosUtils.deleteFile(fileName);
    }
}
