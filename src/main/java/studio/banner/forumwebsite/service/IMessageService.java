package studio.banner.forumwebsite.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import studio.banner.forumwebsite.bean.Message;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2021/12/9 21:24
 * @role: 聊天服务类接口
 */
public interface IMessageService {

    /**
     * 查询通信用户列表
     *
     * @param userId 用户id
     * @param page   第几页
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> selectUserList(Integer userId, Integer page);

    /**
     * 将发送的消息保存到MongoDB
     *
     * @param userId  用户id
     * @param toId    被发送人微信
     * @param message 信息
     * @return Message
     */
    Message saveMessage(Integer userId, Integer toId, String message);

    /**
     * 查询点对点聊天记录
     *
     * @param formId 发送人id
     * @param toId   被发送人id
     * @param page   页数
     * @param rows   每页信息数
     * @return List<Message>
     */
    List<Message> findListByFromAndTo(Integer formId, Integer toId, Integer page, Integer rows);

    /**
     * 根据id查询数据
     *
     * @param id 信息id
     * @return Message
     */
    Message findMessageById(String id);

    /**
     * 更新消息状态
     *
     * @param id     信息id
     * @param status 信息状态
     * @return UpdateResult
     */
    UpdateResult updateMessageState(Object id, Integer status);

    /**
     * 新增消息
     *
     * @param message 信息
     * @return Message
     */
    Message saveMessage(Message message);

    /**
     * 根据消息id删除数据
     *
     * @param id 信息id
     * @return DeleteResult
     */
    DeleteResult deleteMessage(String id);

    /**
     * 查询历史消息
     *
     * @param fromId 发送人id
     * @param toId   被发送人id
     * @param page   页数
     * @param rows   每页信息数
     * @return List<Message>
     */
    List<Message> queryMessageList(Integer fromId, Integer toId, Integer page, Integer rows);

}
