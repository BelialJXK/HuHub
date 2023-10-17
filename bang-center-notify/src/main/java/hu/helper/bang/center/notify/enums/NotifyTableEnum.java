package hu.helper.bang.center.notify.enums;

import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.notify.controller.request.NotifyDetailQueryRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 通知类型枚举
 *
 * @Author : Jiang XingKun
 * @Date : 2023/2/22
 * @Description :
 */
@Slf4j
public enum NotifyTableEnum {
    /**
     * 评论
     */
    COMMENTS(1, "评论", "bang_center_notify_xx1"),

    /**
     * 点赞
     */
    LIKES(2, "点赞", "bang_center_notify_xx1"),

    /**
     * 回复
     */
    REPLIES(3, "回复", "bang_center_notify_xx1"),

//    ANNOUNCEMENTS(4,"系统公告通知","bang_center_notify_xx1"),

    /**
     * 活动变动
     */
    CHANGES(4, "活动变动", "bang_center_notify_xx2");
    private final Integer code;
    private final String table;
    private final String desc;

    NotifyTableEnum(Integer code, String desc, String table) {
        this.code = code;
        this.desc = desc;
        this.table = table;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getTable() {
        return table;
    }

    public static void getNotifyTable(NotifyDetailQueryRequest request, Integer notifyType) {
        switch (notifyType) {
            case 1:
                request.setTargetTable(COMMENTS.getTable());
                break;
            case 2:
                request.setTargetTable(LIKES.getTable());
                break;
            case 3:
                request.setTargetTable(REPLIES.getTable());
                break;
            case 4:
                request.setTargetTable(CHANGES.getTable());
                break;
            default:
                log.error("通知类型错误:{}",notifyType);
                throw new BangException("通知类型错误");
        }
    }

}
