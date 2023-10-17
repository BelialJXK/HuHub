package hu.helper.bang.center.secondhand.service.impl;

import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.commpost.controller.request.PostInfoCreateRequest;
import hu.helper.bang.center.commpost.enums.ModuleErrorCode;
import hu.helper.bang.center.commpost.service.impl.CommentServiceImpl;
import hu.helper.bang.center.commpost.service.impl.PostServiceImpl;
import hu.helper.bang.center.secondhand.controller.request.SeconderhandCreateRequest;
import hu.helper.bang.center.secondhand.dao.SeconderhandMapper;
import hu.helper.bang.center.secondhand.dao.model.SeconderhandDO;
import hu.helper.bang.center.secondhand.service.SeconderhandService;
import hu.helper.bang.center.secondhand.service.model.SeconderhandDTO;
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
public class SeconderhandServiceImpl implements SeconderhandService {
    @Resource
    PostServiceImpl postService;

    @Resource
    CommentServiceImpl commentService;
    @Resource
    SeconderhandMapper seconderhandMapper;

    @Override
    public SeconderhandDTO getSeconderhand(Long postId) {
        SeconderhandDO seconderhand = seconderhandMapper.getSeconderhand(postId);
        if(null==seconderhand){
            log.error("帖子ID不存在:{}",postId);
            throw new BangException("帖子ID不存在");
        }
        SeconderhandDTO seconderhandDTO=new SeconderhandDTO();
        BeanUtils.copyProperties(seconderhand,seconderhandDTO);
        seconderhandDTO.setComments(commentService.getComment(postId));
        return seconderhandDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createSeconderhand(SeconderhandCreateRequest request) {
        PostInfoCreateRequest postInfoCreateRequest=new SeconderhandCreateRequest();
        BeanUtils.copyProperties(request,postInfoCreateRequest);
        Long postId= postService.registerPostInfo(postInfoCreateRequest);
        if( postId == ModuleErrorCode.CREATE_POST_IFO_ERROR.getCode()){
            log.error("二手帖子主表入库失败:{}",postInfoCreateRequest);
            throw new BangException("二手帖子主表入库失败: "+ postInfoCreateRequest);
        }
        request.setPostId(postId);
        int material=seconderhandMapper.createSeconderhand(request);
        if(material!=1){
            log.error("资二手帖子从表入库失败:{}",request);
            throw  new BangException("二手帖子从表入库失败:"+ request);
        }
        return 1;
    }


}
