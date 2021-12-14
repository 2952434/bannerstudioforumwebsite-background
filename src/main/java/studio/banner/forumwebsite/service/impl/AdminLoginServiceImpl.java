//package studio.banner.forumwebsite.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import studio.banner.forumwebsite.bean.UserBean;
//import studio.banner.forumwebsite.bean.UserInfo;
//import studio.banner.forumwebsite.mapper.AdminMapper;
//import studio.banner.forumwebsite.service.IAdminLoginService;
//
//import java.util.Collections;
//
///**
// * @Author: Ljx
// * @Date: 2021/12/8 10:36
// * @role:
// */
//@Service
//public class AdminLoginServiceImpl implements IAdminLoginService {
//
//    @Autowired
//    private AdminMapper adminMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        QueryWrapper<UserBean> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("member_phone",s);
//        UserBean userBean = adminMapper.selectOne(queryWrapper);
//        if(userBean==null){
//            System.out.println("----------用户名报错--------------");
//            throw new UsernameNotFoundException("用户名错误！！");
//        }else {
//            UserInfo user = new UserInfo();
//            user.setPhone(user.getPhone());
//            user.setPassword(userBean.getMemberPassword());
//            user.setAuthorities(Collections.singleton(new SimpleGrantedAuthority(userBean.getMemberAdmin())));
//            return user;
//        }
//    }
//}
