package hu.helper.bang.center.commpost.dao.model;

import lombok.Data;

import java.util.Date;

/**
 *
 *
 * @author lin
 * @date 2023/03/10
 */
@Data
public class CommentDo {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private Long postId;
    private Long userId;
    private Long toUserId;
    private Long pId;
    private Long toCommentId;
    private String content;
}
