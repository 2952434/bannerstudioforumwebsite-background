//package studio.banner.forumwebsite;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import studio.banner.forumwebsite.bean.UserInfo;
//import studio.banner.forumwebsite.utils.JwtUtils;
//import studio.banner.forumwebsite.utils.RsaUtils;
//
//import java.security.PrivateKey;
//import java.security.PublicKey;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class JwtTest {
//
//    //生成公钥的路径
//    private static final String publicFilePath = "E:\\Java2\\key\\rsa.pub";
//    //生成私钥的路径
//    private static final String privateFilePath = "E:\\Java2\\key\\rsa.pri";
//
//    @Test
//    public void getPublicKey() throws Exception {
//        System.out.println(RsaUtils.getPublicKey(publicFilePath));
//    }
//
//    @Test
//    public void getPrivateKey() throws Exception {
//        System.out.println(RsaUtils.getPrivateKey(privateFilePath));
//    }
//
//    @Test
//    public void generateKey() throws Exception {
//        RsaUtils.generateKey(publicFilePath, privateFilePath, "saltss", 2048);
//    }
////    @Test
////    public void testRsa() throws Exception {
////        //生成公钥和私钥
////        //234指的时加密时的盐 越复杂越好
////        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
////    }
////
////
////    //生成的公钥
////    private PublicKey publicKey;
////    //生成的私钥
////    private PrivateKey privateKey;
////
////    @Before
////    public void testGetRsa() throws Exception {
////        //获得公钥和私钥
////        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
////        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
////    }
////
////
////    @Test
////    public void testGenerateToken() throws Exception {
////        // 生成token
////        System.out.println(privateKey);
////        String token = JwtUtils.generateToken(new UserInfo("17839003797", "12345"), privateKey, 5);
////        System.out.println("token = " + token);
////
////        /*
////         * 一般在登陆后或者每次刷新访问都会重新会写入cookie 因为是test所有没有HttpservletRequest和HttpservletRespone 所以就没写这步
////         *  CookieUtils.setCookie(request,response,"Cookie名字",token,过期时间,"utf-8",true);
////         */
////    }
////
////    @Test
////    public void testParseToken() throws Exception {
////
////        /*
////         * 一般在要访问用户登陆后才可以访问的内容 都会去从cookie中取出token解析 因为是test所有没有HttpservletRequest和HttpservletRespone 所以就没写这步
////         * String token= CookieUtils.getCookieValue(request,"Cookie名称");
////         */
////
////        //该token是刚才测试生成token打印的
////        String token = "eyJhbGciOiJSUzI1NiJ9.eyJwaG9uZSI6IjE3ODM5MDAzNzk3IiwicGFzc3dvcmQiOiIxMjM0NSIsImV4cCI6MTYzODYxOTM3OX0.XspmelWin4mXw8rCJHSDfJWVvJb2ULynV74NvdSoWguYxmlZFZJd-Uq4oO20SSLensVbgf8OknPUTiwEZLTTXvNkq8yzlMyVZXn3k2vWYBNZnj3mgxbGXC8tqTccPu8n4cHd4_CduDCj7nG5TtBMxJUjW0cL4IiRhEIzGBvTKu8";
////        // 解析token
////        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
////        System.out.println("phone: " + user.getPhone());
////        System.out.println("password: " + user.getPassword());
////
////        /*
////         * 验证成功一般还会用JwtUtil重新生成cookie 再用CookieUtil存入cookie 来刷新过期时间
////         *
////         * jwtProperties是把jwt的这些信息写入了配置文件 然后用@ConfigurationProperties读取成一个配置实体类
////         * 再用@EnableConfigurationProperties注解要注入该配置的类 然后才可以用@Autowired注入该配置实体类
////         * String newToken = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
////         *
////         * CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),newToken,jwtProperties.getExpire(),"utf-8",true);
////         */
////
////    }
//}