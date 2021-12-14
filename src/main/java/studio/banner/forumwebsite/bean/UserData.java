package studio.banner.forumwebsite.bean;


import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2021/12/10 14:43
 * @role:
 */
public class UserData {
    public static final Map<Integer, User> USER_MAP = new HashMap<>();

    static {
        USER_MAP.put(100,User.builder().id(100).username("zhangshan").build());
        USER_MAP.put(101,User.builder().id(101).username("lishi").build());
        USER_MAP.put(102,User.builder().id(102).username("wangwu").build());
        USER_MAP.put(103,User.builder().id(103).username("zhaoliu").build());
    }
}
