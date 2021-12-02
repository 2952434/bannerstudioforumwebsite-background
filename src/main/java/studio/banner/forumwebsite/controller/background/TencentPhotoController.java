package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.ITencentYunService;

/**
 * @Author: Ljx
 * @Date: 2021/11/28 14:03
 * @role:
 */
@RestController
@Api(tags = "腾讯云上传接口", value = "TencentPhotoController")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class TencentPhotoController {
    protected static final Logger logger = LoggerFactory.getLogger(TencentPhotoController.class);
    @Autowired
    private ITencentYunService iTencentYunService;
    @RequestMapping(value = "/upload" )
    @ApiOperation(value = "腾讯云上传接口",notes = "上传图片不能为空",httpMethod = "POST")
    public RespBean upload(@RequestPart MultipartFile file) {
        String url = iTencentYunService.upload(file);
        return RespBean.ok("上传成功",url);
    }
    @DeleteMapping("/delete")
    @ApiOperation(value = "腾讯云删除接口",httpMethod = "DELETE")
    @ApiImplicitParam(name = "fileName",value = "图片名",dataTypeClass = String.class)
    public RespBean delete(@RequestParam String  fileName) {
        if (iTencentYunService.delete(fileName)){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}

