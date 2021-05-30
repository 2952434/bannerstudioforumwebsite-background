package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.UserMsgBean;

/**
 * @Author: ljh
 * @Date: 2021/5/17 11:51
 */
public interface IUserMsgService {
    /**
     * 新增用户信息
     * @param  memberId;
     * @param  memberName;
     * @param  memberSex;
     * @param  memberAge;
     * @param  memberTime;
     * @param  memberHead;
     * @param  memberFans;
     * @param  memberAttention;
     * @return boolean
     */
    boolean insertUserMsg(Integer memberId, String memberName,String memberSex,Integer memberAge,String memberTime,String memberHead,Integer memberFans,Integer memberAttention);

    /**
     * 根据Id更改用户昵称
     * @param memberId
     * @param newMemberName
     * @return boolean
     */
     boolean updateUserName(Integer memberId,String newMemberName);

    /**
     * 根据Id更改用户性别
     * @param memberId
     * @param memberSex
     * @return boolean
     */
     boolean updateUserSex(Integer memberId,String memberSex);

    /**
     * 根据Id更改用户年龄
     * @param memberId
     * @param memberAge
     * @return boolean
     */
    boolean updateUserAge(Integer memberId,Integer memberAge);

    /**
     * 根据Id更改用户头像
     * @param memberId
     * @param memberHead
     * @return boolean
     */
    boolean updateUserHead(Integer memberId,String memberHead);


    /**
     * 根据Id查询用户信息
     * @param memberId
     * @return UserMsgBean
     */
    UserMsgBean selectUserMsg(Integer memberId);
}
