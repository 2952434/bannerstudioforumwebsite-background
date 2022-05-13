package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.banner.forumwebsite.bean.CollectBean;
import studio.banner.forumwebsite.bean.CollectFavoriteBean;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.mapper.CollectFavoriteMapper;
import studio.banner.forumwebsite.mapper.CollectMapper;
import studio.banner.forumwebsite.service.ICollectService;
import studio.banner.forumwebsite.service.IMemberInformationService;
import studio.banner.forumwebsite.service.IPostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2022/3/7 23:43
 * @role:
 */
@Service
public class CollectServiceImpl implements ICollectService {

    protected static Logger logger = LoggerFactory.getLogger(CollectServiceImpl.class);

    @Autowired
    private CollectFavoriteMapper collectFavoriteMapper;

    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private IPostService iPostService;
    @Autowired
    private IMemberInformationService iMemberInformationService;


    @Override
    public RespBean insertCollectFavorite(CollectFavoriteBean collectFavoriteBean) {
        if (judgeCollectFavorite(collectFavoriteBean.getUserId(), collectFavoriteBean.getFavoriteName())!=null){
            return RespBean.error("该文件夹以存在");
        }
        if (collectFavoriteMapper.insert(collectFavoriteBean)!=1) {
            return RespBean.error("添加文件夹失败");
        }
        return RespBean.ok("添加文件夹成功");
    }

    @Override
    public CollectFavoriteBean judgeCollectFavorite(Integer userId, String collectFavoriteName) {
        QueryWrapper<CollectFavoriteBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("favorite_name",collectFavoriteName)
                .eq("user_id",userId);
        return collectFavoriteMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer selectCollectNumByUserId(Integer userId) {
        QueryWrapper<CollectBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("col_user_id",userId);
        return collectMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer selectCollectNumByPostId(Integer postId) {
        QueryWrapper<CollectBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("col_art_id",postId);
        return collectMapper.selectCount(queryWrapper);
    }

    @Override
    public RespBean updateCollectFavorite(CollectFavoriteBean collectFavoriteBean) {
        if (judgeCollectFavorite(collectFavoriteBean.getUserId(), collectFavoriteBean.getFavoriteName())!=null){
            return RespBean.error("该文件夹以存在");
        }
        if (collectFavoriteMapper.updateById(collectFavoriteBean)==1){
            return RespBean.ok("更新收藏夹成功");
        }
        return RespBean.error("更新收藏夹失败");
    }

    @Override
    public RespBean selectCollectFavoriteById(Integer userId,Integer selectId) {
        QueryWrapper<CollectFavoriteBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<CollectFavoriteBean> collectFavoriteBeans = collectFavoriteMapper.selectList(queryWrapper);

        if (userId.equals(selectId)){
            return RespBean.ok("查询成功",collectFavoriteBeans);
        }
        List<CollectFavoriteBean> list = new ArrayList<>();
        for (CollectFavoriteBean collectFavoriteBean : collectFavoriteBeans) {
            if (collectFavoriteBean.getPrivacy()==0){
                list.add(collectFavoriteBean);
            }
        }
        return RespBean.ok("查询成功",list);
    }

    @Override
    public RespBean deleteCollectFavorite(Integer favoriteId, Integer moveFavoriteId) {
        if (moveFavoriteId==null){
            return RespBean.error("请选择");
        }
        if (collectFavoriteMapper.deleteById(favoriteId)==1) {
            logger.info("删除收藏夹成功");
        }else {
            return RespBean.error("删除收藏夹失败");
        }
        if (moveFavoriteId==-1){
            QueryWrapper<CollectBean> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("favorite_id",favoriteId);
            if (collectMapper.delete(queryWrapper)==1) {
                logger.info("帖子删除成功");
            }
        }else {
            UpdateWrapper<CollectBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("favorite_id",favoriteId).set("favorite_id",moveFavoriteId);
            collectMapper.update(null,updateWrapper);
        }
        return RespBean.ok("收藏夹删除成功");
    }

    /**
     * 判断该用户是否收藏该帖子
     * @param userId
     * @param postId
     * @return
     */
    @Override
    public List<CollectBean> judgeCollectPost(Integer userId, Integer postId) {
        QueryWrapper<CollectBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("col_user_id",userId).eq("col_art_id",postId);
        return collectMapper.selectList(queryWrapper);
    }

    /**
     * 增加收藏文章
     *
     * @param collectBean 贴子对象
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertCollect(CollectBean collectBean) {
        List<CollectBean> collectBeans = judgeCollectPost(collectBean.getColUserId(), collectBean.getColArtId());
        if (collectBeans.size()==0){
            if (collectMapper.insert(collectBean) == 1) {
                iMemberInformationService.updateColNum(collectBean.getColUserId());
                logger.info("添加收藏成功");
                return true;
            }
            logger.error("添加收藏失败");
            return false;
        }else {
            CollectBean collectBean1 = collectBeans.get(0);
            collectBean1.setFavoriteId(collectBean.getFavoriteId());
            if (collectMapper.updateById(collectBean1)==1) {
                logger.info("更改收藏夹成功");
                return true;
            }else {
                logger.error("更改收藏夹失败");
                return false;
            }
        }
    }


    /**
     * 根据收藏帖子id和用户id取消收藏文章
     * @param postId
     * @param userId
     * @return
     */
    @Override
    public boolean deleteCollect(Integer postId,Integer userId) {
        QueryWrapper<CollectBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("col_art_id",postId).eq("col_user_id",userId);
        if (collectMapper.delete(queryWrapper) == 1) {
            iMemberInformationService.updateColNum(userId);
            return true;
        }
        return false;
    }

    /**
     * 通过收藏id和用户id批量取消收藏
     * @param ids
     * @param userId
     * @return
     */
    @Override
    public RespBean deleteBatchCollectByIds(List<Integer> ids,Integer userId) {
        if (collectMapper.deleteBatchIds(ids)==1) {
            iMemberInformationService.updateColNum(userId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * 清除用户收藏
     *
     * @param userId 用户id
     * @return boolean
     */
    @Override
    public boolean deleteCollectByUserId(Integer userId) {
        QueryWrapper<CollectBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("clo_user_id", userId);
        if (collectMapper.delete(queryWrapper) == 1) {
            iMemberInformationService.updateColNum(userId);
            return true;
        }
        return false;
    }

    /**
     * 通过收藏帖子id 收藏夹id 更改帖子收藏夹
     * @param colId
     * @param favoriteId
     * @return
     */
    @Override
    public RespBean updateCollectById(Integer colId, Integer favoriteId) {
        UpdateWrapper<CollectBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("col_id",colId).set("favorite_id",favoriteId);
        if (collectMapper.update(null,updateWrapper)==1) {
            logger.info("收藏移动成功");
            return RespBean.ok("收藏移动成功");
        }
        return RespBean.error("收藏移动失败");
    }

    /**
     * 通过收藏帖子id 收藏夹id 批量更改帖子收藏夹
     * @param colIds
     * @param favoriteId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespBean updateCollectByIds(List<Integer> colIds, Integer favoriteId) {
        UpdateWrapper<CollectBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("col_id",colIds).set("favorite_id",favoriteId);
        if (collectMapper.update(null,updateWrapper)==1) {
            logger.info("收藏移动成功");
            return RespBean.ok("收藏移动成功");
        }
        return RespBean.error("收藏移动失败");
    }

    /**
     * 根据收藏夹id查询收藏夹收藏帖子
     * @param favoriteId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespBean selectCollectByFavoriteId(Integer favoriteId) {
        QueryWrapper<CollectBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("favorite_id",favoriteId);
        List<CollectBean> collectBeans = collectMapper.selectList(queryWrapper);
        if (collectBeans.size()==0){
            return RespBean.error("该收藏夹中无信息");
        }
        for (CollectBean collectBean : collectBeans) {
            PostBean postBean = iPostService.selectPost(collectBean.getColArtId());
            collectBean.setColArtTit(postBean.getPostTitle());
        }
        return RespBean.ok(collectBeans);

    }

    /**
     * 根据用户id和帖子id查询收藏夹id
     * @param userId
     * @param postId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespBean selectCollectFavoriteId(Integer userId, Integer postId) {
        QueryWrapper<CollectBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("col_user_id",userId).eq("col_art_id",postId);
        CollectBean collectBean = collectMapper.selectOne(queryWrapper);
        if (collectBean!=null){
            return RespBean.ok("查询成功",collectBean);
        }
        Map<String, String> map = collectFavoriteMapper.selectFavoriteId(userId);
        collectBean = new CollectBean();
        collectBean.setFavoriteId(Integer.parseInt(String.valueOf(map.get("favorite_id"))));
        return RespBean.ok("查询成功",collectBean);
    }

    @Override
    public RespBean selectCollectFavoriteByFavoriteId(Integer favoriteId) {

        return RespBean.ok("查询成功",collectFavoriteMapper.selectById(favoriteId));
    }

    /**
     * 跟据收藏夹id清除该收藏夹
     * @param favoriteId
     * @return
     */
    @Override
    public RespBean deleteCollectByFavoriteId(Integer favoriteId) {
        QueryWrapper<CollectBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("favorite_id",favoriteId);
        int delete = collectMapper.delete(queryWrapper);
        if (delete==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


}
