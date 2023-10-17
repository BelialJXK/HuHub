package hu.helper.bang.center.commpost.service;

/**
 *
 *
 * @author lin
 * @date 2023/03/11
 */
public interface VoteService {
    /**
     *
     * 判断指定用户是否点赞某个帖子
     * @param userId 用户id
     * @param postId post id
     * @return {@link Boolean}
     */
     Boolean judgeVote(Long userId,Long postId);


    /**
     * 用户点赞了帖子
     *
     * @param userId 用户id
     * @param postId post id
     * @return int
     */
     int  userVoteForPost(Long userId, Long postId);

    /**
     * 用户取消点赞了帖子
     *
     * @param userId 用户id
     * @param postId post id
     * @return int
     */
     int deleteVote(Long userId, Long postId);

    /**
     * 为帖子创建点赞模块
     * @param postId post id
     * @return {@link Long
     */
     int createVoteModule(Long postId);

    /**
     * 获得指定帖子的点赞数
     *
     * @param postId post id
     * @return int 如果为-1则表明未找到指定的帖子
     */
     int getNumberOfVotes(Long postId);

}
