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
 * @role:
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

    @Override
    public boolean judgeUserGradeNameById(Integer userId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        UserGradeContactBean userGradeContactBean = userGradeContactMapper.selectOne(queryWrapper);
        if (userGradeContactBean == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean insertUserGradeDirection(Integer userId, String grade, String userName, String userDirection) {
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
                        QueryWrapper<UserGradeContactBean> queryWrapper1 = new QueryWrapper<>();
                        queryWrapper1.eq("user_id", userId);
                        UserGradeContactBean userGradeContactBean1 = userGradeContactMapper.selectOne(queryWrapper1);
                        if (userGradeContactBean1 == null && userNameBean1 != null) {
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
            UserGradeBean userGradeBean = userGradeMapper.selectOne(queryWrapper1);
            return userGradeBean;
        }
    }

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
            UserNameBean userNameBean = userNameMapper.selectOne(queryWrapper1);
            return userNameBean;
        }
    }

    @Override
    public UserDirectionBean selectUserDirectionById(Integer userId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        if (userGradeContactBeans.size()!=1){
            return null;
        }else {
            QueryWrapper<UserDirectionBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id",userGradeContactBeans.get(0).getUserDirectionId());
            UserDirectionBean userDirectionBean = userDirectionMapper.selectOne(queryWrapper1);
            return userDirectionBean;
        }
    }

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

    @Override
    public List<UserNameBean> selectUserNameByDirection(String userDirection) {
        QueryWrapper<UserDirectionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_direction",userDirection);
        List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper);
        if (userDirectionBeans.size()!=1){
            return null;
        }else {
            QueryWrapper<UserGradeContactBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_direction_id",userDirectionBeans.get(0).getId());
            List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper1);
            if (userDirectionBeans.size()==0){
                return null;
            }else {
                List<UserNameBean> userNameBeans = new ArrayList<>();
                for (UserGradeContactBean userGradeContactBean : userGradeContactBeans) {
                    QueryWrapper<UserNameBean> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id",userGradeContactBean.getUserNameId());
                    UserNameBean userNameBean = userNameMapper.selectOne(queryWrapper2);
                    userNameBeans.add(userNameBean);
                }
                return userNameBeans;
            }
        }
    }

    @Override
    public List<UserGradeContactBean> selectUserContactByUserName(String userName) {
        QueryWrapper<UserNameBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        List<UserNameBean> userNameBeans = userNameMapper.selectList(queryWrapper);
        if (userNameBeans.size()!=1){
            return null;
        }else {
            QueryWrapper<UserGradeContactBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_name_id",userNameBeans.get(0).getId());
            List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper1);
            return userGradeContactBeans;
        }
    }

    @Override
    public List<UserNameBean> selectUserNameByDirectionAndGrade(String userDirection, String grade) {
        QueryWrapper<UserGradeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_grade",grade);
        List<UserGradeBean> userGradeBeans = userGradeMapper.selectList(queryWrapper);
        if (userGradeBeans.size()!=1){
            return null;
        }else {
            QueryWrapper<UserDirectionBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_direction",userDirection);
            List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper1);
            if (userDirectionBeans.size()!=1){
                return null;
            }else {
                QueryWrapper<UserGradeContactBean> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2
                        .eq("user_direction_id",userDirectionBeans.get(0).getId())
                        .eq("user_grade_id",userGradeBeans.get(0).getId());

                List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper2);
                if (userDirectionBeans.size()==0){
                    return null;
                }else {
                    QueryWrapper<UserNameBean> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3.eq("id",userGradeContactBeans.get(0).getUserNameId());
                    List<UserNameBean> userNameBeans = userNameMapper.selectList(queryWrapper3);
                    return userNameBeans;
                }
            }
        }
    }

    @Override
    public boolean deleteUserById(Integer userId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        if (userGradeContactBeans.size()==0){
            return false;
        }else {
            QueryWrapper<UserNameBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id",userGradeContactBeans.get(0).getUserNameId());
            List<UserNameBean> userNameBeans = userNameMapper.selectList(queryWrapper1);
            if (userNameBeans.size()==0){
                return false;
            }else {
                UpdateWrapper<UserNameBean> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",userGradeContactBeans.get(0).getUserNameId());
                int delete = userNameMapper.delete(updateWrapper);
                UpdateWrapper<UserGradeContactBean> updateWrapper1 = new UpdateWrapper<>();
                updateWrapper.eq("user_id",userId);
                int delete1 = userGradeContactMapper.delete(updateWrapper1);
                if (delete==1&&delete1==1){
                    return true;
                }else {
                    return false;
                }
            }
        }
    }

    @Override
    public boolean insertUserGrade(UserGradeBean userGradeBean) {
        QueryWrapper<UserGradeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_grade",userGradeBean.getUserGrade());
        List<UserGradeBean> userGradeBeans = userGradeMapper.selectList(queryWrapper);
        if (userGradeBeans.size()==0){
            int insert = userGradeMapper.insert(userGradeBean);
            return insert==1;
        }else {
            return false;
        }

    }

    @Override
    public List<UserGradeBean> selectAllUserGrade() {
        List<UserGradeBean> userGradeBeans = userGradeMapper.selectList(null);
        return userGradeBeans;
    }

    @Override
    public boolean updateUserGradeById(UserGradeBean userGradeBean) {
        int updateById = userGradeMapper.updateById(userGradeBean);
        return updateById==1;
    }

    @Override
    public boolean deleteUserGradeById(Integer gradeId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_grade_id",gradeId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        for (UserGradeContactBean userGradeContactBean : userGradeContactBeans) {
            userNameMapper.deleteById(userGradeContactBean.getUserNameId());
        }
        UpdateWrapper<UserGradeContactBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_grade_id",gradeId);
        userGradeContactMapper.delete(updateWrapper);
        int delete = userGradeMapper.deleteById(gradeId);
        return delete==1;
    }

    @Override
    public boolean insertUserDirection(UserDirectionBean userDirectionBean) {
        QueryWrapper<UserDirectionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_direction",userDirectionBean.getUserDirection());
        List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper);
        if (userDirectionBeans.size()!=0){
            return false;
        }else {
            int insert = userDirectionMapper.insert(userDirectionBean);
            return insert==1;
        }

    }

    @Override
    public List<UserDirectionBean> selectAllUserDirection() {
        List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(null);
        return userDirectionBeans;
    }

    @Override
    public boolean updateUserDirectionById(UserDirectionBean userDirectionBean) {
        QueryWrapper<UserDirectionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_direction",userDirectionBean.getUserDirection());
        List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper);
        if (userDirectionBeans.size()!=0){
            return false;
        }else {
            int updateById = userDirectionMapper.updateById(userDirectionBean);
            return updateById==1;
        }
    }

    @Override
    public boolean deleteUserDirectionById(Integer userDirectionId) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_direction_id",userDirectionId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        for (UserGradeContactBean userGradeContactBean : userGradeContactBeans) {
            int delete = userNameMapper.deleteById(userGradeContactBean.getUserNameId());
            if (delete==1){
                int delete1 = userGradeContactMapper.deleteById(userGradeContactBean.getId());
                if (delete1!=1){
                    return false;
                }
            }else {
                return false;
            }
        }
        int delete = userDirectionMapper.deleteById(userDirectionId);
        if (delete==1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateUserDirectionByUserId(Integer userId,String userDirection) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        if (userGradeContactBeans.size()==0){
            return false;
        }else {
            QueryWrapper<UserDirectionBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_direction",userDirection);
            List<UserDirectionBean> userDirectionBeans = userDirectionMapper.selectList(queryWrapper1);
            if (userDirectionBeans.size()==0){
                return false;
            }else {
                userGradeContactBeans.get(0).setUserDirectionId(userDirectionBeans.get(0).getId());
                int updateById = userGradeContactMapper.updateById(userGradeContactBeans.get(0));
                return updateById == 1;
            }
        }
    }

    @Override
    public boolean updateUserNameByUserId(Integer userId, UserNameBean userNameBean) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        if (userGradeContactBeans.size()==0){
            return false;
        }else {
            QueryWrapper<UserNameBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id",userGradeContactBeans.get(0).getUserNameId());
            List<UserNameBean> userNameBeans = userNameMapper.selectList(queryWrapper1);
            if (userNameBeans.size()!=0){
                return false;
            }else {
                userNameBean.setId(userGradeContactBeans.get(0).getUserNameId());
                int updateById = userNameMapper.updateById(userNameBean);
                return updateById==1;
            }
        }
    }

    @Override
    public boolean updateUserGradeByUserId(Integer userId, String userGrade) {
        QueryWrapper<UserGradeContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<UserGradeContactBean> userGradeContactBeans = userGradeContactMapper.selectList(queryWrapper);
        if (userGradeContactBeans.size()==0){
            return false;
        }else {
            QueryWrapper<UserGradeBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_grade", userGrade);
            List<UserGradeBean> userGradeBeans = userGradeMapper.selectList(queryWrapper1);
            if (userGradeBeans.size()!=1){
                return false;
            }else {
                userGradeContactBeans.get(0).setUserGradeId(userGradeBeans.get(0).getId());
                int updateById = userGradeContactMapper.updateById(userGradeContactBeans.get(0));
                return updateById==1;
            }
        }
    }
}
