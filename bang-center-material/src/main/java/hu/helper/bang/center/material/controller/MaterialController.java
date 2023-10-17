package hu.helper.bang.center.material.controller;

import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.commpost.enums.PostTypeEnum;
import hu.helper.bang.center.material.controller.request.MaterialCreateRequest;
import hu.helper.bang.center.material.controller.request.SubjectCreateRequest;
import hu.helper.bang.center.material.controller.respond.MaterialVO;
import hu.helper.bang.center.material.controller.respond.SubjectVO;
import hu.helper.bang.center.material.dao.model.SubjectDO;
import hu.helper.bang.center.material.service.MaterialService;
import hu.helper.bang.center.material.service.model.MaterialDTO;
import hu.helper.bang.center.common.result.BangResult;
import hu.helper.bang.center.common.util.BangPreconditions;
import hu.helper.bang.center.material.service.model.SubjectDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/19
 */
@Api(value = "学习资料控制器", tags = {"学习资料相关接口"})
@RestController
@Slf4j
@RequestMapping(value = "/material")
public class MaterialController {
    @Resource
    MaterialService materialService;
    /**
     * 数据库变动数量
     */
    private final int SUCCESS=1;

    @ApiOperation(value = "获取资料帖子详情")
    @GetMapping("/getMaterial")
    public BangResult getMaterial(Long postId) {
        BangPreconditions.checkNotNull(postId);
        MaterialDTO materialDTO=materialService.getMaterial(postId);
        if(null==materialDTO){
            return  BangResult.error("帖子ID不存在");
        }
        MaterialVO materialVO=new MaterialVO();
        BeanUtils.copyProperties(materialDTO,materialVO);
        return BangResult.ok(materialVO);
    }

    @ApiOperation(value = "新建资料帖子")
    @PostMapping("/createMaterial")
    public BangResult createMaterial(@Valid @RequestBody MaterialCreateRequest request) {
        if(!PostTypeEnum.STUDY.getDesc().equals(request.getType())){
            return BangResult.error("帖子类型错误");
        }
        int result=materialService.createMaterial(request);
        if(result==SUCCESS){
            return BangResult.ok("新建资料帖子成功");
        }
        return BangResult.error("新建资料帖子失败");
    }

    @ApiOperation(value = "获取学校资料")
    @GetMapping("/getSubject")
    public BangResult getSubject() {
        SubjectDTO subject =materialService.getSubject();
        if(null==subject){
            return  BangResult.error("查询异常");
        }
        List<SubjectVO> subjectVO=new ArrayList<>();
        dto2vo(subject,subjectVO);
        return BangResult.ok(subjectVO);
    }

    @ApiOperation(value = "新建科目")
    @PostMapping("/createSubject")
    public BangResult createSubject(@Valid @RequestBody SubjectCreateRequest subject) {
        int result=materialService.createSubject(subject);
        if(result==SUCCESS){
            return BangResult.ok("新建科目成功");
        }
        return BangResult.error("新建科目失败");
    }
    public void dto2vo(SubjectDTO subject, List<SubjectVO> subjectVO){
        for (SubjectDO subjectDo : subject.getSubjectDoList()) {
            SubjectVO vo=new SubjectVO();
            BeanUtils.copyProperties(subjectDo,vo);
            subjectVO.add(vo);
        }
    }
}
