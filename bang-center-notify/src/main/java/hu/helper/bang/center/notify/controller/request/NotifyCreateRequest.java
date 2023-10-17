package hu.helper.bang.center.notify.controller.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author : Jiang XingKun
 * @Date : 2023/2/21
 * @Description : 通知新建请求类
 */
@Data
@ApiModel
public class NotifyCreateRequest {

    @NotNull(message = "发送人ID不能为空")
    private Long senderId;

    @NotNull(message = "用户ID不能为空")
    private Long receiverId;

    @NotNull(message = "目标表id不能为空")
    private Long postId;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "通知内容不能为空")
    private String content;

    @NotNull(message = "通知类型不能为空")
    private Integer notifyType;

    private String photos;

}
