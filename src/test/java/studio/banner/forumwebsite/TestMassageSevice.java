package studio.banner.forumwebsite;

import com.alibaba.fastjson.JSON;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.spring.web.json.Json;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.mapper.FixedInformationMapper;
import studio.banner.forumwebsite.mapper.MemberInformationMapper;
import studio.banner.forumwebsite.mapper.PostMapper;
import studio.banner.forumwebsite.mapper.UserContactMapper;
import studio.banner.forumwebsite.service.*;
import studio.banner.forumwebsite.service.impl.MessageServiceImpl;
import studio.banner.forumwebsite.service.impl.UserAttentionServiceImpl;

import java.util.*;

@SpringBootTest
public class TestMassageSevice {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private UserContactMapper userContactMapper;
    @Autowired
    private FixedInformationMapper fixedInformationMapper;
    @Autowired
    private ICollectService iCollectService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private IUserAttentionService iUserAttentionService;

    @Autowired
    private MemberInformationMapper memberInformationMapper;
    @Autowired
    private IPostEsService postEsService;
    @Test
    public void test01(){
//        List<FixedInformationBean> fixedInformationBeans = fixedInformationMapper.selectList(null);
//        System.out.println(fixedInformationBeans);
//        List<HashMap<String,String>> map = fixedInformationMapper.selectDirectionNum();
//        System.out.println(JSON.toJSONString(map));
//        List<HashMap<String, String>> hashMaps = fixedInformationMapper.selectDirectionPostNum();
//        System.out.println(hashMaps.get(0).get("name"));
//        List<String> list = fixedInformationMapper.selectUserGrade();
//        System.out.println(list);
//        List<Map<String, String>> maps = postMapper.selectListPost();
//        System.out.println(maps);
//
//        RespBean allWithPage = postEsService.findAllWithPage(1);
//        System.out.println(allWithPage.getObj());

//        RespBean type = postEsService.findByPostContentAndPostTitleAndPostType("李君祥", 1);
//        System.out.println(type.getObj());

PostBean postBean = new PostBean();
            postBean.setPostCommentNumber(1);
            postBean.setPostTime("123");
            postBean.setPostColNum(1);
            postBean.setPostPageView(12);
            postBean.setPostContent("dasfadad");
            postBean.setPostImageAddress("http");
            postBean.setPostTitle("你好");
            postBean.setPostMemberId(55);
            postBean.setPostType("Java,Python");
            postBean.setPostTop(0);
        for (int i = 0; i < 200; i++) {

            postMapper.insert(postBean);
        }

//        List<Map<String, String>> maps = postMapper.selectPostById(0);
//        System.out.println(maps);
//        PostBean postBean = new PostBean(maps.get(0));
//        System.out.println(postBean);

    }

    @Test
    public void testAttention(){
        UserAttentionBean userAttentionBean = new UserAttentionBean();
        userAttentionBean.setAttentionId(55);
        userAttentionBean.setBeAttentionId(197);
        userAttentionBean.setAttentionInformation("123");
        System.out.println(iUserAttentionService.insertContact(userAttentionBean));
        iUserAttentionService.deleteContact(55,197);
    }

    @Test
    public void testCollect(){
        System.out.println(iCollectService.judgeCollectPost(53, 1));
    }
    @Test
    public void testSave() {
        Message message = Message.builder()
                .id(ObjectId.get())
                .msg("你好")
                .sendDate(new Date())
                .status(1)
                .from(new User(100, "zhangshan"))
                .to(new User(101, "lishi"))
                .build();
        this.messageService.saveMessage(message);
        message = Message.builder()
                .id(ObjectId.get())
                .msg("你也好")
                .sendDate(new Date())
                .status(1)
                .from(new User(101, "lishi"))
                .to(new User(100, "zhangshan"))
                .build();
        this.messageService.saveMessage(message);
    }

    @Test
    public void testQueryList(){
        List<Message> listByFromAndTo = this.messageService.findListByFromAndTo(26, 25, 1, 10);
        for (Message message : listByFromAndTo) {
            System.out.println(message);
        }
    }
}