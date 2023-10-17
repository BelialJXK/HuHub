package hu.helper.bang.center.commpost.dao;

import hu.helper.bang.center.commpost.dao.model.PostInfoDo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author lin
 * @date 2023/03/14
 */
@Mapper
public interface PostInfoMapper {
    String INFO_TABLE = " bang_center_post_info ";
    String INFO_FIELD = " gmt_create,gmt_modified,user_id,title,content,type,photos,deleted";

    /**
     * 获取Post Info信息
     * @param postId post id
     * @return {@link PostInfoDo}
     */
    @Select("select * from " + INFO_TABLE +
                "where post_id=${postId}"
    )
    @Results({
            @Result(column = "post_id", property = "postId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "gmt_create",property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "title",property = "title"),
            @Result(column = "content",property = "content"),
            @Result(column = "type",property = "type"),
            @Result(column = "photos",property = "photos"),
            @Result(column = "deleted",property = "deleted")
    })
    PostInfoDo getPostInfo(Long postId);

    /**
     * 往post info里添加一个新纪录
     *
     * @param postInfoDo hu.helper.bang.center.commpost.dao.model.PostInfoDo
     * @return int
     */
    @Options(useGeneratedKeys = true, keyProperty = "postId")
    @Insert("insert into" + INFO_TABLE +
                "("+INFO_FIELD+")" + " values (now(),now(),#{userId},#{title},#{content},#{type},#{photos},0)"
    )
    Long createPostInfo(PostInfoDo postInfoDo);

    /**
     * 将指定post info的deleted字段标记为1
     * @param postId post id
     * @return int
     */
    @Update("update" + INFO_TABLE +
                "set deleted=1,gmt_modified=now() " +
                    "where post_id=${postId}"
    )
    int deletePostInfo(Long postId);

    /**
     * 从post info表中删除指定post info
     * @param postId post id
     * @return int
     */
    @Delete("delete from" + INFO_TABLE +
            "where post_id=${postId}"
    )
    int deletePostInfoForever(Long postId);

    /**
     * 获取指定时间节点前未删除的number数量的post info
     * @param number 想要获取的数量
     * @param timestamp 想要获取的时间节点
     * @return {@link List}<{@link PostInfoDo}>
     */
    @Select("select * from " + INFO_TABLE +
            "WHERE deleted = 0 AND gmt_create  < ${timestamp}" +
            " ORDER BY gmt_create  DESC " +
            " LIMIT ${number}"
    )
    @Results({
            @Result(column = "post_id", property = "postId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "gmt_create",property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "title",property = "title"),
            @Result(column = "content",property = "content"),
            @Result(column = "type",property = "type"),
            @Result(column = "photos",property = "photos"),
            @Result(column = "deleted",property = "deleted")
    })
    List<PostInfoDo> getMultiPostInfo(int number, Date timestamp);

    /**
     * 获取当前时间节点前未删除的number数量的post info
     *
     * @param number 想要获取的数量
     * @return {@link List}<{@link PostInfoDo}>
     */
    @Select("select * from " + INFO_TABLE +
            "WHERE deleted = 0 AND gmt_create  < now()" +
            " ORDER BY gmt_create  DESC " +
            " LIMIT ${number}"
    )
    @Results({
            @Result(column = "post_id", property = "postId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "gmt_create",property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "title",property = "title"),
            @Result(column = "content",property = "content"),
            @Result(column = "type",property = "type"),
            @Result(column = "photos",property = "photos"),
            @Result(column = "deleted",property = "deleted")
    })
    List<PostInfoDo> getCurrentMultiPostInfo(int number);

}
