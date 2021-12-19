package studio.banner.forumwebsite.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import studio.banner.forumwebsite.bean.Message;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2021/12/9 21:24
 * @role:
 */
public interface IMessageService {

    /**
     * 查询通信用户列表
     * @param userId
     * @param page
     * @return
     */
    List<Map<String,Object>> selectUserList(Integer userId,Integer page);

    /**
     * 将发送的消息保存到MongoDB
     * @param userId
     * @param toId
     * @param message
     * @return
     */
    Message saveMessage(Integer userId,Integer toId,String message);
    /**
     * 查询点对点聊天记录
     * @param formId
     * @param toId
     * @param page
     * @param rows
     * @return
     */
    List<Message> findListByFromAndTo(Integer formId,Integer toId,Integer page,Integer rows);

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Message findMessageById(String id);

    /**
     * 更新消息状态
     * @param id
     * @param status
     * @return
     */
    UpdateResult updateMessageState(Object id,Integer status);

    /**
     * 新增消息
     * @param message
     * @return
     */
    Message saveMessage(Message message);

    /**
     * 根据消息id删除数据
     * @param id
     * @return
     */
    DeleteResult deleteMessage(String id);

    /**
     * 查询历史消息
     * @param fromId
     * @param toId
     * @param page
     * @param rows
     * @return
     */
    List<Message> queryMessageList(Integer fromId, Integer toId, Integer page, Integer rows);

}
