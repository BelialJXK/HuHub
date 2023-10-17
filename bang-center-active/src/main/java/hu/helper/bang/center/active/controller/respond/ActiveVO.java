package hu.helper.bang.center.active.controller.respond;


import hu.helper.bang.center.commpost.dao.model.PostInfoDo;
import hu.helper.bang.center.commpost.service.model.CommentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActiveVO extends PostInfoDo {
    /**
     * 活动地点
     */
    private String activePlace;
    /**
     * 开始时间
     */
    private Date activeStartTime;
    /**
     * 结束时间
     */
    private Date activeEndTime;
    /**
     * 微信号
     */
    private String wechat;
    /**
     * 活动群
     */
    private String activeQrCode;
    /**
     * 评论list
     */
    private List<CommentDTO> comments;
}
