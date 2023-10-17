package hu.helper.bang.center.commpost.dao.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.JsonElement;
import hu.helper.bang.center.common.util.JsonElementSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @author lin
 */
@Data
public class PostInfoDo {
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 删除状态
     */
    private Boolean deleted;
    /**
     * 帖子id
     */
    private Long postId;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 用户名
     */
    private String nickname;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 帖子类型
     */
    private String type;
    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 图片地址
     */
    @JsonSerialize(using = JsonElementSerializer.class)
    JsonElement photos;
}
