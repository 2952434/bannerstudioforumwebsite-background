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
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.Message;
import studio.banner.forumwebsite.bean.User;
import studio.banner.forumwebsite.mapper.MemberInformationMapper;
import studio.banner.forumwebsite.service.IMessageService;

import java.util.*;

/**
 * @Author: Ljx
 * @Date: 2021/12/9 21:35
 * @role: 聊天服务类实现
 */
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MemberInformationMapper memberInformationMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IMessageService messageService;

    /**
     * 查询通信用户列表
     *
     * @param userId 用户id
     * @param page   第几页
     * @return List<Map < String, Object>>
     */
    @Override
    public List<Map<String, Object>> selectUserList(Integer userId, Integer page) {
        List<MemberInformationBean> list = memberInformationMapper.selectList(null);
        List<Map<String, Object>> result = new ArrayList<>();
        for (MemberInformationBean memberInformationBean : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", memberInformationBean.getMemberId());
            map.put("avatar", memberInformationBean.getMemberHead());
            map.put("info_type", null);
            map.put("to_user", memberInformationBean.getMemberId());
            map.put("username", memberInformationBean.getMemberName());
            List<Message> messages = messageService.queryMessageList(userId,
                    memberInformationBean.getMemberId(), page, 10);
            if (messages != null && !messages.isEmpty()) {
                Message message = messages.get(0);
                map.put("chat_msg", message.getMsg());
                map.put("chat_time", message.getSendDate().getTime());
            }
            result.add(map);
        }
        return result;
    }

    /**
     * 将发送的消息保存到MongoDB
     *
     * @param userId  用户id
     * @param toId    被发送人微信
     * @param msg 信息
     * @return Message
     */
    @Override
    public Message saveMessage(Integer userId, Integer toId, String msg) {
        QueryWrapper<MemberInformationBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", userId);
        List<MemberInformationBean> list = memberInformationMapper.selectList(queryWrapper);
        QueryWrapper<MemberInformationBean> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("member_id", toId);
        List<MemberInformationBean> list1 = memberInformationMapper.selectList(queryWrapper1);
        if (list.size() == 0 && list1.size() == 0) {
            return null;
        } else {
            Message message = new Message();
            message.setId(ObjectId.get());
            message.setMsg(msg);
            message.setSendDate(new Date());
            message.setStatus(1);
            message.setFrom(new User(list.get(0).getMemberId(), list.get(0).getMemberName()));
            message.setTo(new User(list1.get(0).getMemberId(), list1.get(0).getMemberName()));
            return mongoTemplate.save(message);
        }
    }

    /**
     * 查询点对点聊天记录
     *
     * @param formId 发送人id
     * @param toId   被发送人id
     * @param page   页数
     * @param rows   每页信息数
     * @return List<Message>
     */
    @Override
    public List<Message> findListByFromAndTo(Integer formId, Integer toId, Integer page, Integer rows) {
        Criteria criteriaFrom = Criteria.where("from_id").is(formId).and("to_id").is(toId);
        Criteria criteriaTo = Criteria.where("from_id").is(toId).and("to_id").is(formId);

        Criteria criteria = new Criteria().orOperator(criteriaFrom, criteriaTo);
        PageRequest pageRequest = PageRequest.of(page - 1, rows, Sort.by(Sort.Direction.ASC, "send_date"));
        //设置查询条件，分页
        Query query = Query.query(criteria).with(pageRequest);
//        System.out.println(query);
        return this.mongoTemplate.find(query, Message.class);
    }

    /**
     * 根据id查询数据
     *
     * @param id 信息id
     * @return Message
     */
    @Override
    public Message findMessageById(String id) {
        return this.mongoTemplate.findById(new ObjectId(id), Message.class);
    }

    /**
     * 更新消息状态
     *
     * @param id     信息id
     * @param status 信息状态
     * @return UpdateResult
     */
    @Override
    public UpdateResult updateMessageState(Object id, Integer status) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.update("status", status);
        if (status == 1) {
            update.set("send_date", new Date());
        } else if (status == 2) {
            update.set("read_date", new Date());
        }
        return this.mongoTemplate.updateFirst(query, update, Message.class);
    }

    /**
     * 新增消息
     *
     * @param message 信息
     * @return Message
     */
    @Override
    public Message saveMessage(Message message) {
        //写入当前时间
        message.setSendDate(new Date());
        message.setStatus(1);
        return this.mongoTemplate.save(message);
    }

    /**
     * 根据消息id删除数据
     *
     * @param id 信息id
     * @return DeleteResult
     */
    @Override
    public DeleteResult deleteMessage(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return this.mongoTemplate.remove(query, Message.class);
    }

    /**
     * 查询历史消息
     *
     * @param fromId 发送人id
     * @param toId   被发送人id
     * @param page   页数
     * @param rows   每页信息数
     * @return List<Message>
     */
    @Override
    public List<Message> queryMessageList(Integer fromId, Integer toId, Integer page, Integer rows) {
        List<Message> list = findListByFromAndTo(fromId, toId, page, rows);
        for (Message message : list) {
            if (message.getStatus().intValue() == 1) {
                //修改信息状态为已读
                updateMessageState(message.getId(), 2);
            }
        }
        return list;
    }
}
