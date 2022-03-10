package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.StudyRouteBean;
import studio.banner.forumwebsite.mapper.StudyRouteMapper;
import studio.banner.forumwebsite.mapper.MemberInformationMapper;
import studio.banner.forumwebsite.service.IStudyRouteService;
import studio.banner.forumwebsite.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/5 21:12
 * @role:
 */
@Service
public class StudyRouteServiceImpl implements IStudyRouteService {

    @Autowired
    private StudyRouteMapper studyRouteMapper;
    @Autowired
    private MemberInformationMapper memberInformationMapper;


    @Override
    public RespBean insertStudyRoute(StudyRouteBean studyRouteBean) {
        QueryWrapper<StudyRouteBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("study_direction",studyRouteBean.getStudyDirection())
                .eq("study_title",studyRouteBean.getStudyTitle());
        if (studyRouteMapper.selectOne(queryWrapper)==null){
            if (studyRouteMapper.insert(studyRouteBean)==1){
                return RespBean.ok("学习路线添加成功");
            }
            return RespBean.error("学习路线添加失败");
        }
        return RespBean.error("学习路线已存在");
    }

    @Override
    public RespBean deleteStudyRouteById(Integer id) {
        if (studyRouteMapper.deleteById(id)==1){
            return RespBean.ok("删除学习路线成功");
        }
        return RespBean.error("删除学习路线失败");
    }

    @Override
    public RespBean selectStudyRouteById(Integer id) {
        StudyRouteBean studyRouteBean = studyRouteMapper.selectById(id);
        if (studyRouteBean!=null){
            return RespBean.ok("查询成功",studyRouteBean);
        }
        return RespBean.error("查询失败，无该学习路线");
    }

    @Override
    public RespBean selectStudyRouteByDirection(String direction,Integer id) {
        QueryWrapper<StudyRouteBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("study_direction",direction);
        QueryWrapper<MemberInformationBean> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("member_id",id);
        MemberInformationBean memberInformationBean = memberInformationMapper.selectOne(queryWrapper1);
        if (memberInformationBean ==null){
            return RespBean.error("查询失败");
        }
        String memberTime = memberInformationBean.getMemberTime();
        int age = TimeUtils.getAgeFromBirthTime(memberTime);
        List<StudyRouteBean> studyRouteBeans = studyRouteMapper.selectList(queryWrapper);
        List<StudyRouteBean> studyRouteBeans01 = new ArrayList<>();
        if (studyRouteBeans.size()!=0){
            if (age>0){
                return RespBean.ok("查询成功",studyRouteBeans);
            }
            for (int i = 0; i < studyRouteBeans.size(); i++) {
                if (TimeUtils.dateToStamp(studyRouteBeans.get(i).getPublishTime())
                        .compareTo(TimeUtils.dateToStamp(TimeUtils.getDateString()))<0){
                    studyRouteBeans01.add(studyRouteBeans.get(i));
                }
            }
            return RespBean.ok("查询成功",studyRouteBeans01);
        }
        return RespBean.error("暂无该方向学习计划",studyRouteBeans);
    }

    @Override
    public RespBean updateStudyRoute(StudyRouteBean studyRouteBean) {
        QueryWrapper<StudyRouteBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("study_direction",studyRouteBean.getStudyDirection())
                .eq("study_title",studyRouteBean.getStudyTitle());
        StudyRouteBean studyRouteBean1 = studyRouteMapper.selectOne(queryWrapper);
        if (studyRouteBean.getId()==studyRouteBean1.getId()){
            if (studyRouteMapper.updateById(studyRouteBean)==1){
                return RespBean.ok("学习路线更新成功");
            }
            return RespBean.error("学习路线更新失败");
        }
        return RespBean.error("学习路线产生冲突，更新失败");

    }

    @Override
    public RespBean selectAllStudyRoute() {
        List<StudyRouteBean> studyRouteBeans = studyRouteMapper.selectList(null);
        if (studyRouteBeans.size()==0){
            return RespBean.error("暂无学习路线");
        }
        return RespBean.ok("查询成功",studyRouteBeans);
    }

}
