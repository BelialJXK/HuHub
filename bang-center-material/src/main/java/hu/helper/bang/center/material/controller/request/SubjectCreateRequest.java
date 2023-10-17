package hu.helper.bang.center.material.controller.request;


import hu.helper.bang.center.commpost.controller.request.PostInfoCreateRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


/**
 * 学习资料请求类
 * @author JIANG XINGKUN
 * @date 2023/03/19
 */
@Data
@ApiModel
public class SubjectCreateRequest  {
    @NotNull(message = "学校不能为空")
    private String university;
    @NotNull(message = "专业不能为空")
    private String major;
    @NotNull(message = "科目不能为空")
    private String subject;


}
