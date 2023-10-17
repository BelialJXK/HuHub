package hu.helper.bang.center.active.service;

import hu.helper.bang.center.active.controller.request.ActiveCreateRequest;
import hu.helper.bang.center.active.dao.model.ActiveDO;
import hu.helper.bang.center.active.service.model.ActiveDTO;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/20
 */
public interface ActiveService {
    /**
     * 获取活动帖子详情
     *
     * @param postId 帖子id
     * @return 活动返回类
     */
    ActiveDTO getActive(Long postId);

    /**
     * 新建活动帖子
     *
     * @param request 活动帖子请求类
     * @return 成功数量
     */
    int createActive(ActiveDO request);

}
