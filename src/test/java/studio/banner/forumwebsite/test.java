package studio.banner.forumwebsite;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studio.banner.forumwebsite.bean.FixedInformationBean;
import studio.banner.forumwebsite.mapper.FixedInformationMapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2022/3/6 11:20
 * @role:
 */
@SpringBootTest
public class test {

    @Autowired
    FixedInformationMapper fixedInformationMapper;

    @Test
    public void test(){
        System.out.println(fixedInformationMapper);
//        List<String> list = fixedInformationMapper.selectUserGrade();
//        System.out.println(list);
    }
}
