package studio.banner.forumwebsite.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import studio.banner.forumwebsite.bean.CommentBean;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/15/17:03
 * @Description: 评论功能服务层接口
 */
public interface ICommentService {
    /**
     * 增加评论
     * @param commentBean
     * @return boolean
     */
    boolean insertComment(CommentBean commentBean);

    /**
     * 根据评论id删除该评论
     * @param commentId
     * @return boolean
     */
    boolean deleteComment(int commentId);

    /**
     * 根据帖子id删除该帖子全部评论
     * @param commentPostId
     * @return boolean
     */
    boolean deleteAllCommnetByPostId(int commentPostId);

    /**
     * 根据用户id删除该用户所有评论
     * @param commentMemberId
     * @return boolean
     */
    boolean deleteAllCommentByMemberId(int commentMemberId);

    /**
     * 根据评论id修改评论内容
     * @param commentId
     * @return boolean
     */
    boolean updateCommentContent(int commentId,String newContent);

    /**
     * 根据评论id修改评论点赞量
     * @param commentId
     * @return boolean
     */
    boolean updateCommentLikeNumber(int commentId);

    /**
     * 根据帖子id查找该帖子下全部评论
     * @param commentPostId
     * @return List
     */
   List<CommentBean> selectAllCommentByPostId(int commentPostId);

    /**
     * 根据用户id查找该用户全部评论
     * @param commentMemberId
     * @return List
     */
     List <CommentBean>selectAllCommentByMemberId(int commentMemberId);

    /**
     * 根据评论id查找该评论
     * @param commentId
     * @return CommentBean
     */
    CommentBean selectComment(int commentId);

    /**
     * 分页查询全部评论
     * @return IPage
     */
    IPage<CommentBean> selectAllComment(int page);


}
