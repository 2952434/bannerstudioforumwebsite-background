package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.RespBean;

import java.util.List;
import java.util.Map;

/**
 * @Author: ljh
 * @Date: 2021/5/17 11:51
 * @role: 用户信息类接口
 */
public interface IMemberInformationService {

    /**
     * 新增用户信息
     *
     * @param memberInformationBean 用户信息实体
     * @return boolean
     */
    RespBean insertUserMsg(MemberInformationBean memberInformationBean);


    /**
     * 根据Id查询用户信息
     *
     * @param memberId 用户id
     * @return MemberInformationBean
     */
    MemberInformationBean selectUserMsg(Integer memberId);


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
    List<MemberInformationBean> selectBirthday(Integer memberId);

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
     * 根据id查询用户是否存在
     *
     * @param memberId 用户id
     * @return MemberInformationBean
     */
    MemberInformationBean selectUserById(Integer memberId);

    /**
     * 更新收藏量根据用户id
     * @param memberId
     */
    void  updateColNum(Integer memberId);

    /**
     * 点赞量加1
     * @param memberId
     */
    void increaseLikeNum(Integer memberId);

    /**
     * 点赞量减1
     * @param memberId
     */
    void underLikeNum(Integer memberId);


    /**
     *  根据用户id查询用户所有信息
     * @param memberId 用户id
     * @return Map<String,String>
     */
    RespBean selectAllInformationByMemberId(Integer memberId);

    /**
     * 更新用户信息
     * @param memberInformationBean
     * @return
     */
    RespBean updateMemberInformation(MemberInformationBean memberInformationBean);
}
