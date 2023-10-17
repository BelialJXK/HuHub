package hu.helper.bang.center.user.controller.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author : Luo Siwei
 * @Date : 2023/2/11 21:53
 * @Description : 登录请求参数
 */
@Data
@ApiModel
public class LoginRequest {
    @NotBlank(message = "微信临时授权不能为空")
    private String code;
}
