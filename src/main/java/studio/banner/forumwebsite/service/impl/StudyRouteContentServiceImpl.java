package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.StudyRouteContent;
import studio.banner.forumwebsite.mapper.StudyRouteContentMapper;
import studio.banner.forumwebsite.service.IStudyRouteContentService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hyy
 * @date 2021/5/15 10:01
 * @role 学习路线内容 service实现
 */
@Service
public class StudyRouteContentServiceImpl implements IStudyRouteContentService {
    protected static final Logger logger = LoggerFactory.getLogger(IStudyRouteContentService.class);
    @Resource
    private StudyRouteContentMapper studyRouteContentMapper;

    /**
     * 插入一条学习路线内容
     *
     * @param studyRouteContent 学习路线内容对象
     * @return boolean
     */
    @Override
    public boolean insert(StudyRouteContent studyRouteContent) {
        QueryWrapper<StudyRouteContent> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("serial_number", studyRouteContent.getSerialNumber())
                .eq("direction_id", studyRouteContent.getDirectionId());
        List<StudyRouteContent> list = studyRouteContentMapper.selectList(queryWrapper);
        if (list.size() == 0) {
            logger.info("插入成功");
            return studyRouteContentMapper.insert(studyRouteContent) == 1;
        }
        logger.info("插入失败");
        return false;
    }

    /**
     * 根据内容序号 和 学习路线id 删除指定一条学习路线内容
     *
     * @param serialNumber 内容序号
     * @param directionId  学习路线id
     * @return boolean
     */
    @Override
    public boolean delete(Integer serialNumber, Integer directionId) {
        QueryWrapper<StudyRouteContent> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("serial_number", serialNumber)
                .eq("direction_id", directionId);
        List<StudyRouteContent> list = studyRouteContentMapper.selectList(queryWrapper);
        if (list != null) {
            logger.info("删除成功");
            return studyRouteContentMapper.delete(queryWrapper) != 0;
        }
        logger.info("删除失败");
        return false;
    }

    /**
     * 更新指定学习路线指定阶段的指定内容
     *
     * @param studyRouteContent 学习内容对象
     * @return boolean
     */
    @Override
    public boolean update(StudyRouteContent studyRouteContent) {
        QueryWrapper<StudyRouteContent> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("serial_number", studyRouteContent.getSerialNumber())
                .eq("direction_id", studyRouteContent.getDirectionId());
        List<StudyRouteContent> list = studyRouteContentMapper.selectList(queryWrapper);
        if (list != null) {
            logger.info("更新成功");
            return studyRouteContentMapper.update(studyRouteContent, queryWrapper) != 0;
        }
        logger.info("更新失败");
        return false;
    }

    /**
     * 根据 内容序号、学习路线id 查询指定条 学习路线内容
     *
     * @param serialNumber 内容序号
     * @param directionId  学习路线id
     * @return StudyRouteContent
     */
    @Override
    public StudyRouteContent selectSingle(Integer serialNumber, Integer directionId) {
        QueryWrapper<StudyRouteContent> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("serial_number", serialNumber)
                .eq("direction_id", directionId);
        List<StudyRouteContent> list = studyRouteContentMapper.selectList(queryWrapper);
        if (list.size() != 0) {
            logger.info("查询成功");
            return list.get(0);
        }
        logger.info("查询失败");
        return null;
    }

    /**
     * 查询 指定学习路线的指定阶段的所有内容
     *
     * @param directionId 学习路线id
     * @return List<StudyRouteContent>
     */
    @Override
    public List<StudyRouteContent> selectByDirectionId(Integer directionId) {
        QueryWrapper<StudyRouteContent> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("direction_id", directionId);
        List<StudyRouteContent> list = studyRouteContentMapper.selectList(queryWrapper);
        if (list.size() != 0) {
            logger.info("查询成功");
            return list;
        }
        logger.info("查询失败");
        return null;
    }

    /**
     * 查询 学习路线内容表 中的所有内容
     *
     * @return List<StudyRouteContent>
     */
    @Override
    public List<StudyRouteContent> selectAll() {
        return studyRouteContentMapper.selectList(null);
    }

    /**
     * 分页查询 所有内容
     *
     * @param pageNumber 页码数
     * @param pageSize   每页容量
     * @return IPage<StudyRouteContent>
     */
    @Override
    public IPage<StudyRouteContent> selectByPage(Integer pageNumber, Integer pageSize) {
        Page<StudyRouteContent> page = new Page<>(pageNumber, pageSize);
        return studyRouteContentMapper.selectPage(page, null);
    }
}
