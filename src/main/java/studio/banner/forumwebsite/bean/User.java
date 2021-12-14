package studio.banner.forumwebsite.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jijunxiang
 * test websocket
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Integer id;
    private String username;

}
