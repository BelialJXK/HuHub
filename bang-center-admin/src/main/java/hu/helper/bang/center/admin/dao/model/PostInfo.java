package hu.helper.bang.center.admin.dao.model;

import lombok.Data;


/**
 * @author WCY
 * @date 2023/04/21
 */

@Data
public class PostInfo {

    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private String type;
    private Integer deleted;
    private String photos;
}
