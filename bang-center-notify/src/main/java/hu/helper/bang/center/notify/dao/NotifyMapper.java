package hu.helper.bang.center.notify.dao;

import hu.helper.bang.center.notify.controller.request.NotifyCreateRequest;
import hu.helper.bang.center.notify.controller.request.NotifyUpdateRequest;
import hu.helper.bang.center.notify.dao.model.NotifyDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 通知mapper
 *
 * @Author : Jiang XingKun
 * @Date : 2023/2/22
 * @Description
 */
@Mapper
public interface NotifyMapper {
    String TABLE_INFO = " bang_center_notify_info ";
    String TABLE_USER = " bang_center_user_info  ";

    String FIELD_INFO = " gmt_create, gmt_modified, sender_id, receiver_id, status, post_id, title, content, notify_type, photos ";


    /**
     * 获取未读通知总数
     *
     * @param receiverId 用户id
     * @return 未读通知总数
     */
    @Select("select count(1) from " + TABLE_INFO + " where receiver_id=#{receiverId} and status=1")
    Integer getTotalUnread(long receiverId);

    /**
     * 新建通知
     *
     * @param notifyRecordDO 通知数据
     * @return 新增数量
     */
    @Insert("insert into" + TABLE_INFO + "(" + FIELD_INFO + ") "
            + "values (now(), now(), #{senderId}, #{receiverId}, 1, #{postId}, #{title}, #{content}, #{notifyType},#{photos})")
    Integer createNotify(NotifyDO notifyRecordDO);

    /**
     * 更新通知状态
     *
     * @param notifyRecordDO 通知数据
     * @return 更新数量
     */
    @Update("update " + TABLE_INFO + " set " +
            "status=#{status},gmt_modified=now()" +
            " where id=#{id}")
    Integer updateNotify(NotifyUpdateRequest notifyRecordDO);

    /**
     * 获取用户未删通知
     *
     * @param receiverId 用户id
     * @return 通知list
     */
    @Select("select  r.id as id, r.gmt_modified as gmt_modified," +
            " r.content as content, r.notify_type as notify_type, r.photos as photos, " +
            " s.nickname as sender_name, s.avatar_url as sender_photo"
            + " from " + TABLE_INFO + " as r "
            + " left join " + TABLE_USER + " as s on r.sender_id=s.id "
            + " where r.receiver_id=#{receiverId} and r.status!=3 and r.notify_type != 2 order by r.gmt_modified desc")
    List<NotifyDO> getNotifys(long receiverId);

    /**
     * 批量新建通知
     *
     * @param notifyCreateRequestList 新建数据列表
     * @return 新建成功数量
     */
    @Insert({
            "<script> insert Integero " + TABLE_INFO + " (" + FIELD_INFO + ") "
                    + " values "
                    + " <foreach collection=\"notifyCreateRequestList\" item=\"item\"  separator=\",\"  > "
                    + " (now(), now(), #{item.senderId},#{item.receiverId}, 1,  #{item.targetId}, "
                    + " #{item.content}, #{item.notifyType},#{item.photos} )"
                    + " </foreach>"
                    + "</script>"
    })
    Integer batchCreateNotify(@Param("notifyCreateRequestList") List<NotifyCreateRequest> notifyCreateRequestList);

    /**
     * 检查用户id是否存在
     *
     * @param receiverId 用户id
     * @return Boolean
     */
    @Select("select count(1) from " + TABLE_INFO + " where receiver_id=#{receiverId}")
    Integer checkId(Long receiverId);

    /**
     * 获取用户点赞通知
     *
     * @param receiverId 用户id
     * @return 点赞list
     */
    @Select("select  r.id as id, r.gmt_modified as gmt_modified, r.title as title, " +
            " r.content as content, r.notify_type as notify_type, r.photos as photos," +
            " s.nickname as sender_name, s.avatar_url as sender_photo"
            + " from " + TABLE_INFO + " as r "
            + " left join " + TABLE_USER + " as s on r.sender_id=s.id "
            + " where r.receiver_id=#{receiverId} and r.status!=3 and r.notify_type=2  order by r.gmt_modified desc")
    List<NotifyDO> getNotifyLikes(Long receiverId);
}
