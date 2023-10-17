package hu.helper.bang.center.material.dao.model;

import hu.helper.bang.center.commpost.dao.model.PostInfoDo;
import hu.helper.bang.center.commpost.service.model.CommentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;
import java.util.Date;
import java.util.List;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MaterialDO extends PostInfoDo {
    /**
     * 文件地址
     */
    private String fileUrl;

}
