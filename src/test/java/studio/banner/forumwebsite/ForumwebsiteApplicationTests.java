package studio.banner.forumwebsite;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studio.banner.forumwebsite.bean.CommentBean;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IPostService;
import studio.banner.forumwebsite.service.impl.CommentServiceImpl;
import studio.banner.forumwebsite.service.impl.PostServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@SpringBootTest
class ForumApplicationTests {
        @Autowired
        ICommentService iCommentService;
        @Test
        void updatePost() {
            SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     // 北京
            bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));  // 设置北京时区
            Date date = new Date();
            //iCommentService.insertComment(new CommentBean(1, 3, 1, 0,"太棒了！！！",bjSdf.format(date)));
            iCommentService.deleteAllCommnetByPostId(1);


//    public void testFileUpload() {
//        //构造一个带指定Zone对象的配置类
//        Configuration cfg = new Configuration(Zone.zone0());
//        //...其他参数参考类注释
//        UploadManager uploadManager = new UploadManager(cfg);
//        //...生成上传凭证，然后准备上传
//        String accessKey = "1UMawCZEuScLwWXPXX9IRz33HGMi-BXfXwGMuaZY";//当前用户的访问凭证
//        String secretKey = "w8pK83Y7RtghmtVFrAfhNE1EC1lvQJoE0C7Q8HHA";//当前用户的访问凭证
//        String bucket = "saas-export-ee88";//存储空间名称
//        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
//        String localFilePath = "E:\\学习总结.jnp"; //上传文件路径
//        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = null;
//
//        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
//
//        try {
//            Response response = uploadManager.put(localFilePath, key, upToken);
//            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
//        } catch (QiniuException ex) {
//            Response r = ex.response;
//            System.err.println(r.toString());
//            try {
//                System.err.println(r.bodyString());
//            } catch (QiniuException ex2) {
//                //ignore
//            }
//            }
//
//    }
        }
    }