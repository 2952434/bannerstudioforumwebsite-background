package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.UserDirectionBean;
import studio.banner.forumwebsite.bean.UserGradeBean;
import studio.banner.forumwebsite.bean.UserGradeContactBean;
import studio.banner.forumwebsite.bean.UserNameBean;
import studio.banner.forumwebsite.mapper.UserDirectionMapper;
import studio.banner.forumwebsite.mapper.UserGradeContactMapper;
import studio.banner.forumwebsite.mapper.UserGradeMapper;
import studio.banner.forumwebsite.mapper.UserNameMapper;
import studio.banner.forumwebsite.service.IUserGradeService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 19:51
 * @role: 班级服务类实现
 */
@Service
public class UserGradeServiceImpl implements IUserGradeService {

    @Autowired
    private UserGradeMapper userGradeMapper;
    @Autowired
    private UserGradeContactMapper userGradeContactMapper;
    @Autowired
    private UserNameMapper userNameMapper;
    @Autowired
    private UserDirectionMapper userDirectionMapper;

    /**
     * 通过用户id判断用户是否已添加年级和姓名
     *
     * @param userId 用户id
     * @return boolean
     */
    @Override
    public boolean judgeUserGradeNameById(Integer userId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        UserGradeContactBean userGradeContactBean = userGradeContactMapper.selectOne(queryWrapper);
        return userGradeContactBean == null;
    }

    /**
     * 给用户添加年级和姓名和方向
     *
     * @param userId        用户id
     * @param grade         用户班级
     * @param userName      用户姓名
     * @param userDirection 用户方向
     * @return boolean
     */
    @Override
    public boolean insertUserGradeDirection(Integer userId, String grade, String userName, String userDirection) {
        QueryWrapper<UserGradeContactBean> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", userId);
        UserGradeContactBean userGradeContactBean1 = userGradeContactMapper.selectOne(queryWrapper1);
        if (userGradeContactBean1 != null) {
            return false;
        } else {
            QueryWrapper<UserDirectionBean> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("user_direction", userDirection);
            List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper3);
            if (userDirectionBeans.size() == 0) {
                return false;
            } else {
                QueryWrapper<UserGradeBean> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_grade", grade);
                UserGradeBean userGradeBean = userGradeMapper.selectOne(queryWrapper);
                if (userGradeBean == null) {
                    return false;
                } else {
                    QueryWrapper<UserNameBean> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("user_name", userName);
                    List<UserNameBean> userNameBeans = userNameMapper.selectList(queryWrapper2);
                    if (userNameBeans.size() != 0) {
                        return false;
                    } else {
                        UserNameBean userNameBean = new UserNameBean();
                        userNameBean.setUserName(userName);
                        int insert1 = userNameMapper.insert(userNameBean);
                        if (insert1 != 1) {
                            return false;
                        } else {
                            UserNameBean userNameBean1 = userNameMapper.selectOne(queryWrapper2);
                            if (userNameBean1 != null) {
                                UserGradeContactBean userGradeContactBean = new UserGradeContactBean();
                                userGradeContactBean.setUserGradeId(userGradeBean.getId());
                                userGradeContactBean.setUserId(userId);
                                userGradeContactBean.setUserNameId(userNameBean1.getId());
                                userGradeContactBean.setUserDirectionId(userDirectionBeans.get(0).getId());
                                int insert = userGradeContactMapper.insert(userGradeContactBean);
                                return insert == 1;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据用户id查询用户所在班级
     *
     * @param userId 用户id
     * @return UserGradeBean
     */
    @Override
    public UserGradeBean selectUserGradeById(Integer userId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        UserGradeContactBean userGradeContactBean = userGradeContactMapper.selectOne(queryWrapper);
        if (userGradeContactBean == null) {
            return null;
        } else {
            QueryWrapper<UserGradeBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", userGradeContactBean.getUserGradeId());
            return userGradeMapper.selectOne(queryWrapper1);
        }
    }

    /**
     * 根据用户id查询用户姓名
     *
     * @param userId 用户id
     * @return UserNameBean
     */
    @Override
    public UserNameBean selectUserNameById(Integer userId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        UserGradeContactBean userGradeContactBean = userGradeContactMapper.selectOne(queryWrapper);
        if (userGradeContactBean == null) {
            return null;
        } else {
            QueryWrapper<UserNameBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", userGradeContactBean.getUserNameId());
            return userNameMapper.selectOne(queryWrapper1);
        }
    }

    /**
     * 根据用户id查询用户方向
     *
     * @param userId 用户id
     * @return UserDirectionBean
     */
    @Override
    public UserDirectionBean selectUserDirectionById(Integer userId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        if (userGradeContactBeans.size() != 1) {
            return null;
        } else {
            QueryWrapper<UserDirectionBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", userGradeContactBeans.get(0).getUserDirectionId());
            return userDirectionMapper.selectOne(queryWrapper1);
        }
    }

    /**
     * 通过年级查询该年级下所有用户名
     *
     * @param grade 班级
     * @return List<UserNameBean>
     */
    @Override
    public List<UserNameBean> selectUserNameByGrade(String grade) {
        QueryWrapper<UserGradeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_grade", grade);
        UserGradeBean userGradeBean = userGradeMapper.selectOne(queryWrapper);
        if (userGradeBean == null) {
            return null;
        } else {
            QueryWrapper<UserGradeContactBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_grade_id", userGradeBean.getId());
            List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper1);
            if (userGradeContactBeans.size() == 0) {
                return null;
            } else {
                List<UserNameBean> list = new ArrayList<>();
                for (UserGradeContactBean userGradeContactBean : userGradeContactBeans) {
                    QueryWrapper<UserNameBean> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id", userGradeContactBean.getUserNameId());
                    UserNameBean userNameBean = userNameMapper.selectOne(queryWrapper2);
                    list.add(userNameBean);
                }
                return list;
            }
        }
    }

    /**
     * 根据方向查询所有属于该年级用户的姓名
     *
     * @param userDirection 用户方向
     * @return List<UserNameBean>
     */
    @Override
    public List<UserNameBean> selectUserNameByDirection(String userDirection) {
        QueryWrapper<UserDirectionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_direction", userDirection);
        List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper);
        if (userDirectionBeans.size() != 1) {
            return null;
        } else {
            QueryWrapper<UserGradeContactBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_direction_id", userDirectionBeans.get(0).getId());
            List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper1);
            if (userDirectionBeans.size() == 0) {
                return null;
            } else {
                List<UserNameBean> userNameBeans = new ArrayList<>();
                for (UserGradeContactBean userGradeContactBean : userGradeContactBeans) {
                    QueryWrapper<UserNameBean> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id", userGradeContactBean.getUserNameId());
                    UserNameBean userNameBean = userNameMapper.selectOne(queryWrapper2);
                    userNameBeans.add(userNameBean);
                }
                return userNameBeans;
            }
        }
    }

    /**
     * 根据用户姓名查询用户信息id
     *
     * @param userName 用户姓名
     * @return List<UserGradeContactBean>
     */
    @Override
    public List<UserGradeContactBean> selectUserContactByUserName(String userName) {
        QueryWrapper<UserNameBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        List<UserNameBean> userNameBeans = userNameMapper.selectList(queryWrapper);
        if (userNameBeans.size() != 1) {
            return null;
        } else {
            QueryWrapper<UserGradeContactBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_name_id", userNameBeans.get(0).getId());
            return userGradeContactMapper.selectList(queryWrapper1);
        }
    }

    /**
     * 根据方向和年级查询用户姓名
     *
     * @param userDirection 方向
     * @param grade         班级
     * @return List<UserNameBean>
     */
    @Override
    public List<UserNameBean> selectUserNameByDirectionAndGrade(String userDirection, String grade) {
        QueryWrapper<UserGradeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_grade", grade);
        List<UserGradeBean> userGradeBeans = userGradeMapper.selectList(queryWrapper);
        if (userGradeBeans.size() != 1) {
            return null;
        } else {
            QueryWrapper<UserDirectionBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_direction", userDirection);
            List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper1);
            if (userDirectionBeans.size() != 1) {
                return null;
            } else {
                QueryWrapper<UserGradeContactBean> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2
                        .eq("user_direction_id", userDirectionBeans.get(0).getId())
                        .eq("user_grade_id", userGradeBeans.get(0).getId());

                List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper2);
                if (userDirectionBeans.size() == 0) {
                    return null;
                } else {
                    QueryWrapper<UserNameBean> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3.eq("id", userGradeContactBeans.get(0).getUserNameId());
                    return userNameMapper.selectList(queryWrapper3);
                }
            }
        }
    }

    /**
     * 根据id删除该用户
     *
     * @param userId 用户id
     * @return boolean
     */
    @Override
    public boolean deleteUserById(Integer userId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        if (userGradeContactBeans.size() == 0) {
            return false;
        } else {
            QueryWrapper<UserNameBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", userGradeContactBeans.get(0).getUserNameId());
            List<UserNameBean> userNameBeans = userNameMapper.selectList(queryWrapper1);
            if (userNameBeans.size() == 0) {
                return false;
            } else {
                UpdateWrapper<UserNameBean> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", userGradeContactBeans.get(0).getUserNameId());
                int delete = userNameMapper.delete(updateWrapper);
                UpdateWrapper<UserGradeContactBean> updateWrapper1 = new UpdateWrapper<>();
                updateWrapper.eq("user_id", userId);
                int delete1 = userGradeContactMapper.delete(updateWrapper1);
                return delete == 1 && delete1 == 1;
            }
        }
    }

    /**
     * 管理员增加用户年级
     *
     * @param userGradeBean 班级
     * @return boolean
     */
    @Override
    public boolean insertUserGrade(UserGradeBean userGradeBean) {
        QueryWrapper<UserGradeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_grade", userGradeBean.getUserGrade());
        List<UserGradeBean> userGradeBeans = userGradeMapper.selectList(queryWrapper);
        if (userGradeBeans.size() == 0) {
            int insert = userGradeMapper.insert(userGradeBean);
            return insert == 1;
        } else {
            return false;
        }
    }

    /**
     * 查询所有年级
     *
     * @return List<UserGradeBean>
     */
    @Override
    public List<UserGradeBean> selectAllUserGrade() {
        return userGradeMapper.selectList(null);
    }

    /**
     * 根据id修改年级信息
     *
     * @param userGradeBean 班级实体
     * @return boolean
     */
    @Override
    public boolean updateUserGradeById(UserGradeBean userGradeBean) {
        int updateById = userGradeMapper.updateById(userGradeBean);
        return updateById == 1;
    }

    /**
     * 根据年级id删除年级
     *
     * @param gradeId 年级id
     * @return boolean
     */
    @Override
    public boolean deleteUserGradeById(Integer gradeId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_grade_id", gradeId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        for (UserGradeContactBean userGradeContactBean : userGradeContactBeans) {
            userNameMapper.deleteById(userGradeContactBean.getUserNameId());
        }
        UpdateWrapper<UserGradeContactBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_grade_id", gradeId);
        userGradeContactMapper.delete(updateWrapper);
        int delete = userGradeMapper.deleteById(gradeId);
        return delete == 1;
    }

    /**
     * 添加学习方向
     *
     * @param userDirectionBean 学习方向实体
     * @return boolean
     */
    @Override
    public boolean insertUserDirection(UserDirectionBean userDirectionBean) {
        QueryWrapper<UserDirectionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_direction", userDirectionBean.getUserDirection());
        List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper);
        if (userDirectionBeans.size() != 0) {
            return false;
        } else {
            int insert = userDirectionMapper.insert(userDirectionBean);
            return insert == 1;
        }

    }

    /**
     * 查询所有学习方向
     *
     * @return List<UserDirectionBean>
     */
    @Override
    public List<UserDirectionBean> selectAllUserDirection() {
        return userDirectionMapper.selectList(null);
    }

    /**
     * 通过方向id更改学习方向
     *
     * @param userDirectionBean 方向实体
     * @return boolean
     */
    @Override
    public boolean updateUserDirectionById(UserDirectionBean userDirectionBean) {
        QueryWrapper<UserDirectionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_direction", userDirectionBean.getUserDirection());
        List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper);
        if (userDirectionBeans.size() != 0) {
            return false;
        } else {
            int updateById = userDirectionMapper.updateById(userDirectionBean);
            return updateById == 1;
        }
    }

    /**
     * 根据方向id删除该方向
     *
     * @param userDirectionId 方向id
     * @return boolean
     */
    @Override
    public boolean deleteUserDirectionById(Integer userDirectionId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_direction_id", userDirectionId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        for (UserGradeContactBean userGradeContactBean : userGradeContactBeans) {
            int delete = userNameMapper.deleteById(userGradeContactBean.getUserNameId());
            if (delete == 1) {
                int delete1 = userGradeContactMapper.deleteById(userGradeContactBean.getId());
                if (delete1 != 1) {
                    return false;
                }
            } else {
                return false;
            }
        }
        int delete = userDirectionMapper.deleteById(userDirectionId);
        return delete == 1;
    }

    /**
     * 根据用户id更改用户所在年级
     *
     * @param userId        用户id
     * @param userDirection 用户方向
     * @return boolean
     */
    @Override
    public boolean updateUserDirectionByUserId(Integer userId, String userDirection) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        if (userGradeContactBeans.size() == 0) {
            return false;
        } else {
            QueryWrapper<UserDirectionBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_direction", userDirection);
            List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper1);
            if (userDirectionBeans.size() == 0) {
                return false;
            } else {
                userGradeContactBeans.get(0).setUserDirectionId(userDirectionBeans.get(0).getId());
                int updateById = userGradeContactMapper.updateById(userGradeContactBeans.get(0));
                return updateById == 1;
            }
        }
    }

    /**
     * 根据用户id更改用户姓名
     *
     * @param userId       用户id
     * @param userNameBean 用户名字实体
     * @return boolean
     */
    @Override
    public boolean updateUserNameByUserId(Integer userId, UserNameBean userNameBean) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        if (userGradeContactBeans.size() == 0) {
            return false;
        } else {
            QueryWrapper<UserNameBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", userGradeContactBeans.get(0).getUserNameId());
            List<UserNameBean> userNameBeans = userNameMapper.selectList(queryWrapper1);
            if (userNameBeans.size() != 0) {
                return false;
            } else {
                userNameBean.setId(userGradeContactBeans.get(0).getUserNameId());
                int updateById = userNameMapper.updateById(userNameBean);
                return updateById == 1;
            }
        }
    }

    /**
     * 根据用户id更改用户所在方向
     *
     * @param userId    用户id
     * @param userGrade 用户方向
     * @return boolean
     */
    @Override
    public boolean updateUserGradeByUserId(Integer userId, String userGrade) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        if (userGradeContactBeans.size() == 0) {
            return false;
        } else {
            QueryWrapper<UserGradeBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_grade", userGrade);
            List<UserGradeBean> userGradeBeans = userGradeMapper.selectList(queryWrapper1);
            if (userGradeBeans.size() != 1) {
                return false;
            } else {
                userGradeContactBeans.get(0).setUserGradeId(userGradeBeans.get(0).getId());
                int updateById = userGradeContactMapper.updateById(userGradeContactBeans.get(0));
                return updateById == 1;
            }
        }
    }

    /**
     * 根据方向、年级、姓名模糊查询用户
     *
     * @param dim 模糊查询字段
     * @return List<UserGradeContactBean>
     */
    @Override
    public List<UserGradeContactBean> selectDimUserName(String dim) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        QueryWrapper<UserGradeBean> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.like("user_grade", dim);
        List<UserGradeBean> userGradeBeans = userGradeMapper.selectList(queryWrapper1);
        for (UserGradeBean userGradeBean : userGradeBeans) {
            queryWrapper.or().eq("user_grade_id", userGradeBean.getId());
        }
        QueryWrapper<UserNameBean> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.like("user_name", dim);
        List<UserNameBean> userNameBeans = userNameMapper.selectList(queryWrapper2);
        for (UserNameBean userNameBean : userNameBeans) {
            queryWrapper.or().eq("user_name_id", userNameBean.getId());
        }
        QueryWrapper<UserDirectionBean> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.like("user_direction", dim);
        List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper3);
        for (UserDirectionBean userDirectionBean : userDirectionBeans) {
            queryWrapper.or().eq("user_direction_id", userDirectionBean.getId());
        }
        return userGradeContactMapper.selectList(queryWrapper);
    }
}
