package studio.banner.forumwebsite.controller.background;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.config.QiNiuYunConfig;
import studio.banner.forumwebsite.manager.QiNiuYunManager;

import java.io.IOException;
/**
 * Created with IntelliJ IDEA.
 * @Author: HYK
 * @Date: 2021/05/15/10:29
 * @Description:七牛云接口
 */
@Api(tags = "七牛云接口",value = "QiNiuYunController")
@RestController
public class QiNiuYunController {
    @Autowired
    private QiNiuYunManager qiNiuYunManager;
    /**
     * 七牛云上传图片
     * @param file
     * @param model
     * @return RespBean
     * @throws IOException
     */
    @PostMapping("/qiniu")
    public RespBean qiNiuYunUpload(@RequestParam("file") MultipartFile file,
                                 Model model) throws IOException {
        String link = qiNiuYunManager.uploadImg(file);
        model.addAttribute("link",link);
        System.out.println(link);
        return RespBean.ok("上传图片成功",link);
    }
    @DeleteMapping("/delete")
    public RespBean qiNiuYunDelete(String imageAddress){
        QiNiuYunConfig config = new QiNiuYunConfig();
        if (config.delete(imageAddress) == true){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


}