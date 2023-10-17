package hu.helper.bang.center.commpost.service.model;

import hu.helper.bang.center.commpost.dao.model.CommentDo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 一个comment item 包含一个父评论和其所有的子评论
 * @author lin
 * @date 2023/03/16
 */
@Data
public class CommentDTO {
    CommentDo parentComment;
    List<CommentDo> commentDoList;

    public CommentDTO(CommentDo parentComment){
        this.parentComment = parentComment;
        commentDoList = new ArrayList<>();
    }
    public Date getParentCommentDate(){
        return parentComment.getGmtModified();
    }
}
