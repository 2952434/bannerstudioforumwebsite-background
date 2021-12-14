package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.UserMsgBean;

import java.util.List;

/**
 * @Author: ljh
 * @Date: 2021/5/17 11:51
 */
public interface IUserMsgService {
    /**
     * 新增用户信息
     *
     * @return boolean
     */
    boolean insertUserMsg(UserMsgBean userMsgBean);

    /**
     * 根据Id更改用户昵称
     *
     * @param memberId
     * @param newMemberName
     * @return boolean
     */
    boolean updateUserName(Integer memberId, String newMemberName);

    /**
     * 根据Id更改用户性别
     *
     * @param memberId
     * @param memberSex
     * @return boolean
     */
    boolean updateUserSex(Integer memberId, String memberSex);

    /**
     * 根据Id更改用户年龄
     *
     * @param memberId
     * @param memberAge
     * @return boolean
     */
    boolean updateUserAge(Integer memberId, Integer memberAge);

    /**
     * 根据Id更改用户头像
     *
     * @param memberId
     * @param memberHead
     * @return boolean
     */
    boolean updateUserHead(Integer memberId, String memberHead);


    /**
     * 根据Id查询用户信息
     *
     * @param memberId
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
     * @param memberId
     * @param signature
     * @return
     */
    boolean updateUserSignature(Integer memberId, String signature);

    /**
     * 根据id查询用户是否存在
     * @param memberId
     * @return
     */
    UserMsgBean selectUserById(Integer memberId);
}
