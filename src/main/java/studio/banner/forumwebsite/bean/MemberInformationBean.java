package studio.banner.forumwebsite.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import studio.banner.forumwebsite.utils.TimeUtils;

import java.util.Date;

/**
 * @Author: ljh
 * @Date: 2021/5/17 11:33
 * <p>
 * 用户信息实体
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_member_information")
@ApiModel(value = "用户信息", description = "注册时伴随产生的用户信息表")
@ToString
public class MemberInformationBean {
    /**
     * 用户id
     */
    private Integer memberId;
    /**
     * 用户昵称
     */
    private String memberName;
    /**
     * 用户性别
     */
    private String memberSex;
    /**
     * 用户年龄
     */
    private Integer memberAge;
    /**
     * 用户账号创建时间
     */
    private String memberTime;
    /**
     * 用户头像地址
     */
    private String memberHead;
    /**
     * 用户粉丝数
     */
    private Integer memberFans;
    /**
     * 用户关注数
     */
    private Integer memberAttention;
    /**
     * 用户生日
     */
    private String memberBirthday;
    /**
     * 用户个性签名
     */
    private String memberSignature;
    /**
     * 用户帖子数量
     */
    private Integer memberPostNum;
    /**
     * 用户点赞数量
     */
    private Integer memberLikeNum;
    /**
     * 用户收藏数量
     */
    private Integer memberCollectNum;
    /**
     * 用户帖子总访问量
     */
    private Integer memberViewNum;

    /**
     * 用户手机号
     */
    private String memberPhone;

    /**
     * 用户QQ号
     */
    private String memberQq;
    /**
     * 用户微信号
     */
    @TableField(value = "member_wechat")
    private String memberWeChat;
    /**
     * 用户邮箱
     */
    private String memberEmail;

    public MemberInformationBean(AuthUser authUser) {
        this.memberId = authUser.getId();
        this.memberName = authUser.getUserName();
        this.memberSex = authUser.getSex();
        this.memberAge = TimeUtils.getAgeFromBirthTime(authUser.getBirthday());
        this.memberTime = authUser.getCreatTime();
        this.memberHead = authUser.getHeadPortraitUrl();
        this.memberFans = 0;
        this.memberAttention = 0;
        this.memberBirthday = authUser.getBirthday();
        this.memberSignature = authUser.getPersonalizedSignature();
        this.memberPostNum = 0;
        this.memberLikeNum = 0;
        this.memberCollectNum = 0;
        this.memberViewNum = 0;
        this.memberPhone = authUser.getPhone();
        this.memberQq = authUser.getMemberQQ();
        this.memberWeChat = authUser.getMemberWeChat();
        this.memberEmail = authUser.getEmail();
    }
}
