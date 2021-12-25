package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import studio.banner.forumwebsite.bean.StudyRoute;

import java.util.List;

/**
 * @author hyy
 * @date 2021/5/14 16:39
 * @role 学习路线Service层接口
 */
public interface IStudyRouteService {
    /**
     * 插入一条学习路线
     *
     * @param studyRoute 学习路线对象
     * @return boolean
     */
    boolean insert(StudyRoute studyRoute);

    /**
     * 根据 学习方向 和 阶段编号 删除指定学习方向的指定阶段的内容
     *
     * @param studyDirection 学习方向
     * @param stageNumber    阶段编号
     * @return boolean
     */
    boolean deleteByNumber(String studyDirection, Integer stageNumber);

    /**
     * 根据学习方向 删除指定方向的所有内容
     *
     * @param studyDirection 学习方向
     * @return boolean
     */
    boolean deleteByDirection(String studyDirection);

    /**
     * 根据 学习方向 和 阶段编号 更新 指定学习方向的指定阶段的内容
     *
     * @param studyRoute 学习路线对象
     * @return boolean
     */
    boolean update(StudyRoute studyRoute);

    /**
     * 根据 学习方向 和 阶段编号 查询指定学习方向的指定阶段的内容
     *
     * @param studyDirection 学习方向
     * @param stageNumber    阶段编号
     * @return StudyRoute
     */
    StudyRoute selectByStageNumber(String studyDirection, Integer stageNumber);

    /**
     * 根据学习方向查询该方向的所有阶段的内容
     *
     * @param studyDirection 学习方向
     * @return List<StudyRoute>
     */
    List<StudyRoute> selectByStudyDirection(String studyDirection);

    /**
     * 查询所有学习方向内容
     *
     * @return List<StudyRoute>
     */
    List<StudyRoute> selectAll();

    /**
     * 分页查询所有学习方向内容
     *
     * @param pageNumber 页码数
     * @param pageSize   每页容量
     * @return IPage<StudyRoute>
     */
    IPage<StudyRoute> selectByPage(Integer pageNumber, Integer pageSize);

}
