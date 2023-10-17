package hu.helper.bang.center.notify.controller.respond;

import lombok.Data;

import java.util.Date;

/**
 * @Author : Jiang XingKun
 * @Date : 2023/2/22
 * @Description :
 */
@Data
public class NotifyVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 发送人名称
     */
    private String senderName;

    /**
     * 发送人头像
     */
    private String senderPhoto;

    /**
     * 接收人名称
     */
    private String receiverName;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知类型（评论、点赞、回复、系统公告通知、活动变动）
     */
    private Integer notifyType;

    /**
     * 存minion图像地址，“；”分割
     */
    private String photos;

}
