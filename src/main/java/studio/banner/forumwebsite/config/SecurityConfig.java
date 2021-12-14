//package studio.banner.forumwebsite.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import studio.banner.forumwebsite.filter.JwtLoginFilter;
//import studio.banner.forumwebsite.filter.JwtVerifyFilter;
//import studio.banner.forumwebsite.service.IAdminLoginService;
//
///**
// * @author jijunxiang
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private IAdminLoginService adminLoginService;
//
//    @Autowired
//    private RsaKeyProperties prop;
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * 指定认证对象来源
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 对密码进行编码
////        auth.userDetailsService(adminLoginService).passwordEncoder(bCryptPasswordEncoder());
//        // 对密码不进行处理
//        auth.userDetailsService(adminLoginService).passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/product").hasAnyRole("admin")
//                // 其他都放行了
//                .anyRequest()
//                .authenticated()
//                .and()
//                .addFilter(new JwtLoginFilter(super.authenticationManager(),prop))
//                .addFilter(new JwtVerifyFilter(super.authenticationManager(),prop))
//                // 不需要session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
//}
