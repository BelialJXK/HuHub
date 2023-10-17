package hu.helper.bang.center.active.controller.request;


import hu.helper.bang.center.commpost.controller.request.PostInfoCreateRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


/**
 * 学习资料请求类
 * @author JIANG XINGKUN
 * @date 2023/03/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class ActiveCreateRequest extends PostInfoCreateRequest {
    private Long postId;
    @NotNull(message = "活动地点不能为空")
    private String activePlace;
    @NotNull(message = "活动开始时间不能为空")
    private String activeStartTime;
    @NotNull(message = "活动结束时间不能为空")
    private String activeEndTime;
    @NotNull(message = "发起人微信号不能为空")
    private String wechat;
    @NotNull(message = "活动二维码不能为空")
    private String activeQrCode;
}
