package hu.helper.bang.center.commpost.service.impl;

import hu.helper.bang.center.commpost.controller.request.CommentCreateRequest;
import hu.helper.bang.center.commpost.dao.CommentMapper;
import hu.helper.bang.center.commpost.dao.model.CommentDo;
import hu.helper.bang.center.commpost.service.CommentService;
import hu.helper.bang.center.commpost.service.model.CommentDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author lin
 * @date 2023/03/13
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    CommentMapper commentMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createComment(CommentCreateRequest commentCreateRequest) {
        CommentDo commentDo = new CommentDo();
        BeanUtils.copyProperties(commentCreateRequest,commentDo);
        commentDo.setPId(commentCreateRequest.getParentId());
        return commentMapper.createComment(commentDo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteComment(Long commentId) {
        if (commentMapper.getCommentById(commentId).getPId()==null) {
            return commentMapper.deleteParentComment(commentId) + commentMapper.deleteByCommentId(commentId);
        } else {
            List<CommentDo> commentDoList = commentMapper.getCommentByParentId(commentMapper.getCommentById(commentId).getPId());
            int count = 0;
            for (CommentDo item : commentDoList) {
                if (item.getId().equals(commentId) || (item.getToCommentId()!=null && item.getToCommentId().equals(commentId))) {
                    count += commentMapper.deleteByCommentId(item.getId());
                }
            }
            return count;
        }

    }

    @Override
    public List<CommentDTO> getComment(Long postId){
        List<CommentDo> commentDoList = commentMapper.getCommentByPostId(postId);
        List<CommentDTO>commentDTOList = new ArrayList<>();
        for (CommentDo commentDo:commentDoList){
            if(commentDo.getPId() == null){
                commentDTOList.add(new CommentDTO(commentDo));
            }
        }
        for (CommentDo commentDo:commentDoList){
             Optional<CommentDTO> first = commentDTOList.stream().filter(item ->
                    item.getParentComment().getId().equals(commentDo.getPId())).findFirst();
             if(first.isPresent()){
                 CommentDTO commentDTO = first.get();
                 List<CommentDo> commentList = commentDTO.getCommentDoList();
                 commentList.add(commentDo);
                 commentDTO.setCommentDoList(commentList);
             }
        }
        // sort
        for (CommentDTO commentDTO : commentDTOList){
            commentDTO.getCommentDoList().sort(Comparator.comparing(CommentDo::getGmtModified));
        }
        commentDTOList.sort(Comparator.comparing(CommentDTO::getParentCommentDate));
        return commentDTOList;

    }



}
