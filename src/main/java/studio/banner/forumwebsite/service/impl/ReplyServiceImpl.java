package studio.banner.forumwebsite.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.ReplyBean;
import studio.banner.forumwebsite.mapper.ReplyMapper;
import studio.banner.forumwebsite.service.IReplyService;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/17/18:07
 * @Description: 回复操作的实现类
 */
@Service
public class ReplyServiceImpl implements IReplyService {
    @Autowired
    ReplyMapper replyMapper;
    @Override
    public boolean insertReply(ReplyBean replyBean) {
        if (replyBean != null)
        {
            replyMapper.insert(replyBean);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReply(Integer replyId) {
        if (replyMapper.selectById(replyId) != null){
            replyMapper.deleteById(replyId);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllReplyByMemberId(Integer memberId) {
        if (selectAllReplyByMemberId(memberId,1).getRecords().size() != 0){
            UpdateWrapper<ReplyBean>updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("reply_member_id",memberId);
            replyMapper.delete(updateWrapper);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllReplyByCommentId(Integer commentId) {

        if (selectAllReplyByCommentId(commentId ,1) != null){
            UpdateWrapper<ReplyBean>updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("comment_id",commentId);
            replyMapper.delete(updateWrapper);
        }
        return false;
    }

    @Override
    public boolean updateReplyContent(Integer replyId, String newContent) {

        if (replyMapper.selectById(replyId) != null){
            UpdateWrapper<ReplyBean>updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("reply_id",replyId).set("reply_content",newContent);
            replyMapper.update(null,updateWrapper);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateReplyLikeNumber(Integer replyId) {

        if (replyMapper.selectById(replyId) != null){
            UpdateWrapper<ReplyBean>updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("reply_id",replyId).set("reply_like_number",replyMapper.selectById(replyId).getReplyLikeNumber()+1);
            replyMapper.update(null,updateWrapper);
            return true;
        }
        return false;
    }

    @Override
    public IPage<ReplyBean> selectAllReplyByCommentId(Integer commenmtId, int page) {
        QueryWrapper<ReplyBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id",commenmtId);
        Page<ReplyBean> page1 = new Page<>(page,10);
        IPage<ReplyBean> page2 = replyMapper.selectPage(page1,queryWrapper);
        if (page2.getSize() != 0){
            return page2;
        }
        return null;
    }

    @Override
    public IPage<ReplyBean> selectAllReplyByMemberId(Integer memberId, int page) {
            QueryWrapper<ReplyBean>queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("reply_member_id",memberId);
        Page<ReplyBean> page1 = new Page<>(page,10);
        IPage<ReplyBean> page2 = replyMapper.selectPage(page1,queryWrapper);
        if (page2.getSize() != 0){
            return page2;
        }
        return null;
    }

    @Override
    public ReplyBean selectReply(Integer replyId) {
        if(replyMapper.selectById(replyId) != null){
            return replyMapper.selectById(replyId);
        }
        return null;
    }
}
