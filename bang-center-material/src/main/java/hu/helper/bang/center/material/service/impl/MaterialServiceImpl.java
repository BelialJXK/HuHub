package hu.helper.bang.center.material.service.impl;

import hu.helper.bang.center.material.controller.request.MaterialCreateRequest;
import hu.helper.bang.center.material.controller.request.SubjectCreateRequest;
import hu.helper.bang.center.material.dao.MaterialMapper;
import hu.helper.bang.center.material.dao.model.MaterialDO;
import hu.helper.bang.center.material.dao.model.SubjectDO;
import hu.helper.bang.center.material.service.MaterialService;
import hu.helper.bang.center.material.service.model.MaterialDTO;
import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.commpost.controller.request.PostInfoCreateRequest;
import hu.helper.bang.center.commpost.enums.ModuleErrorCode;
import hu.helper.bang.center.commpost.service.impl.CommentServiceImpl;
import hu.helper.bang.center.commpost.service.impl.PostServiceImpl;
import hu.helper.bang.center.material.service.model.SubjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/19
 */
@Slf4j
@Service
public class MaterialServiceImpl implements MaterialService {
    @Resource
    PostServiceImpl postService;

    @Resource
    CommentServiceImpl commentService;
    @Resource
    MaterialMapper materialMapper;

    @Override
    public MaterialDTO getMaterial(Long postId) {
        MaterialDO material = materialMapper.getMaterial(postId);
        if(null==material){
            log.error("帖子ID不存在:{}",postId);
            throw new BangException("帖子ID不存在");
        }
        MaterialDTO materialDTO=new MaterialDTO();
        BeanUtils.copyProperties(material,materialDTO);
        materialDTO.setComments(commentService.getComment(postId));
        return materialDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createMaterial(MaterialCreateRequest request) {
        PostInfoCreateRequest postInfoCreateRequest=new MaterialCreateRequest();
        BeanUtils.copyProperties(request,postInfoCreateRequest);
        Long postId= postService.registerPostInfo(postInfoCreateRequest);
        if( postId == ModuleErrorCode.CREATE_POST_IFO_ERROR.getCode()){
            log.error("资料帖子主表入库失败:{}",postInfoCreateRequest);
            throw new BangException("资料帖子主表入库失败: "+ postInfoCreateRequest);
        }
        request.setPostId(postId);
        int material=materialMapper.createMaterial(request);
        if(material!=1){
            log.error("资料帖子从表入库失败:{}",request);
            throw new BangException("资料帖子从表入库失败:"+ request);
        }
        return 1;
    }

    @Override
    public SubjectDTO getSubject() {
        List<SubjectDO> subjectDoList =materialMapper.getSubject();
        SubjectDTO subjectDTO=new SubjectDTO();
        subjectDTO.setSubjectDoList(subjectDoList);
        return subjectDTO;
    }

    @Override
    public int createSubject(SubjectCreateRequest subject) {
        int result=materialMapper.subjectCheck(subject);
        if(result==1){
            log.error("该科目已经存在:{}",subject);
            throw new BangException("该科目已经存在:"+subject.toString());
        }
        return materialMapper.createSubject(subject);
    }
}
