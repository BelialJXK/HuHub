package hu.helper.bang.center.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import hu.helper.bang.center.admin.dao.model.PostInfo;
import hu.helper.bang.center.admin.dao.model.RoleInfo;
import hu.helper.bang.center.admin.dao.model.UserInfo;
import hu.helper.bang.center.admin.service.PostService;
import hu.helper.bang.center.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author WCY
 * @date 2023/04/21
 */

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;


    @GetMapping("")
    public String test() {
        return "hello world";
    }


    /**
     * 更新用户状态
     * @param id 用户ID
     * @param status 用户状态
     * @return ResponseEntity<String> 更新结果
     */
    @SaCheckRole("超级管理员")
    @PutMapping("/user/{id}/status/{status}")
    public ResponseEntity<String> updateUser(@PathVariable("id") @NotNull @Min(0) Long id, @PathVariable("status") @NotNull @Min(0) Integer status) {
        UserInfo user = userService.findUserById(id);
        if (Objects.equals(user.getStatus(), status)){
            return ResponseEntity.badRequest().body("已经设置为目标状态");
        }
        else {
            user.setStatus(status);
            userService.updateUser(user);
            return ResponseEntity.ok("更新成功");
        }
    }
    /**
     * 定义角色与帖子类型的映射关系
     */
    private static final Map<String, String> ROLE_TO_POST_TYPE_MAP;

    static {
        ROLE_TO_POST_TYPE_MAP = new HashMap<>();
        ROLE_TO_POST_TYPE_MAP.put("超级管理员", null);
        ROLE_TO_POST_TYPE_MAP.put("普通帖子管理员", "NormalPost");
        ROLE_TO_POST_TYPE_MAP.put("心情树洞管理员", "MoodHolePost");
        ROLE_TO_POST_TYPE_MAP.put("学习资料管理员", "StudyMaterialPost");
        ROLE_TO_POST_TYPE_MAP.put("二手交易管理员", "SecondHandTradePost");
        ROLE_TO_POST_TYPE_MAP.put("活动管理员", "ActivityPost");
    }

    /**
     * 更新帖子状态
     * @param id 用户ID
     * @param postId 帖子ID
     * @param deleted 删除状态
     * @param postType 帖子类型
     * @return ResponseEntity<String> 更新结果
     */
//    @SaCheckRole({"超级管理员", "普通帖子管理员", "心情树洞管理员", "学习资料管理员", "二手交易管理员", "活动管理员"})
    @PutMapping("/updatePost/userId/{id}/postId/{postId}/status/{deleted}/type/{postType}")
    public ResponseEntity<String> updatePost(@PathVariable("id") @NotNull @Min(0) Long id,
                                             @PathVariable("postId") @NotNull @Min(0) Long postId,
                                             @PathVariable("deleted") @NotNull @Min(0) Integer deleted,
                                             @PathVariable("postType") String postType) {

        boolean hasPermission = false;
        RoleInfo roleInfo = userService.findRoleById(id);
        for (String role : ROLE_TO_POST_TYPE_MAP.keySet()) {

            if (roleInfo.getRoleName().equals(role)) {
                if ("超级管理员".equals(role) || postType.equals(ROLE_TO_POST_TYPE_MAP.get(role))) {
                    hasPermission = true;
                    break;
                }
            }
        }
        if (!hasPermission) {
            return ResponseEntity.badRequest().body("无权限操作该类型的帖子");
        }

        PostInfo post = postService.findPostById(postId);
        if (Objects.equals(post.getDeleted(), deleted)) {
            return ResponseEntity.badRequest().body("已经设置为目标状态");
        } else {
            post.setDeleted(deleted);
            postService.updatePost(post);
            return ResponseEntity.ok("更新成功");
        }
    }

}
