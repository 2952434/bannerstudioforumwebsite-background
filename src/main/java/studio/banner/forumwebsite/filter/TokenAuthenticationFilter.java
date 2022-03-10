package studio.banner.forumwebsite.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import studio.banner.forumwebsite.service.IMemberInformationService;
import studio.banner.forumwebsite.service.IUserGradeService;
import studio.banner.forumwebsite.service.IFixedInformationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Administrator
 * @version 1.0
 **/
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private IMemberInformationService iMemberInformationService;
    @Autowired
    private IFixedInformationService iFixedInformationService;
    @Autowired
    private IUserGradeService iUserGradeService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //解析出头中的token
        String token = httpServletRequest.getHeader("Authorization");
        String[] s = token.split(" ");
        token = s[1];
        if(token!=null){
            //从安全上下文中拿 到用户身份对象
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
            //取出用户身份信息
            String username = userAuthentication.getName();
//            AuthUser authUser = JSON.parseObject(principal, AuthUser.class);

//            //更新数据库操作
//            UserBean userBean = new UserBean(authUser);
//            iUserService.insertUser(userBean);
//            MemberInformationBean userMsgBean = new MemberInformationBean(authUser);
//            iMemberInformationService.insertUserMsg(userMsgBean);
//            FixedInformationBean usersInformationBean = new FixedInformationBean(authUser);
//            iFixedInformationService.insertUsersInformation(usersInformationBean);
//            iUserGradeService.insertUserGradeDirection(authUser.getId(),authUser.getGrade(),authUser.getMemberName(),authUser.getDirection());

            //用户权限
            Collection<? extends GrantedAuthority> authorities = userAuthentication.getAuthorities();
            //将用户信息和权限填充 到用户身份token对象中
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(username,null, AuthorityUtils.createAuthorityList(String.valueOf(authorities)));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            //将authenticationToken填充到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
