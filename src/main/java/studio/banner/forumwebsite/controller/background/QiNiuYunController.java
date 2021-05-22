package studio.banner.forumwebsite.controller.background;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.manager.QiNiuYunManager;


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
    private QiNiuYunManager qiNiuYunManager;
    @PostMapping("/qiniu")
    public RespBean qiNiuYunUpload(@RequestParam("file") MultipartFile file,
                                   Model model){
        String link = qiNiuYunManager.uploadImg(file);
        model.addAttribute("link",link);
        System.out.println(link);
        return RespBean.ok("上传图片成功",link);
    }
//    @GetMapping("/test")
//    public  ModelAndView qiniutest(){
//        ModelAndView modelAndView=new ModelAndView();
//        modelAndView.setViewName("upload");
//        return modelAndView;
//    }

}