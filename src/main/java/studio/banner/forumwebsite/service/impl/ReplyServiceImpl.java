package studio.banner.forumwebsite.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        if (selectAllReplyByMemberId(memberId) != null){
            UpdateWrapper<ReplyBean>updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("reply_member_id",memberId);
            replyMapper.delete(updateWrapper);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllReplyByCommentId(Integer commentId) {

        if (selectAllReplyByCommentId(commentId) != null){
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
    public List<ReplyBean> selectAllReplyByCommentId(Integer commenmtId) {
        QueryWrapper<ReplyBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id",commenmtId);
        if (replyMapper.selectList(queryWrapper).size() != 0){
            return replyMapper.selectList(queryWrapper);
        }
        return null;
    }

    @Override
    public List<ReplyBean> selectAllReplyByMemberId(Integer memberId) {
            QueryWrapper<ReplyBean>queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("reply_member_id",memberId);
            if (replyMapper.selectList(queryWrapper).size() != 0){
                return replyMapper.selectList(queryWrapper);
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
