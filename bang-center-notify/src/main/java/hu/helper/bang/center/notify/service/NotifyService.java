package hu.helper.bang.center.notify.service;


import hu.helper.bang.center.common.result.BangTableData;
import hu.helper.bang.center.notify.controller.request.NotifyCreateRequest;
import hu.helper.bang.center.notify.controller.request.NotifyRequest;
import hu.helper.bang.center.notify.controller.request.NotifyUpdateRequest;

import java.util.List;

/**
 * @Author : Jiang XingKun
 * @Date : 2023/2/21
 * @Description : 通知服务
 */

public interface NotifyService {
    /**
     * 获取用户未读通知总数
     * @param receiverId 用户id
     * @return 未读通知总数
     */
    Integer getTotalUnread(Long receiverId);

    /**
     * 新建通知
     * @param request 请求数据
     * @return 新建数量
     */
    Integer createNotify( NotifyCreateRequest request);

    /**
     * 更新通知状态
     * @param request 请求数据
     * @return 更新数量
     */
    Integer updateNotifyState( NotifyUpdateRequest request);

    /**
     * 获取通知列表
     * @param notifyRequest 请求数据
     * @return 通知列表
     */
    BangTableData getNotify(NotifyRequest notifyRequest);

    /**
     * 批量新建通知
     * @param request 新建数据列表
     * @return 新建成功数量
     */
    Integer batchCreateNotify(List<NotifyCreateRequest> request);

}