package hu.helper.bang.center.commpost.controller.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lin
 * @date 2023/03/15
 */
@Data
@ApiModel
public class CommentDeleteRequest {
    @NotNull
    Long commentId;
}
