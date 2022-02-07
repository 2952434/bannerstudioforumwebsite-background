package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.UserMsgBean;

import java.util.List;

/**
 * @Author: ljh
 * @Date: 2021/5/17 11:51
 * @role: 用户信息类接口
 */
public interface IUserMsgService {
    /**
     * 新增用户信息
     *
     * @param userMsgBean 用户信息实体
     * @return boolean
     */
    boolean insertUserMsg(UserMsgBean userMsgBean);

    /**
     * 根据Id更改用户昵称
     *
     * @param memberId      用户id
     * @param newMemberName 用户新姓名
     * @return boolean
     */
    boolean updateUserName(Integer memberId, String newMemberName);

    /**
     * 根据Id更改用户性别
     *
     * @param memberId  用户id
     * @param memberSex 用户性别
     * @return boolean
     */
    boolean updateUserSex(Integer memberId, String memberSex);

    /**
     * 根据Id更改用户年龄
     *
     * @param memberId  用户id
     * @param memberAge 用户年龄
     * @return boolean
     */
    boolean updateUserAge(Integer memberId, Integer memberAge);

    /**
     * 根据Id更改用户头像
     *
     * @param memberId   用户id
     * @param memberHead 头像地址
     * @return boolean
     */
    boolean updateUserHead(Integer memberId, String memberHead);


    /**
     * 根据Id查询用户信息
     *
     * @param memberId 用户id
     * @return UserMsgBean
     */
    UserMsgBean selectUserMsg(Integer memberId);


    /**
     * 根据用户id查询用户生日
     *
     * @param id 用户id
     * @return boolean
     */
    boolean selectBirthdayById(Integer id);

    /**
     * 查询过生日的人
     *
     * @param memberId 用户id
     * @return List
     */
    List<UserMsgBean> selectBirthday(Integer memberId);

    /**
     * 通过邮箱祝福过生日的人
     *
     * @param content  祝福内容
     * @param memberId 被祝福人的id
     * @return boolean
     */
    boolean blessUserBirthday(Integer memberId, String content);

    /**
     * 系统每天早上0点自动监测过生日的人并发送祝福邮件
     */
    void automaticSentMail();

    /**
     * 根据用户id更改个性签名
     *
     * @param memberId  用户id
     * @param signature 个性签名
     * @return boolean
     */
    boolean updateUserSignature(Integer memberId, String signature);

    /**
     * 根据id查询用户是否存在
     *
     * @param memberId 用户id
     * @return UserMsgBean
     */
    UserMsgBean selectUserById(Integer memberId);
}
