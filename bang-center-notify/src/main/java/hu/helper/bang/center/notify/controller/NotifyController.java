package hu.helper.bang.center.notify.controller;

import hu.helper.bang.center.common.result.BangResult;
import hu.helper.bang.center.common.result.BangTableData;
import hu.helper.bang.center.notify.controller.request.NotifyCreateRequest;
import hu.helper.bang.center.notify.controller.request.NotifyDetailQueryRequest;
import hu.helper.bang.center.notify.controller.request.NotifyRequest;
import hu.helper.bang.center.notify.controller.request.NotifyUpdateRequest;
import hu.helper.bang.center.notify.enums.NotifyTableEnum;
import hu.helper.bang.center.notify.service.NotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author : Jiang XingKun
 * @Date : 2023/2/21
 * @Description : 通知控制器
 */
@Api(value = "通知控制器", tags = {"通知相关接口"})
@RestController
@Slf4j
@RequestMapping(value = "/notify")
public class NotifyController {
    @Resource
    private NotifyService notifyService;

    /**
     * 数据库变动数量
     */
    private final int SUCCESS=1;
    @ApiOperation(value = "获取未读通知总数")
    @GetMapping("/getNotifyNum")
    public BangResult getTotalUnread(Long receiverId) {
        if (null == receiverId) {
            return BangResult.error("用户id不能为空");
        }
        Integer total = notifyService.getTotalUnread(receiverId);
        return BangResult.ok("未读通知总数获取成功").put("total", total);
    }

    @ApiOperation(value = "新建通知数据")
    @PostMapping("/createNotify")
    public BangResult createNotify(@Valid @RequestBody NotifyCreateRequest request) {
        Integer result = notifyService.createNotify(request);
        if (result == SUCCESS) {
            return BangResult.ok("新建通知成功");
        }
        return BangResult.error("新建通知失败");
    }

    @ApiOperation(value = "批量新建通知数据")
    @PostMapping("/batchCreateNotify")
    public BangResult batchCreateNotify(@Valid @RequestBody List<NotifyCreateRequest> request) {
        Integer result = notifyService.batchCreateNotify(request);
        if (result == request.size()) {
            return BangResult.ok("新建通知成功");
        }
        return BangResult.error("新建通知失败");
    }

    @ApiOperation(value = "更新通知状态")
    @PostMapping("/updateNotifyState")
    public BangResult updateNotifyState(@Valid @RequestBody NotifyUpdateRequest request) {
        Integer result = notifyService.updateNotifyState(request);
        if (result == SUCCESS) {
            return BangResult.ok("更新通知成功");
        }
        return BangResult.error("更新通知失败");
    }

    @ApiOperation(value = "获取通知数据")
    @GetMapping("/getNotify")
    public BangResult getNotify(@Valid @RequestBody NotifyRequest notifyRequest) {
        BangTableData result = notifyService.getNotify(notifyRequest);
        if(result.getTableData().isEmpty()){
            return BangResult.ok("没有更多数据");
        }
        return BangResult.ok("通知数据获取成功").put("data", result);
    }

    @ApiOperation(value = "获取通知详情")
    @GetMapping("/getNotifyDetail")
    public BangResult getNotifyDetail(@Valid @RequestBody NotifyDetailQueryRequest request) {
        Integer notifyType = request.getNotifyType();
        NotifyTableEnum.getNotifyTable(request, notifyType);
        //TODO 查询目标表id
        //TODO 拿目标表id去调用帖子模块的返回接口
        return null;
    }

}
