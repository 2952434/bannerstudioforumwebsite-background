package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.StudyRouteBean;

/**
 * @Author: Ljx
 * @Date: 2022/3/5 21:09
 * @role: 学习路线服务接口
 */
public interface IStudyRouteService {

    /**
     * 增加学习路线
     * @param studyRouteBean
     * @return
     */
    RespBean insertStudyRoute(StudyRouteBean studyRouteBean);

    /**
     * 根据id删除学习路线
     * @param id
     * @return
     */
    RespBean deleteStudyRouteById(Integer id);

    /**
     * 根据id查询学习路线
     * @param id
     * @return
     */
    RespBean selectStudyRouteById(Integer id);

    /**
     * 根据方向和id查询学习路线
     * @param direction
     * @param id
     * @return
     */
    RespBean selectStudyRouteByDirection(String direction,Integer id);

    /**
     * 根据方向查询学习路线
     * @param direction
     * @return
     */
    RespBean selectStudyByDirection(String direction);
    /**
     * 更改学习路线
     * @param studyRouteBean
     * @return
     */
    RespBean updateStudyRoute(StudyRouteBean studyRouteBean);

    /**
     * 查询所有学习路线
     * @return
     */
    RespBean selectAllStudyRoute();
}
