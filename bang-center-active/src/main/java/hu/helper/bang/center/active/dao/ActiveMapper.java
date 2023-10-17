package hu.helper.bang.center.active.dao;


import hu.helper.bang.center.active.controller.request.ActiveCreateRequest;
import hu.helper.bang.center.active.dao.model.ActiveDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/20
 */
@Mapper
public interface ActiveMapper {
    String TABLE_INFO = " bang_center_post_info_active ";
    String FIELD_INFO = "  post_id,gmt_create,gmt_modified,active_place,active_start_time"
                        +",active_end_time,wechat,active_qr_code ";

    /**
     * 新增学习资料从表记录
     *
     * @param request 学习资料请求类
     * @return 新增成功数量
     */
    @Insert("insert into" + TABLE_INFO + "(" + FIELD_INFO + ") "
            + "values (#{postId},now(),now(),#{activePlace},#{activeStartTime},#{activeEndTime},#{wechat},#{activeQrCode})")
    int createActive(ActiveDO request);

    /**
     * 获取资料帖子详情
     *
     * @param postId 帖子id
     * @return 学习资料返回类
     */
    @Select(" select p.post_id,p.title,p.content,p.type,p.gmt_modified,p.photos,u.nickname,u.avatar_url," +
            " a.active_place,a.active_start_time,a.active_end_time,a.wechat,a.active_qr_code "+
            " from bang_center_post_info p" +
            " left join bang_center_post_info_active a on a.post_id=p.post_id" +
            " left join bang_center_user_info u on p.user_id=u.id " +
            " where p.post_id=#{postId} and p.deleted=0")
    ActiveDO getActive(Long postId);


}
