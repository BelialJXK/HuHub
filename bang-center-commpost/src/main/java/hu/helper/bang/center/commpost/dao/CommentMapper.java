package hu.helper.bang.center.commpost.dao;


import hu.helper.bang.center.commpost.dao.model.CommentDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author lin
 * @date 2023/03/13
 */
@Mapper
public interface CommentMapper {
    String TABLE_COMMENT = " bang_center_post_reply ";
    String COMMENT_FIELD = " gmt_create,gmt_modified,post_id,user_id,to_user_id,p_id,to_comment_id,content ";

    /**
     * 往数据库内插入新评论
     * @param commentDo comment实体类
     * @return int
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into" + TABLE_COMMENT + "("+ COMMENT_FIELD+")"+
            " values (now(),now(),#{postId},#{userId},#{toUserId},#{pId},#{toCommentId},#{content})"
    )
    int createComment(CommentDo commentDo);

//    /**
//     * 往数据库内插入新父评论
//     * @param commentDo comment实体类
//     * @return int
//     */
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    @Insert("insert into" + TABLE_COMMENT + "("+ COMMENT_FIELD+")"+
//            " values (now(),now(),#{postId},#{userId},#{toUserId},#{id},#{toCommentId},#{content})"
//    )
//    int createParentComment(CommentDo commentDo);
    /**
     * 删除全部的以入参comment id为pId的评论
     *
     * @param pId comment id 自增
     * @return int
     */
    @Delete("delete from" + TABLE_COMMENT +
                "where p_id=${pId}"
    )
    int deleteParentComment(Long pId);

    /**
     * 删除指定的comment
     * @param commentId commentId
     * @return int
     */
    @Delete("delete from" + TABLE_COMMENT+
                "where id=${commentId}"
    )
    int deleteByCommentId(Long commentId);

    /**
     * 获取一个一级id下的全部评论
     * @param parentId parent_id
     * @return {@link List}<{@link CommentDo}>
     */
    @Select("select * from" + TABLE_COMMENT +
                "where p_id=${parentId}"
    )
    @Results({
            @Result(column = "post_id", property = "postId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "gmt_create",property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "id",property = "id"),
            @Result(column = "content",property = "content"),
            @Result(column = "p_id",property = "pId"),
            @Result(column = "to_comment_id",property = "toCommentId"),
            @Result(column = "to_user_id",property = "toUserId")
    })
    List<CommentDo> getCommentByParentId(Long parentId);

    /**
     * 返回指定id的comment
     * @param commentId commentId
     * @return {@link CommentDo}
     */
    @Select("select * from" + TABLE_COMMENT +
                "where id=${commentId}"
    )
    CommentDo getCommentById(Long commentId);

    /**
     * 返回给出post id下的所有评论
     * @param postId post id
     * @return {@link List}<{@link CommentDo}>
     */
    @Select("select * from" + TABLE_COMMENT +
            "where post_id=${postId}"
    )
    @Results({
            @Result(column = "post_id", property = "postId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "gmt_create",property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "id",property = "id"),
            @Result(column = "content",property = "content"),
            @Result(column = "p_id",property = "pId"),
            @Result(column = "to_comment_id",property = "toCommentId"),
            @Result(column = "to_user_id",property = "toUserId")
    })
    List<CommentDo> getCommentByPostId(Long postId) ;

}
