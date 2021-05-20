package studio.banner.forumwebsite.controller.background;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.config.QiNiuYunConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/15/10:29
 * @Description:七牛云接口
 */
@Api(tags = "七牛云接口",value = "QiNiuYunController")
@RestController
public class QiNiuYunController {

    @Autowired
    private QiNiuYunConfig qiNiuYunConfig;
    @PostMapping("/qiniu")
    public RespBean qiNiuYunUpload(@RequestParam("file") MultipartFile file,
                                   Model model) throws IOException {
        String filename = file.getOriginalFilename();
        FileInputStream inputStream = (FileInputStream) file.getInputStream();
        //为文件重命名：uuid+filename
        filename = UUID.randomUUID()+ filename;
        String link = qiNiuYunConfig.uploadImgToQiNiu(inputStream, filename);
        model.addAttribute("link",link);
        System.out.println(link);
        return RespBean.ok("图片上传成功",link);
    }
}
