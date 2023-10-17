package hu.helper.bang.center.commpost.service.impl;

import com.google.gson.Gson;
import hu.helper.bang.center.commpost.controller.request.PostInfoCreateRequest;
import hu.helper.bang.center.commpost.dao.PostInfoMapper;
import hu.helper.bang.center.commpost.dao.model.PostInfoDo;
import hu.helper.bang.center.commpost.enums.ModuleErrorCode;
import hu.helper.bang.center.commpost.service.PostService;
import hu.helper.bang.center.commpost.service.VoteService;
import hu.helper.bang.center.commpost.utils.UtilsTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lin
 * @date 2023/03/17
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {
    @Resource
    PostInfoMapper postInfoMapper;
    @Resource
    VoteService voteService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long registerPostInfo (PostInfoCreateRequest postInfoCreateRequest){
        PostInfoDo postInfoDo = new PostInfoDo();
        BeanUtils.copyProperties(postInfoCreateRequest,postInfoDo);
        if(!UtilsTools.judgeType(postInfoDo.getType())){
            log.error("错误的type类型,请查看type类型-String对照表");
            return (long) -1;
        }
        if(postInfoCreateRequest.getPhotos()!=null) {
            Gson gson = new Gson();
            postInfoDo.setPhotos(gson.toJsonTree(postInfoCreateRequest.getPhotos()));
        }
        if(postInfoMapper.createPostInfo(postInfoDo) !=1){
            return (long)ModuleErrorCode.CREATE_POST_IFO_ERROR.getCode();
        }
        if(voteService.createVoteModule(postInfoDo.getPostId())!=1){
            return (long)ModuleErrorCode.VOTE_MODULE_ERROR.getCode();
        }
        return postInfoDo.getPostId();
    }
    @Override
    public List<PostInfoDo> getPostInfo(int number, Date timestamp){
        if(timestamp !=null) {
            return postInfoMapper.getMultiPostInfo(number, timestamp);
        }else {
            return postInfoMapper.getCurrentMultiPostInfo(number);
        }
    }

}
