package studio.banner.forumwebsite.config;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.storage.Configuration;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.path}")
    private String path;
    private String localFilePath = "D:\\photos\\1.jpg";
    private String key = null;
//    String fileName = "studio/wyf-study/qiniu.jpg";
//    String domainOfBucket = "http://devtools.qiniu.com";
private static final Logger logger = LoggerFactory.getLogger(QiNiuYunConfig.class);
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
//                String returnPath = getPath() + "/" + putRet.key;
                String returnPath="http://qtga4eaxm.hn-bkt.clouddn.com/"+putRet.key;
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean delete(String imageName) {
        Configuration cfg = new Configuration(Region.region2());
        cfg.useHttpsDomains = false;
        // 生成密钥
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            if (imageName!=null){
                bucketManager.delete(bucket,imageName);
                return true;
            }
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            logger.error(String.valueOf(ex.code()));
            logger.error(ex.response.toString());
        }
        return false;
    }
}

