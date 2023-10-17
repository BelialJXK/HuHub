package hu.helper.bang.center.user.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.common.result.BangResult;
import hu.helper.bang.center.user.controller.request.LoginRequest;
import hu.helper.bang.center.user.controller.respond.UserVO;
import hu.helper.bang.center.user.service.UserService;
import hu.helper.bang.center.user.service.model.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @Author : Luo Siwei
 * @Date : 2023/1/27 21:02
 * @Description : 用户控制器
 */
@Api(value = "用户控制器", tags = {"用户相关接口"})
@RestController
@Slf4j
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/loginOrRegister")
    @ApiOperation("登录或注册用户")
    public BangResult loginOrRegister(@Valid @RequestBody LoginRequest request) {
        if (userService.isCurrentUserLogin()) {
            return BangResult.error("用户已登录，无需重复登录。");
        }
        String openId = userService.getOpenId(request.getCode());
        Long id = userService.checkUserExist(openId);
        if (null == id) {
            id = userService.registerUser(openId);
        }
        StpUtil.login(id);
        String token = Optional.of(StpUtil.getTokenInfo()).map(t -> t.tokenValue).orElseThrow(() -> new BangException("获取token失败"));
        return BangResult.ok("用户登录/注册成功")
                .put("token", token);
    }

    @GetMapping("/findUser")
    @ApiOperation("查看用户详情")
    public BangResult findUser(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            return BangResult.error("用户id不能为空");
        }
        UserDTO userById = userService.findUserById(id);
        if (ObjectUtils.isEmpty(userById)) {
            return BangResult.error("未查询到相关用户");
        }
        return BangResult.ok("角色查询成功")
                .put("user", dto2vo(userById));
    }

    @GetMapping("/isLogin")
    @ApiOperation("检查当前用户是否登录")
    public BangResult isLogin() {
        return BangResult.ok("当前用户登录状态").put("isLogin", userService.isCurrentUserLogin());
    }

    @GetMapping("/currentUserInfo")
    @ApiOperation("获取当前用户信息")
    public BangResult currentUserInfo() {
        if (!userService.isCurrentUserLogin()) {
            return BangResult.error("当前用户未登录");
        }
        UserDTO user = userService.findCurrentUser();
        return BangResult.ok("获取当前用户信息成功")
                .put("user", dto2vo(user));
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public BangResult logout() {
        if (!userService.isCurrentUserLogin()) {
            return BangResult.error("当前用户未登录");
        }
        userService.exitCurrentUser();
        return BangResult.ok("登出成功");
    }

    @GetMapping("/test")
    @ApiOperation("测试")
    @SaCheckRole("普通用户")
    public BangResult test() {
        return BangResult.ok("访问成功");
    }

    private UserVO dto2vo(UserDTO userDTO) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDTO, userVO);
        userVO.setRole(userService.findRoleNameByUserId(userVO.getId()));
        return userVO;
    }
}
