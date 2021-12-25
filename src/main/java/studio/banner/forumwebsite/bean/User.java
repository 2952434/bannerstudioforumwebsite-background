package studio.banner.forumwebsite.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jijunxiang
 * test websocket
 * 信息联系人实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    /**
     * 发送人id（接收人id）
     */
    private Integer id;
    /**
     * 发送人名字（接收人名字）
     */
    private String username;

}
