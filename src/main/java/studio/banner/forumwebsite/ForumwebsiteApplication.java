package studio.banner.forumwebsite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import studio.banner.forumwebsite.config.RsaKeyProperties;

/**
 * @author j8*ijunxiang
 */
@SpringBootApplication
@MapperScan("studio.banner.forumwebsite.mapper")
@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableSwagger2
@EnableScheduling
public class ForumwebsiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForumwebsiteApplication.class, args);
    }

}
