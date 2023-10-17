package hu.helper.bang.center.commpost.service.impl;

import hu.helper.bang.center.commpost.controller.request.CommentCreateRequest;
import hu.helper.bang.center.commpost.dao.PostInfoMapper;
import hu.helper.bang.center.commpost.dao.model.PostInfoDo;
import hu.helper.bang.center.commpost.enums.NotifyTypeEnum;
import hu.helper.bang.center.notify.controller.request.NotifyCreateRequest;
import hu.helper.bang.center.notify.service.NotifyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author lin
 * @date 2023/03/14
 */
@Service
public class UtilsServiceImpl implements hu.helper.bang.center.commpost.service.UtilsService {
    @Resource
    NotifyService notifyService;
    @Resource
    PostInfoMapper postInfoMapper;

    @Override
    public int callNotifyByComment(CommentCreateRequest commentCreateRequest){
        PostInfoDo postInfoDo = postInfoMapper.getPostInfo(commentCreateRequest.getPostId());
        if(postInfoDo == null){
            return -1;
        }
        System.out.println(commentCreateRequest);
        NotifyCreateRequest notifyCreateRequest = new NotifyCreateRequest();
        notifyCreateRequest.setSenderId(commentCreateRequest.getUserId());
        notifyCreateRequest.setReceiverId(postInfoDo.getUserId());
        notifyCreateRequest.setPostId(commentCreateRequest.getPostId());
        notifyCreateRequest.setTitle("您有一条新评论");
        notifyCreateRequest.setContent("点击查看详情");
        notifyCreateRequest.setNotifyType(NotifyTypeEnum.COMMENT.getCode());
        return notifyService.createNotify(notifyCreateRequest);

    }

    @Override
    public int callNotifyByVote(Long userId, Long postId){
        PostInfoDo postInfoDo = postInfoMapper.getPostInfo(postId);
        if(postInfoDo == null){
            return -1;
        }
        NotifyCreateRequest notifyCreateRequest = new NotifyCreateRequest();
        notifyCreateRequest.setSenderId(userId);
        notifyCreateRequest.setReceiverId(postInfoDo.getUserId());
        notifyCreateRequest.setPostId(postId);
        notifyCreateRequest.setTitle("您的帖子被人点赞了");
        notifyCreateRequest.setContent("点击查看详情");
        notifyCreateRequest.setNotifyType(NotifyTypeEnum.LIKE.getCode());
        return notifyService.createNotify(notifyCreateRequest);

    }
}
