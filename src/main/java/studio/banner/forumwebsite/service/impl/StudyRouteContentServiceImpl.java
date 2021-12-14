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

    @Override
    public List<StudyRouteContent> selectAll() {
        return studyRouteContentMapper.selectList(null);
    }

    @Override
    public IPage<StudyRouteContent> selectByPage(Integer pageNumber, Integer pageSize) {
        Page<StudyRouteContent> page = new Page<>(pageNumber, pageSize);
        return studyRouteContentMapper.selectPage(page, null);
    }
}
