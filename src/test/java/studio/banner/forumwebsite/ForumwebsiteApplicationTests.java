package studio.banner.forumwebsite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studio.banner.forumwebsite.bean.CollectBean;
import studio.banner.forumwebsite.bean.UserBean;
import studio.banner.forumwebsite.service.ICollectService;
import studio.banner.forumwebsite.service.IUserService;

@SpringBootTest
class ForumwebsiteApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICollectService iCollectService;

    /**
     * 测试增加用户
     */
//    @Test
//    void insertUser(){
//        iUserService.insertUser(new UserBean(null, 123445,"asdfaf"));
//    }

    /**
     * 测试增加收藏
     */
    @Test
    void insertCollect(){
        iCollectService.insertCollect(new CollectBean(null,1,"ahfah",2));
    }

    /**
     *测试删除收藏
     */
    @Test
    void deleteCollect(){
        iCollectService.deleteCollect(2);
    }

    @Test
    void selectCollect(){
        iCollectService.selectCollectByUserId(3);
    }
}

