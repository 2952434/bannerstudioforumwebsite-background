package studio.banner.forumwebsite.controller.background;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.ITencentYunService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Ljx
 * @Date: 2021/11/28 14:03
 * @role:
 */
@RestController
@Api(tags = "后台腾讯云上传图片接口", value = "BTencentPhotoController")
@RequestMapping("/backGround")
public class BTencentPhotoController {
    protected static final Logger logger = LoggerFactory.getLogger(BTencentPhotoController.class);
    @Autowired
    private ITencentYunService iTencentYunService;

    @RequestMapping(value = "/tencentPhotoBackGround/upload")
    @ApiOperation(value = "腾讯云上传接口", notes = "上传图片不能为空", httpMethod = "POST")
    public JSONObject upload(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {
        String url = iTencentYunService.upload(file);
        JSONObject res = new JSONObject();
        res.put("url", url);
        res.put("success", 1);
        res.put("message", "upload success!");
        return res;
    }

    @DeleteMapping("/tencentPhotoBackGround/delete")
    @ApiOperation(value = "腾讯云删除接口", httpMethod = "DELETE")
    @ApiImplicitParam(name = "fileName", value = "图片名", dataTypeClass = String.class)
    public RespBean delete(@RequestParam String fileName) {
        if (iTencentYunService.delete(fileName)) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
