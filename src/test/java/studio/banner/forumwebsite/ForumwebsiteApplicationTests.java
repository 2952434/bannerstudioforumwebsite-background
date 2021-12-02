package studio.banner.forumwebsite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studio.banner.forumwebsite.service.IReplyService;

@SpringBootTest
class ForumApplicationTests {
        @Autowired
        IReplyService iReplyService;
        @Test
        void updatePost() {

        }
    }