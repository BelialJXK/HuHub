package hu.helper.bang.center.commpost.controller.request;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @author lin
 * @date 2023/03/13
 */
@Data
@ApiModel
public class CommentCreateRequest {
    @NotNull(message = "对应帖子id不能为空")
    private Long postId;
    @NotNull(message = "发表评论的用户id不能为空")
    private Long userId;
    private Long toUserId;
    private Long parentId;
    private Long toCommentId;
    @NotNull(message = "评论内容不能为空")
    private String content;
}
