package studio.banner.forumwebsite.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.Message;
import studio.banner.forumwebsite.service.IMessageService;

import java.util.Date;
import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/9 21:35
 * @role:
 */
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 实现点对点消息查询
     * @param formId
     * @param toId
     * @param page
     * @param rows
     * @return
     */
    @Override
    public List<Message> findListByFromAndTo(Integer formId, Integer toId, Integer page, Integer rows) {
        Criteria criteriaFrom = Criteria.where("from_id").is(formId).and("to_id").is(toId);
        Criteria criteriaTo = Criteria.where("from_id").is(toId).and("to_id").is(formId);

        Criteria criteria = new Criteria().orOperator(criteriaFrom,criteriaTo);
        PageRequest pageRequest = PageRequest.of(page - 1, rows, Sort.by(Sort.Direction.ASC, "send_date"));
        //设置查询条件，分页
        Query query = Query.query(criteria).with(pageRequest);
//        System.out.println(query);
        return this.mongoTemplate.find(query,Message.class);
    }

    @Override
    public Message findMessageById(String id) {
        return this.mongoTemplate.findById(new ObjectId(id),Message.class);
    }

    @Override
    public UpdateResult updateMessageState(Object id, Integer status) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.update("status", status);
        if (status.intValue() == 1){
            update.set("send_date",new Date());
        }else if (status.intValue()==2){
            update.set("read_date", new Date());
        }
        return this.mongoTemplate.updateFirst(query,update,Message.class);
    }

    @Override
    public Message saveMessage(Message message) {
        //写入当前时间
        message.setSendDate(new Date());
        message.setStatus(1);
        return this.mongoTemplate.save(message);
    }

    @Override
    public DeleteResult deleteMessage(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return this.mongoTemplate.remove(query,Message.class);
    }
}
