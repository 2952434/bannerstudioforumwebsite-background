package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import studio.banner.forumwebsite.bean.InformBean;

/**
 * @Author: Ljx
 * @Date: 2021/12/19 14:07
 * @role: 通知服务层接口
 */
public interface IInformService {
    /**
     * 增加通知
     *
     * @param informBean 通知实体
     * @return boolean
     */
    boolean insertInform(InformBean informBean);

    /**
     * 分页查询通知
     *
     * @param page 页数
     * @return IPage<InformBean>
     */
    IPage<InformBean> selectInform(Integer page);

    /**
     * 通过id查询通知
     *
     * @param id 通知id
     * @return InformBean
     */
    InformBean selectInformById(Integer id);

    /**
     * 更新通知内容
     *
     * @param informBean 通知实体
     * @return boolean
     */
    boolean updateInformById(InformBean informBean);

    /**
     * 根据id删除通知
     *
     * @param id 通知id
     * @return boolean
     */
    boolean deleteInformById(Integer id);
}
