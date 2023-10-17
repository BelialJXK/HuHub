package hu.helper.bang.center.secondhand.controller.respond;


import hu.helper.bang.center.commpost.dao.model.PostInfoDo;
import hu.helper.bang.center.commpost.service.model.CommentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SeconderhandVO extends PostInfoDo {

    /**
     * 价格
     */
    private String price;
    /**
     * 评论list
     */
    private List<CommentDTO> comments;
}
