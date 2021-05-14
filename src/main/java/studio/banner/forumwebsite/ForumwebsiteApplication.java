package studio.banner.forumwebsite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("studio.banner.forumwebsite.mapper")
@EnableSwagger2
public class ForumwebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumwebsiteApplication.class, args);
    }

}
