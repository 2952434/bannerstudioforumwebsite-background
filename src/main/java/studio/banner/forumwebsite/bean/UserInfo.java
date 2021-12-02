package studio.banner.forumwebsite.bean;

/**
 * @Author: Ljx
 * @Date: 2021/11/19 8:07
 * @role:
 */
/**
 * 载荷对象
 */
public class UserInfo {

    private Long id;

    private String username;

    public UserInfo() {
    }

    public UserInfo(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}