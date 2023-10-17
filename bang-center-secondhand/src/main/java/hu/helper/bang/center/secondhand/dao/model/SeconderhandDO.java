package hu.helper.bang.center.secondhand.dao.model;

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
public class SeconderhandDO extends PostInfoDo {

    /**
     * 价格
     */
    private String price;

}
