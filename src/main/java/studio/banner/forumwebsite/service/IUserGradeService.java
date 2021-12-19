package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.UserDirectionBean;
import studio.banner.forumwebsite.bean.UserGradeBean;
import studio.banner.forumwebsite.bean.UserGradeContactBean;
import studio.banner.forumwebsite.bean.UserNameBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 19:53
 * @role:
 */
public interface IUserGradeService {


    /**
     * 通过用户id判断用户是否已添加年级和姓名
     * @param userId
     * @return
     */
    boolean judgeUserGradeNameById(Integer userId);

    /**
     * 给用户添加年级和姓名和方向
     * @param userId
     * @param grade
     * @param userName
     * @param userDirection
     * @return
     */
    boolean insertUserGradeDirection(Integer userId, String grade,String userName,String userDirection);

    /**
     * 根据用户id查询用户所在班级
     * @param userId
     * @return
     */
    UserGradeBean selectUserGradeById(Integer userId);

    /**
     * 根据用户id查询用户姓名
     * @param userId
     * @return
     */
    UserNameBean selectUserNameById(Integer userId);

    /**
     * 根据用户id查询用户方向
     * @param userId
     * @return
     */
    UserDirectionBean selectUserDirectionById(Integer userId);

    /**
     *通过年级查询该年级下所有用户名
     * @param grade
     * @return
     */
    List<UserNameBean> selectUserNameByGrade(String grade);

    /**
     * 根据方向查询所有属于该年级用户的姓名
     * @param userDirection
     * @return
     */
    List<UserNameBean> selectUserNameByDirection(String userDirection);

    /**
     * 根据用户姓名查询用户信息id
     * @param userName
     * @return
     */
    List<UserGradeContactBean> selectUserContactByUserName(String userName);

    /**
     * 根据方向和年级查询用户姓名
     * @param userDirection
     * @param grade
     * @return
     */
    List<UserNameBean> selectUserNameByDirectionAndGrade(String userDirection,String grade);


    /**
     * 根据id删除该用户
     * @param userId
     * @return
     */
    boolean deleteUserById(Integer userId);

    /**
     * 管理员增加用户年级
     * @param userGradeBean
     * @return
     */
    boolean insertUserGrade(UserGradeBean userGradeBean);

    /**
     * 查询所有年级
     * @return
     */
    List<UserGradeBean> selectAllUserGrade();

    /**
     * 根据id修改年级信息
     * @param userGradeBean
     * @return
     */
    boolean updateUserGradeById(UserGradeBean userGradeBean);

    /**
     * 根据年级id删除年级
     * @param gradeId
     * @return
     */
    boolean deleteUserGradeById(Integer gradeId);

    /**
     * 添加学习方向
     * @param userDirectionBean
     * @return
     */
    boolean insertUserDirection(UserDirectionBean userDirectionBean);

    /**
     * 查询所有学习方向
     * @return
     */
    List<UserDirectionBean> selectAllUserDirection();

    /**
     * 通过方向id更改学习方向
     * @param userDirectionBean
     * @return
     */
    boolean updateUserDirectionById(UserDirectionBean userDirectionBean);

    /**
     * 根据方向id删除该方向
     * @param userDirectionId
     * @return
     */
    boolean deleteUserDirectionById(Integer userDirectionId);

    /**
     * 根据用户id更改用户所在年级
     * @param userId
     * @param userDirection
     * @return
     */
    boolean updateUserDirectionByUserId(Integer userId,String userDirection);

    /**
     * 根据用户id更改用户姓名
     * @param userId
     * @param userNameBean
     * @return
     */
    boolean updateUserNameByUserId(Integer userId,UserNameBean userNameBean);

    /**
     * 根据用户id更改用户所在方向
     * @param userId
     * @param userGrade
     * @return
     */
    boolean updateUserGradeByUserId(Integer userId,String userGrade);

    /**
     * 根据方向、年级、姓名模糊查询用户
     * @param dim
     * @return
     */
    List<UserGradeContactBean> selectDimUserName(String dim);
}
