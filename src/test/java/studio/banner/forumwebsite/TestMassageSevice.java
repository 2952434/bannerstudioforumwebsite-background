package studio.banner.forumwebsite;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studio.banner.forumwebsite.bean.FixedInformationBean;
import studio.banner.forumwebsite.bean.Message;
import studio.banner.forumwebsite.bean.User;
import studio.banner.forumwebsite.mapper.FixedInformationMapper;
import studio.banner.forumwebsite.service.IMessageService;
import studio.banner.forumwebsite.service.IReplyService;
import studio.banner.forumwebsite.service.impl.MessageServiceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TestMassageSevice {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private FixedInformationMapper fixedInformationMapper;

    @Test
    public void test01(){
        List<FixedInformationBean> fixedInformationBeans = fixedInformationMapper.selectList(null);
        System.out.println(fixedInformationBeans);
        List<HashMap<String,String>> map = fixedInformationMapper.selectDirectionNum();
        System.out.println(map);
        List<HashMap<String, String>> hashMaps = fixedInformationMapper.selectDirectionPostNum();
        System.out.println(hashMaps.get(0).get("name"));

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