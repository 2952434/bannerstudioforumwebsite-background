//package studio.banner.forumwebsite.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import studio.banner.forumwebsite.bean.Payload;
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
// * @Date: 2021/12/8 12:54
// * @role:
// */
//public class JwtVerifyFilter extends BasicAuthenticationFilter {
//
//    private RsaKeyProperties prop;
//    public JwtVerifyFilter(AuthenticationManager authenticationManager,RsaKeyProperties prop) {
//        super(authenticationManager);
//        this.prop = prop;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//            //请求体的头中是否包含Authorization
//            String header = request.getHeader("Authorization");
//            //Authorization中是否包含Bearer，不包含直接返回
//            if (header == null || !header.startsWith("Bearer ")) {
//                chain.doFilter(request, response);
//
//                response.setContentType("application/json;charset=utf-8");
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                PrintWriter out = response.getWriter();
//                Map resultMap = new HashMap<>();
//                resultMap.put("code", HttpServletResponse.SC_FORBIDDEN);
//                resultMap.put("msg", "请登录！！！");
//                out.write(new ObjectMapper().writeValueAsString(resultMap));
//                out.flush();
//                out.close();
//            }else {
//                //如果携带正确格式的token先得到token
//                String token = header.replace("Bearer ", "");
//                //验证token是否正确
//                Payload<UserInfo> payload = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), UserInfo.class);
//                UserInfo user = payload.getUserInfo();
//                if (user != null){
//                    UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authResult);
//                    chain.doFilter(request,response);
//                }
//            }
//    }
//}
