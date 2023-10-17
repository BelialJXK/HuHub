package hu.helper.bang.center.commpost.dao.model;

import lombok.Data;

import java.util.Date;

/**
 *
 *
 * @author lin guohao
 * @date 2023/03/10
 */
@Data
public class InteractionDo {
    private Long postId;
    private Date gmtCreate;
    private Date gmtModified;
    private int type;
    private int count;
}
