package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import studio.banner.forumwebsite.bean.InformBean;

/**
 * @Author: Ljx
 * @Date: 2021/12/19 14:07
 * @role:
 */
public interface IInformService {
    /**
     * 增加通知
     * @param informBean
     * @return
     */
    boolean insertInform(InformBean informBean);

    /**
     * 分页查询通知
     * @param page
     * @return
     */
    IPage<InformBean> selectInform(Integer page);

    /**
     * 通过id查询通知
     * @param id
     * @return
     */
    InformBean selectInformById(Integer id);

    /**
     * 更新通知内容
     * @param informBean
     * @return
     */
    boolean updateInformById(InformBean informBean);

    /**
     * 根据id删除通知
     * @param id
     * @return
     */
    boolean deleteInformById(Integer id);
}
