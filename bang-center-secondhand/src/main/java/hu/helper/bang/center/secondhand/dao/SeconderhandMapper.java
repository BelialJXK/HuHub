package hu.helper.bang.center.secondhand.dao;


import hu.helper.bang.center.secondhand.controller.request.SeconderhandCreateRequest;
import hu.helper.bang.center.secondhand.dao.model.SeconderhandDO;
import hu.helper.bang.center.secondhand.service.model.SeconderhandDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/120
 */
@Mapper
public interface SeconderhandMapper {
    String TABLE_INFO = " bang_center_post_info_second_hand ";
    String FIELD_INFO = "  post_id,gmt_create,gmt_modified,price ";

    /**
     * 新增学习资料从表记录
     *
     * @param request 学习资料请求类
     * @return 新增成功数量
     */
    @Insert("insert into" + TABLE_INFO + "(" + FIELD_INFO + ") "
            + "values (#{postId}, now(), now(), #{price})")
    int createSeconderhand(SeconderhandCreateRequest request);

    /**
     * 获取资料帖子详情
     *
     * @param postId 帖子id
     * @return 学习资料返回类
     */
    @Select(" select p.post_id,p.title,p.content,p.type,p.gmt_modified,p.photos,u.nickname,u.avatar_url,s.price" +
            " from bang_center_post_info p" +
            " left join bang_center_post_info_second_hand s on s.post_id=p.post_id" +
            " left join bang_center_user_info u on p.user_id=u.id " +
            " where p.post_id=#{postId} and p.deleted=0")
    SeconderhandDO getSeconderhand(Long postId);


}
