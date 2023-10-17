package hu.helper.bang.center.secondhand.service;

import hu.helper.bang.center.secondhand.controller.request.SeconderhandCreateRequest;
import hu.helper.bang.center.secondhand.service.model.SeconderhandDTO;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/20
 */
public interface SeconderhandService {
    /**
     * 获取二手帖子详情
     * @param postId 帖子id
     * @return 二手返回类
     */
    SeconderhandDTO getSeconderhand(Long postId);

    /**
     * 新建二手帖子
     * @param request 二手帖子请求类
     * @return 成功数量
     */
    int  createSeconderhand(SeconderhandCreateRequest request);

}
