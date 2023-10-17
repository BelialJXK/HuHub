package hu.helper.bang.center.secondhand.controller;

import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.common.result.BangResult;
import hu.helper.bang.center.common.util.BangPreconditions;
import hu.helper.bang.center.commpost.enums.PostTypeEnum;
import hu.helper.bang.center.secondhand.controller.request.SeconderhandCreateRequest;
import hu.helper.bang.center.secondhand.controller.respond.SeconderhandVO;
import hu.helper.bang.center.secondhand.service.SeconderhandService;
import hu.helper.bang.center.secondhand.service.model.SeconderhandDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/20
 */
@Api(value = "二手交易控制器", tags = {"二手交易相关接口"})
@RestController
@Slf4j
@RequestMapping(value = "/seconderhand")
public class SeconderhandController {
    @Resource
    SeconderhandService seconderhandService;
    /**
     * 数据库变动数量
     */
    private final int SUCCESS=1;

    @ApiOperation(value = "获取资料帖子详情")
    @GetMapping("/getSeconderhand")
    public BangResult getSeconderhand(Long postId) {
        BangPreconditions.checkNotNull(postId);
        SeconderhandDTO seconderhandDTO=seconderhandService.getSeconderhand(postId);
        if(null==seconderhandDTO){
            return  BangResult.error("帖子ID不存在");
        }
        SeconderhandVO seconderhandVO=new SeconderhandVO();
        BeanUtils.copyProperties(seconderhandDTO,seconderhandVO);
        return BangResult.ok(seconderhandVO);
    }

    @ApiOperation(value = "新建资料帖子")
    @PostMapping("/createSeconderhand")
    public BangResult createMaterial(@Valid @RequestBody SeconderhandCreateRequest request) {
        if(!PostTypeEnum.SECOND.getDesc().equals(request.getType())){
            return BangResult.error("帖子类型错误");
        }
        int result=seconderhandService.createSeconderhand(request);
        if(result==SUCCESS){
            return BangResult.ok("新建资料帖子成功");
        }
        return BangResult.error("新建资料帖子失败");
    }


}
