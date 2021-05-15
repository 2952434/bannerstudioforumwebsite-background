package studio.banner.forumwebsite.config;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.storage.Configuration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/15/9:55
 * @Description:七牛云配置文件
 */

@Data
@Component
public class QiNiuYunConfig {
    @Value("1UMawCZEuScLwWXPXX9IRz33HGMi-BXfXwGMuaZY")
    private String accessKey;
    @Value("w8pK83Y7RtghmtVFrAfhNE1EC1lvQJoE0C7Q8HHA")
    private String secretKey;
    /**
     * 存储空间名称
     */
    @Value("")
    private String bucket;
    /**
     * 上传文件路径
     */
    @Value("")
    private String localFilePath = "E:\\学习总结.png";
    private String key = null;
    public String uploadImgToQiNiu(FileInputStream file, String filename)  {
        // 构造一个带指定Zone对象的配置类，注意后面的zone各个地区不一样的
        Configuration cfg = new Configuration(Region.region2());
        cfg.useHttpsDomains = false;
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成密钥
        Auth auth = Auth.create(accessKey, secretKey);
        try {
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(file, filename, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                // 这个returnPath是获得到的外链地址,通过这个地址可以直接打开图片
                String returnPath="----"+putRet.key;
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
