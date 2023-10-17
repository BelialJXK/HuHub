package hu.helper.bang.center.material.controller.respond;


import hu.helper.bang.center.commpost.dao.model.PostInfoDo;
import hu.helper.bang.center.commpost.service.model.CommentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MaterialVO extends PostInfoDo {

    /**
     * 文件地址
     */
    private String fileUrl;
    /**
     * 评论list
     */
    private List<CommentDTO> comments;
}
