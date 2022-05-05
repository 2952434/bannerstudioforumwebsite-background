package studio.banner.forumwebsite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author j8*ijunxiang
 */
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class ForumwebsiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForumwebsiteApplication.class, args);
    }

}
