package studio.banner.forumwebsite.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
<<<<<<< HEAD
import org.springframework.ui.Model;
=======
>>>>>>> f411588d9a883a91d17700e331f33bfb8f0e9c56
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import studio.banner.forumwebsite.config.QiNiuYunConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: Ljx
 * @Date: 2021/5/22 9:24
 */
@Component
public class QiNiuYunManager {

    @Autowired
    private QiNiuYunConfig qiNiuYunConfig;
<<<<<<< HEAD
    public String uploadImg(@RequestParam("file") MultipartFile file){
=======

    public String uploadImg(@RequestParam("file") MultipartFile file) {
>>>>>>> f411588d9a883a91d17700e331f33bfb8f0e9c56
        String filename = file.getOriginalFilename();
        FileInputStream inputStream = null;
        try {
            inputStream = (FileInputStream) file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //为文件重命名：uuid+filename
<<<<<<< HEAD
        filename = UUID.randomUUID()+ filename;
        String link = qiNiuYunConfig.uploadImgToQiNiu(inputStream, filename);
        return link;
    }
}
=======
        filename = UUID.randomUUID() + filename;
        String link = qiNiuYunConfig.uploadImgToQiNiu(inputStream, filename);
        return link;
    }
}
>>>>>>> f411588d9a883a91d17700e331f33bfb8f0e9c56
