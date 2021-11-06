package studio.banner.forumwebsite.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: ljh
 * @Date: 2021/5/17 11:33
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_member_information")
@ApiModel(value="用户信息", description="注册时伴随产生的用户信息表")
@ToString
public class UserMsgBean {
    private Integer memberId;
    private String memberName;
    private String memberSex;
    private Integer memberAge;
    private String memberTime;
    private String memberHead;
    private Integer memberFans;
    private Integer memberAttention;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format= "yyyy-MM-dd")
    private Date memberBirthday;
    }
