package studio.banner.forumwebsite.controller.frontdesk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.service.IMessageService;

/**
 * @Author: Ljx
 * @Date: 2021/12/16 16:25
 * @role:
 */
@RestController
@CrossOrigin
public class MessageFrontDeskController {
    @Autowired
    private IMessageService messageService;


}
