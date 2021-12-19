package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import studio.banner.forumwebsite.bean.User;
import studio.banner.forumwebsite.bean.UserMsgBean;
import studio.banner.forumwebsite.mapper.UserMsgMapper;
import studio.banner.forumwebsite.service.IMessageService;

import java.util.*;

/**
 * @Author: Ljx
 * @Date: 2021/12/9 21:35
 * @role:
 */
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private UserMsgMapper userMsgMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IMessageService messageService;

    @Override
    public List<Map<String, Object>> selectUserList(Integer userId,Integer page) {
        List<UserMsgBean> list = userMsgMapper.selectList(null);
        List<Map<String,Object>> result = new ArrayList<>();
        for (UserMsgBean userMsgBean : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", userMsgBean.getMemberId());
            map.put("avatar", userMsgBean.getMemberHead());
            map.put("info_type", null);
            map.put("to_user", userMsgBean.getMemberId());
            map.put("username", userMsgBean.getMemberName());
            List<Message> messages = messageService.queryMessageList(userId,
                    userMsgBean.getMemberId(), page, 10);
            if (messages != null && !messages.isEmpty()) {
                Message message = messages.get(0);
                map.put("chat_msg", message.getMsg());
                map.put("chat_time", message.getSendDate().getTime());
            }
            result.add(map);
        }
        return result;
    }

    @Override
    public Message saveMessage(Integer userId, Integer toId, String msg) {
        QueryWrapper<UserMsgBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",userId);
        List<UserMsgBean> list = userMsgMapper.selectList(queryWrapper);
        QueryWrapper<UserMsgBean> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("member_id",toId);
        List<UserMsgBean> list1 = userMsgMapper.selectList(queryWrapper1);
        if (list.size()==0&&list1.size()==0){
            return null;
        }else {
            Message message = new Message();
            message.setId(ObjectId.get());
            message.setMsg(msg);
            message.setSendDate(new Date());
            message.setStatus(1);
            message.setFrom(new User(list.get(0).getMemberId(),list.get(0).getMemberName()));
            message.setTo(new User(list1.get(0).getMemberId(),list1.get(0).getMemberName()));
            return mongoTemplate.save(message);
        }
    }

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
        if (status == 1){
            update.set("send_date",new Date());
        }else if (status == 2){
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

    @Override
    public List<Message> queryMessageList(Integer fromId, Integer toId, Integer page, Integer rows) {
        List<Message> list = findListByFromAndTo(fromId, toId, page, rows);
        for (Message message : list) {
            if (message.getStatus().intValue() == 1){
                //修改信息状态为已读
                updateMessageState(message.getId(),2);
            }
        }
        return list;
    }
}
