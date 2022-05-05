package studio.banner.forumwebsite.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: Ljx
 * @Date: 2021/5/13 21:59
 */
@EnableTransactionManagement
@MapperScan("studio.banner.forumwebsite.mapper")
@Configuration
public class MybatisPlusConfig {
    @Bean
    public ConfigurationCustomizer customizer() {
        /**
         * 将实体中驼峰命名法和表中的下划线对应
         */
        return configuration -> configuration.setMapUnderscoreToCamelCase(true);
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }
}

