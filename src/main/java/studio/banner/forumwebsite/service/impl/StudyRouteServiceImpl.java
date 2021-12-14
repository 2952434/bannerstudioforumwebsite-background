package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.StudyRoute;
import studio.banner.forumwebsite.mapper.StudyRouteMapper;
import studio.banner.forumwebsite.service.IStudyRouteService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hyy
 * @date 2021/5/14 20:28
 * @role 学习路线Service层实现
 */
@Service
public class StudyRouteServiceImpl implements IStudyRouteService {
    protected static final Logger logger = LoggerFactory.getLogger(IStudyRouteService.class);
    @Resource
    private StudyRouteMapper studyRouteMapper;

    @Override
    public boolean insert(StudyRoute studyRoute) {
        QueryWrapper<StudyRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("study_direction", studyRoute.getStudyDirection())
                .eq("stage_number", studyRoute.getStageNumber());
        List<StudyRoute> list = studyRouteMapper.selectList(queryWrapper);
        if (list.size() == 0) {
            logger.info("插入成功！");
            return studyRouteMapper.insert(studyRoute) != 0;
        }
        logger.info("插入失败！");
        return false;
    }

    @Override
    public boolean deleteByNumber(String studyDirection, Integer stageNumber) {
        QueryWrapper<StudyRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("study_direction", studyDirection)
                .eq("stage_number", stageNumber);
        List<StudyRoute> list = studyRouteMapper.selectList(queryWrapper);
        if (list.size() != 0) {
            logger.info("删除成功！");
            return studyRouteMapper.delete(queryWrapper) != 0;
        }
        logger.info("删除失败！");
        return false;
    }

    @Override
    public boolean deleteByDirection(String studyDirection) {
        QueryWrapper<StudyRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("study_direction", studyDirection);
        List<StudyRoute> list = studyRouteMapper.selectList(queryWrapper);
        if (list.size() != 0) {
            Integer count = studyRouteMapper.delete(queryWrapper);
            logger.info(String.format("删除了%d条记录", count));
            return true;
        }
        logger.info("删除失败！");
        return false;
    }

    @Override
    public boolean update(StudyRoute studyRoute) {
        QueryWrapper<StudyRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("study_direction", studyRoute.getStudyDirection())
                .eq("stage_number", studyRoute.getStageNumber());
        List<StudyRoute> list = studyRouteMapper.selectList(queryWrapper);
        if (list.size() != 0) {
            logger.info("更新成功！");
            return studyRouteMapper.update(studyRoute, queryWrapper) != 0;
        }
        logger.info("更新失败！");
        return false;
    }

    @Override
    public StudyRoute selectByStageNumber(String studyDirection, Integer stageNumber) {
        QueryWrapper<StudyRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("study_direction", studyDirection)
                .eq("stage_number", stageNumber);
        List<StudyRoute> list = studyRouteMapper.selectList(queryWrapper);
        if (list.size() != 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<StudyRoute> selectByStudyDirection(String studyDirection) {
        QueryWrapper<StudyRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("study_direction", studyDirection)
                .orderBy(true, true, "stage_number");
        List<StudyRoute> list = studyRouteMapper.selectList(queryWrapper);
        if (list != null) {
            logger.info("查询成功");
            return list;
        }
        logger.info("查询失败");
        return null;
    }

    @Override
    public List<StudyRoute> selectAll() {
        return studyRouteMapper.selectList(null);
    }

    @Override
    public IPage<StudyRoute> selectByPage(Integer pageNumber, Integer pageSize) {
        Page<StudyRoute> page = new Page<>(pageNumber, pageSize);
        return studyRouteMapper.selectPage(page, null);
    }
}
