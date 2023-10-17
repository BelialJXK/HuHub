package hu.helper.bang.center.commpost.controller;

import hu.helper.bang.center.common.result.BangResult;
import hu.helper.bang.center.commpost.controller.request.CommentCreateRequest;
import hu.helper.bang.center.commpost.controller.request.CommentDeleteRequest;
import hu.helper.bang.center.commpost.controller.respond.CommentRespond;
import hu.helper.bang.center.commpost.dao.model.CommentDo;
import hu.helper.bang.center.commpost.service.CommentService;
import hu.helper.bang.center.commpost.service.UtilsService;
import hu.helper.bang.center.commpost.service.model.CommentDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lin
 * @date 2023/03/13
 */
@Api(value = "评论模块控制器", tags = {"评论相关接口"})
@RestController
@Slf4j
@RequestMapping(value = "/comment")
public class CommentController {
    @Resource
    CommentService commentService;
    @Resource
    UtilsService utilsService;
    @GetMapping("")
    public BangResult test(){
        return BangResult.ok("comment model api success");
    }
    @ApiOperation(value = "新建评论或子评论")
    @PostMapping("/createComment")
    public BangResult createComment(@Valid @RequestBody CommentCreateRequest commentCreateRequest){
        System.out.println(commentCreateRequest);
        int result = commentService.createComment(commentCreateRequest);
        int notifyResult = utilsService.callNotifyByComment(commentCreateRequest);
        if(notifyResult > 0){
            log.info("通知创建成功");
        }else {
            log.error("通知创建失败");
        }
        if(result == 1){
            return BangResult.ok("新建评论或者子评论成功");
        }else {
            return BangResult.error("新建评论或者子评论失败");
        }
    }
    @ApiOperation(value = "删除评论")
    @PostMapping("/deleteComment")
    public BangResult createComment(@Valid @RequestBody CommentDeleteRequest commentDeleteRequest){
        Long commentId = commentDeleteRequest.getCommentId();
        int result = commentService.deleteComment(commentId);
        if(result>0){
            return BangResult.ok("评论删除成功").put("number",result);
        }else {
            return BangResult.error("删除评论失败，可能原因为未找到指定评论").put("number",result);
        }
    }
    @ApiOperation(value="返回给出post_id下的所有评论")
    @GetMapping("/getComment")
    public BangResult getComment(Long postId){
        if(postId==null){
            return BangResult.error("未给出postId");
        }
        List<CommentDTO> commentDTOList = commentService.getComment(postId);
        CommentRespond commentRespond = new CommentRespond();
        for(CommentDTO commentDTO:commentDTOList){
            commentRespond.createNewItem();
            commentRespond.addParentComment(commentDTO.getParentComment());
            for(CommentDo commentDo: commentDTO.getCommentDoList()){
                commentRespond.addComment(commentDo);
            }
        }
        return BangResult.ok().put("comment",commentRespond);
    }
}
