package hu.helper.bang.center.commpost.dao;

import hu.helper.bang.center.commpost.dao.model.InteractionDo;
import hu.helper.bang.center.commpost.dao.model.VoteDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *
 * 点赞系统 mapper
 * @author lin
 * @date 2023/03/11
 */
@Mapper
public interface VoteMapper {
    String TABLE_INTERACTION = " bang_center_post_interaction ";
    String TABLE_VOTE = " bang_center_post_vote ";
    String VOTE_FIELD = " user_id,post_id ";
    String INTERACTION_FIELD= " post_id,gmt_create,gmt_modified,type,count ";

    /**
     * 用户喜爱的全部帖子
     *
     * @param userId 用户id
     * @return {@link List}<{@link VoteDo}>
     */
    @Select({"select * from " + TABLE_VOTE + " where user_id=${userId}"})
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "post_id", property = "postId")
    })
    List<VoteDo> allPostUserVoted(Long userId);


    /**
     * 删除对应的关系
     *
     * @param userId 用户id
     * @param postId post id
     * @return int
     */
    @Delete("delete from " + TABLE_VOTE +
                " where user_id =${userId} " +
                " AND post_id=${postId}"
                )
    int deleteFromVote(Long userId, Long postId);


    /**
     * 减少count计数
     *
     * @param postId post id
     * @return int
     */
    @Update("update " + TABLE_INTERACTION +
            " set count=count-1, gmt_modified=now() " +
            " where post_id=${postId} ")
    int deleteFromInteraction(Long postId);

    /**
     * 增加count计数
     *
     * @param postId post id
     * @return int
     */
    @Update("update " + TABLE_INTERACTION +
            " set count=count+1, gmt_modified=now() " +
            " where post_id=${postId} ")
    int addToInteraction(Long postId);

    /**
     * 增加对应关系
     *
     * @param userId 用户id
     * @param postId post id
     * @return int
     */
    @Insert("insert into " + TABLE_VOTE +"("+ VOTE_FIELD+")"+
            " values (#{userId},#{postId})"
    )
    int addToVote(Long userId, Long postId);

    /**
     * 添加新item到interaction表
     *
     * @param postId post id
     * @return int
     */
    @Insert("insert into " + TABLE_INTERACTION + "(" + INTERACTION_FIELD+")" +
            " values (#{postId},now(),now(),0,0)"

    )
    int newInteractionItem(Long postId);

    /**
     * 根据postId获取指定的interaction item
     *
     * @param postId post id
     * @return {@link InteractionDo}
     */
    @Select("select * from " + TABLE_INTERACTION +
                "where post_id = ${postId}"
    )
    @Results({
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "post_id", property = "postId"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "type" , property = "type"),
            @Result(column = "count", property = "count")
    })
    InteractionDo getInteractionItem(Long postId);
}
