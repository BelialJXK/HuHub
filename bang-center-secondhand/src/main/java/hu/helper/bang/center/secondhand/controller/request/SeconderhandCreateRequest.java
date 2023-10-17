package hu.helper.bang.center.secondhand.controller.request;


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
public class SeconderhandCreateRequest extends PostInfoCreateRequest {
    private Long postId;
    @NotNull(message = "价格不能为空")
    private String price;

}
