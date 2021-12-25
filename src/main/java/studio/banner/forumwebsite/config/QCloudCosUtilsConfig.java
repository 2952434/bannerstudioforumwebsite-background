package studio.banner.forumwebsite.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import studio.banner.forumwebsite.utils.QCloudCosUtils;

/**
 * @Author: Ljx
 * @Date: 2021/11/28 14:05
 * @role: 腾讯云配置
 */
@Configuration
public class QCloudCosUtilsConfig {
    @ConfigurationProperties(prefix = "qcloud")
    @Bean
    public QCloudCosUtils qcloudCosUtils() {
        return new QCloudCosUtils();
    }
}
