package hu.helper.bang.center.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.common.util.BangPreconditions;
import hu.helper.bang.center.user.dao.UserMapper;
import hu.helper.bang.center.user.dao.model.UserDO;
import hu.helper.bang.center.user.enums.UserRoleEnum;
import hu.helper.bang.center.user.enums.UserStatusEnum;
import hu.helper.bang.center.user.service.UserService;
import hu.helper.bang.center.user.service.model.UserDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

/**
 * @Author : Luo Siwei
 * @Date : 2023/2/11 20:59
 * @Description : 用户服务实现
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_NAME = "未完善用户";
    private static final String DEFAULT_USER_AVATAR = "test.jpg";
    @Value("${wx.app-id}")
    private String appId;
    @Value("${wx.app-secret}")
    private String appSecret;
    @Resource
    private UserMapper userMapper;

    /**
     * 获取微信openid
     *
     * @param code
     * @return
     */
    public String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap<String, Object> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject json = JSON.parseObject(response);
        String openId = json.getString("openid");
        if (openId == null || openId.length() == 0) {
            throw new RuntimeException("微信临时登陆凭证错误");
        }
        return openId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long registerUser(String openId) {
        //查询是否存在当前用户
        Long existCurrentUser = userMapper.haveUser(openId);
        if (ObjectUtils.isEmpty(existCurrentUser)) {
            //当前用户注册
            UserDO userDO = new UserDO();
            userDO.setNickname(DEFAULT_USER_NAME);
            userDO.setAvatarUrl(DEFAULT_USER_AVATAR);
            userDO.setOpenId(openId);
            userDO.setStatus(UserStatusEnum.NORMAL.getCode());
            userDO.setRoleId(UserRoleEnum.NORMAL_USER.getCode());
            userMapper.registerUser(userDO);
            return userDO.getId();
        } else {
            //如果用户已经注册了，就抛出异常
            throw new BangException("用户已经注册，请勿重复注册");
        }
    }

    @Override
    public Long checkUserExist(String openId) {
        return userMapper.haveUser(openId);
    }

    @Override
    public UserDTO findUserById(Long id) {
        BangPreconditions.checkNotNull(id);
        UserDO userDO = Optional.of(userMapper.queryUserById(id)).orElseThrow(() -> new BangException("未查到对应用户"));
        return do2dto(userDO);
    }

    @Override
    public Boolean isCurrentUserLogin() {
        return StpUtil.isLogin();
    }

    @Override
    public UserDTO findCurrentUser() {
        if (!isCurrentUserLogin()) {
            throw new BangException("用户当前未登录");
        }
        long id = StpUtil.getLoginIdAsLong();
        return findUserById(id);
    }

    @Override
    public void exitCurrentUser() {
        if (!isCurrentUserLogin()) {
            throw new BangException("用户当前未登录");
        }
        StpUtil.logout();
    }

    @Override
    public String findRoleNameByUserId(Long id) {
        BangPreconditions.checkNotNull(id);
        Set<String> set = userMapper.queryUserRolesById(id);
        // 用户只会有一个角色
        if (CollectionUtils.isEmpty(set)) {
            throw new BangException("该用户未查询到角色");
        }
        return new ArrayList<>(set).get(0);
    }

    private UserDTO do2dto(UserDO userDO) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);
        return userDTO;
    }
}
