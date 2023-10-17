package hu.helper.bang.center.active.service.impl;

import hu.helper.bang.center.active.controller.request.ActiveCreateRequest;
import hu.helper.bang.center.active.dao.ActiveMapper;
import hu.helper.bang.center.active.dao.model.ActiveDO;
import hu.helper.bang.center.active.service.ActiveService;
import hu.helper.bang.center.active.service.model.ActiveDTO;
import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.commpost.controller.request.PostInfoCreateRequest;
import hu.helper.bang.center.commpost.enums.ModuleErrorCode;
import hu.helper.bang.center.commpost.service.impl.CommentServiceImpl;
import hu.helper.bang.center.commpost.service.impl.PostServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/20
 */
@Slf4j
@Service
public class ActiveServiceImpl implements ActiveService {
    @Resource
    PostServiceImpl postService;

    @Resource
    CommentServiceImpl commentService;
    @Resource
    ActiveMapper activeMapper;

    @Override
    public ActiveDTO getActive(Long postId) {
        ActiveDO Active = activeMapper.getActive(postId);
        if(null==Active){
            log.error("帖子ID不存在:{}",postId);
            throw new BangException("帖子ID不存在");
        }
        ActiveDTO ActiveDTO=new ActiveDTO();
        BeanUtils.copyProperties(Active,ActiveDTO);
        ActiveDTO.setComments(commentService.getComment(postId));
        return ActiveDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createActive(ActiveDO request) {
        PostInfoCreateRequest postInfoCreateRequest=new ActiveCreateRequest();
        BeanUtils.copyProperties(request,postInfoCreateRequest);
        Long postId= postService.registerPostInfo(postInfoCreateRequest);
        if( postId == ModuleErrorCode.CREATE_POST_IFO_ERROR.getCode()){
            log.error("活动帖子主表入库失败:{}",postInfoCreateRequest);
            throw new BangException("活动帖子主表入库失败: "+ postInfoCreateRequest);
        }
        request.setPostId(postId);
        int material=activeMapper.createActive(request);
        if(material!=1){
            log.error("活动帖子从表入库失败:{}",request);
            throw  new BangException("活动帖子从表入库失败:"+ request);
        }
        return 1;
    }


}
