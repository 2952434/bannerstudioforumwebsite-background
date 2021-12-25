//package studio.banner.forumwebsite.filter;
//
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//
///**
// * @Author: Ljx
// * @Date: 2021/12/8 11:13
// * @role:
// */
//@ControllerAdvice
//public class HandlerControllerException {
//    @ExceptionHandler(RuntimeException.class)
//    public String handException(RuntimeException e) {
//        if (e instanceof AccessDeniedException) {
//            return "redirect:403.jsp";
//        }
//        return "redirect:500.jsp";
//    }
//}
