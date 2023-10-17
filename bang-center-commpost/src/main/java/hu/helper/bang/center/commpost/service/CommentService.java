package hu.helper.bang.center.commpost.service;

import hu.helper.bang.center.commpost.controller.request.CommentCreateRequest;
import hu.helper.bang.center.commpost.dao.model.CommentDo;
import hu.helper.bang.center.commpost.service.model.CommentDTO;

import java.util.List;

/**
 * @author lin
 * @date 2023/03/13
 */
public interface CommentService {
    /**
     * 创建一个新的评论或子评论
     * @param commentCreateRequest request类
     * @return int
     */
    int createComment(CommentCreateRequest commentCreateRequest);

    /**
     * 删除评论
     * @param commentId comment id
     * @return int 删除的评论个数
     */
    int deleteComment(Long commentId);

    /**
     * 返回给出post id下的所有评论
     * @param postId post_id
     * @return {@link List}<{@link CommentDo}>
     */
    List<CommentDTO> getComment(Long postId);
}
