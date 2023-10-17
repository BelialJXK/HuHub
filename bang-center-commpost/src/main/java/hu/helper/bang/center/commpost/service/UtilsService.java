package hu.helper.bang.center.commpost.service;

import hu.helper.bang.center.commpost.controller.request.CommentCreateRequest;


/**
 * @author lin
 * @date 2023/03/14
 */
public interface UtilsService {
    /**
     * 为评论行为调用通知
     * @param commentCreateRequest CommentCreateRequest
     * @return int
     */
    int callNotifyByComment(CommentCreateRequest commentCreateRequest);

    /**
     * 为点赞行为调用通知
     * @param userId user id
     * @param postId post id
     * @return int
     */
    int callNotifyByVote(Long userId, Long postId);
}
