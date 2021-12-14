//package studio.banner.forumwebsite.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import studio.banner.forumwebsite.bean.UserInfo;
//import studio.banner.forumwebsite.config.RsaKeyProperties;
//import studio.banner.forumwebsite.utils.JwtUtils;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author: Ljx
// * @Date: 2021/12/8 12:28
// * @role:
// */
//public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
//    private AuthenticationManager authenticationManager;
//    private RsaKeyProperties prop;
//
//    public JwtLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
//        this.authenticationManager = authenticationManager;
//        this.prop = prop;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            UserInfo userInfo = new ObjectMapper().readValue(request.getInputStream(),UserInfo.class);
//            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userInfo.getUsername(),userInfo.getPassword());
//            return this.getAuthenticationManager().authenticate(authRequest);
//        }catch (Exception e){
//            try {
//                //处理失败请求
//                response.setContentType("application/json;charset=utf-8");
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                PrintWriter out = response.getWriter();
//                Map resultMap = new HashMap<>();
//                resultMap.put("code", HttpServletResponse.SC_UNAUTHORIZED);
//                resultMap.put("msg", "用户名或者密码错误");
//                out.write(new ObjectMapper().writeValueAsString(resultMap));
//                out.flush();
//                out.close();
//            } catch (Exception outEx) {
//                outEx.printStackTrace();
//            }
//
//            throw new RuntimeException(e);
//
//        }
//
//    }
//
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        UserInfo user = new UserInfo();
//        user.setPhone(authResult.getName());
//        user.setAuthorities(authResult.getAuthorities());
//        String token = JwtUtils.generateTokenExpireInMinutes(user, prop.getPrivateKey(), 10);
//        response.addHeader("Authorization","Bearer"+token);
//        try {
//            //处理失败请求
//            response.setContentType("application/json;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_OK);
//            PrintWriter out = response.getWriter();
//            Map resultMap = new HashMap<>();
//            resultMap.put("code", HttpServletResponse.SC_OK);
//            resultMap.put("msg", "认证通过！！！");
//            out.write(new ObjectMapper().writeValueAsString(resultMap));
//            out.flush();
//            out.close();
//        } catch (Exception outEx) {
//            outEx.printStackTrace();
//        }
//    }
//}
