package studio.banner.forumwebsite.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * 浏览器发送给服务器的websocket数据.
 * @author jijunxiang
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "message")
@Builder
public class Message {
    /**
     * 消息id
     */
    @Id
    private ObjectId id;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 消息状态，1-未读，2-已读
     */
    @Indexed
    private Integer status;
    /**
     * 发送消息的时间
     */
    @Field("send_date")
    @Indexed
    private Date sendDate;
    /**
     * 阅读消息的时间
     */
    @Field("read_date")
    private Date readDate;
    /**
     * 发送消息的人
     */
    @Indexed
    private User from;
    /**
     * 接收消息的人
     */
    @Indexed
    private User to;

}
