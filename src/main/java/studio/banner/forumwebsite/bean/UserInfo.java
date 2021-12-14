//package studio.banner.forumwebsite.bean;
//
///**
// * @Author: Ljx
// * @Date: 2021/11/19 8:07
// * @role:
// */
//
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
///**
// * 载荷对象
// */
//@Data
//public class UserInfo implements UserDetails {
//
//    private String phone;
//
//    private String password;
//
//    private Collection<? extends GrantedAuthority> authorities;
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getUsername() {
//        return phone;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}