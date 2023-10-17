package hu.helper.bang.center.admin.dao;
import hu.helper.bang.center.admin.dao.model.PostInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WCY
 * @date 2023/04/21
 */
@Mapper
@Component("adminPostInfoMapper")
public interface PostInfoMapper {

    String TABLE_NAME = "bang_center_post_info";
    /**
     * 根据帖子ID查询帖子信息。
     *
     * @param id 帖子ID
     * @return PostInfo 帖子信息实体类
     */
    @Select("SELECT * FROM " + TABLE_NAME + " WHERE post_id = #{id}")
    @Results({
            @Result(column = "post_id", property = "postId"),
//            @Result(column = "gmt_create", property = "gmtCreate"),
//            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "type", property = "type"),
            @Result(column = "photos", property = "photos"),
            @Result(column = "deleted", property = "deleted")
    })
    PostInfo findPostById(Long id);
    /**
     * 更新帖子信息。
     *
     * @param post PostInfo对象，包含要更新的帖子信息
     * @return int 受影响的行数
     */
    @Update("UPDATE " + TABLE_NAME + " SET deleted = #{deleted} WHERE post_id = #{postId}")
    int updatePost(PostInfo post);

    /**
     * 查询所有帖子信息。
     *
     * @return List<PostInfo> 帖子信息实体类列表
     */
    @Select("SELECT * FROM " + TABLE_NAME)
    @Results({
            @Result(column = "post_id", property = "postId"),
//            @Result(column = "gmt_create", property = "gmtCreate"),
//            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "type", property = "type"),
            @Result(column = "photos", property = "photos"),
            @Result(column = "deleted", property = "deleted")
    })
    List<PostInfo> findAllPosts();
}
