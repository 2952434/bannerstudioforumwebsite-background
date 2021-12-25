package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import studio.banner.forumwebsite.bean.StudyRouteContent;

import java.util.List;

/**
 * @author hyy
 * @date 2021/5/15 10:01
 * @role 学习路线内容 service层
 */
public interface IStudyRouteContentService {
    /**
     * 插入一条学习路线内容
     *
     * @param studyRouteContent 学习路线内容对象
     * @return boolean
     */
    boolean insert(StudyRouteContent studyRouteContent);

    /**
     * 根据内容序号 和 学习路线id 删除指定一条学习路线内容
     *
     * @param serialNumber 内容序号
     * @param directionId  学习路线id
     * @return boolean
     */
    boolean delete(Integer serialNumber, Integer directionId);

    /**
     * 更新指定学习路线指定阶段的指定内容
     *
     * @param studyRouteContent 学习内容对象
     * @return boolean
     */
    boolean update(StudyRouteContent studyRouteContent);

    /**
     * 根据 内容序号、学习路线id 查询指定条 学习路线内容
     *
     * @param serialNumber 内容序号
     * @param directionId  学习路线id
     * @return StudyRouteContent
     */
    StudyRouteContent selectSingle(Integer serialNumber, Integer directionId);

    /**
     * 查询 指定学习路线的指定阶段的所有内容
     *
     * @param directionId 学习路线id
     * @return List<StudyRouteContent>
     */
    List<StudyRouteContent> selectByDirectionId(Integer directionId);

    /**
     * 查询 学习路线内容表 中的所有内容
     *
     * @return List<StudyRouteContent>
     */
    List<StudyRouteContent> selectAll();

    /**
     * 分页查询 所有内容
     *
     * @param pageNumber 页码数
     * @param pageSize   每页容量
     * @return IPage<StudyRouteContent>
     */
    IPage<StudyRouteContent> selectByPage(Integer pageNumber, Integer pageSize);
}
