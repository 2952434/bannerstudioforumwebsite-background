package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import studio.banner.forumwebsite.bean.*;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 19:53
 * @role: 班级服务类接口
 */
public interface IUserGradeService {

    /**
     * 通过用户id判断用户是否已添加年级和姓名
     *
     * @param userId 用户id
     * @return boolean
     */
    boolean judgeUserGradeNameById(Integer userId);

    /**
     * 给用户添加年级和姓名和方向
     *
     * @param userId        用户id
     * @param grade         用户班级
     * @param userName      用户姓名
     * @param userDirection 用户方向
     * @return boolean
     */
    boolean insertUserGradeDirection(Integer userId, String grade, String userName, String userDirection);

    /**
     * 根据用户id查询用户所在班级
     *
     * @param userId 用户id
     * @return UserGradeBean
     */
    UserGradeBean selectUserGradeById(Integer userId);

    /**
     * 根据用户id查询用户姓名
     *
     * @param userId 用户id
     * @return UserNameBean
     */
    UserNameBean selectUserNameById(Integer userId);

    /**
     * 根据用户id查询用户方向
     *
     * @param userId 用户id
     * @return UserDirectionBean
     */
    UserDirectionBean selectUserDirectionById(Integer userId);

    /**
     * 通过年级查询该年级下所有用户名
     *
     * @param grade 班级
     * @return List<UserNameBean>
     */
    List<UserNameBean> selectUserNameByGrade(String grade);

    /**
     * 根据方向查询所有属于该年级用户的姓名
     *
     * @param userDirection 用户方向
     * @return List<UserNameBean>
     */
    List<UserNameBean> selectUserNameByDirection(String userDirection);

    /**
     * 根据用户姓名查询用户信息id
     *
     * @param userName 用户姓名
     * @return List<UserGradeContactBean>
     */
    List<UserGradeContactBean> selectUserContactByUserName(String userName);

    /**
     * 根据方向和年级查询用户姓名
     *
     * @param userDirection 方向
     * @param grade         班级
     * @return List<UserNameBean>
     */
    List<UserNameBean> selectUserNameByDirectionAndGrade(String userDirection, String grade);


    /**
     * 根据id删除该用户
     *
     * @param userId 用户id
     * @return boolean
     */
    boolean deleteUserById(Integer userId);

    /**
     * 管理员增加用户年级
     *
     * @param userGradeBean 班级
     * @return boolean
     */
    boolean insertUserGrade(UserGradeBean userGradeBean);

    /**
     * 查询所有年级
     *
     * @return List<UserGradeBean>
     */
    List<UserGradeBean> selectAllUserGrade();

    /**
     * 根据id修改年级信息
     *
     * @param userGradeBean 班级实体
     * @return boolean
     */
    boolean updateUserGradeById(UserGradeBean userGradeBean);

    /**
     * 根据年级id删除年级
     *
     * @param gradeId 年级id
     * @return boolean
     */
    boolean deleteUserGradeById(Integer gradeId);

    /**
     * 添加学习方向
     *
     * @param userDirectionBean 学习方向实体
     * @return boolean
     */
    boolean insertUserDirection(UserDirectionBean userDirectionBean);

    /**
     * 查询所有学习方向
     *
     * @return List<UserDirectionBean>
     */
    List<UserDirectionBean> selectAllUserDirection();

    /**
     * 通过方向id更改学习方向
     *
     * @param userDirectionBean 方向实体
     * @return boolean
     */
    boolean updateUserDirectionById(UserDirectionBean userDirectionBean);

    /**
     * 根据方向id删除该方向
     *
     * @param userDirectionId 方向id
     * @return boolean
     */
    boolean deleteUserDirectionById(Integer userDirectionId);

    /**
     * 根据用户id更改用户所在年级
     *
     * @param userId        用户id
     * @param userDirection 用户方向
     * @return boolean
     */
    boolean updateUserDirectionByUserId(Integer userId, String userDirection);

    /**
     * 根据用户id更改用户姓名
     *
     * @param userId       用户id
     * @param userNameBean 用户名字实体
     * @return boolean
     */
    boolean updateUserNameByUserId(Integer userId, UserNameBean userNameBean);

    /**
     * 根据用户id更改用户所在方向
     *
     * @param userId    用户id
     * @param userGrade 用户方向
     * @return boolean
     */
    boolean updateUserGradeByUserId(Integer userId, String userGrade);

    /**
     * 根据方向、年级、姓名模糊查询用户
     *
     * @param dim 模糊查询字段
     * @return List<UserGradeContactBean>
     */
    List<UserGradeContactBean> selectDimUserName(String dim);

    /**
     * 根据年级查询帖子
     * @param grade 年级
     * @param page 页数
     * @return List<PostBean>
     */
    List<PostBean> selectPostByGrade(String grade,int page);
}
