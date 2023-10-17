package hu.helper.bang.center.active.controller;

import hu.helper.bang.center.active.controller.request.ActiveCreateRequest;
import hu.helper.bang.center.active.controller.respond.ActiveVO;
import hu.helper.bang.center.active.dao.model.ActiveDO;
import hu.helper.bang.center.active.service.ActiveService;
import hu.helper.bang.center.active.service.model.ActiveDTO;
import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.common.result.BangResult;
import hu.helper.bang.center.common.util.BangPreconditions;
import hu.helper.bang.center.commpost.enums.PostTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/20
 */
@Api(value = "二手交易控制器", tags = {"二手交易相关接口"})
@RestController
@Slf4j
@RequestMapping(value = "/active")
public class ActiveController {
    @Resource
    ActiveService activeService;
    /**
     * 数据库变动数量
     */
    private final int SUCCESS = 1;

    @ApiOperation(value = "获取活动帖子详情")
    @GetMapping("/getActive")
    public BangResult getActive(Long postId) {
        BangPreconditions.checkNotNull(postId);
        ActiveDTO activeDTO = activeService.getActive(postId);
        if (null == activeDTO) {
            return BangResult.error("帖子ID不存在");
        }
        ActiveVO activeVO = new ActiveVO();
        BeanUtils.copyProperties(activeDTO, activeVO);
        return BangResult.ok(activeVO);
    }

    @ApiOperation(value = "新建活动帖子")
    @PostMapping("/createActive")
    public BangResult createMaterial(@Valid @RequestBody ActiveCreateRequest request) {
        if(!PostTypeEnum.ACTIVE.getDesc().equals(request.getType())){
            return BangResult.error("帖子类型错误");
        }
        ActiveDO activeDO=new ActiveDO();
        BeanUtils.copyProperties(request,activeDO);
        isValidDate(request.getActiveStartTime(),request.getActiveEndTime(),activeDO);
        int result = activeService.createActive(activeDO);
        if (result == SUCCESS) {
            return BangResult.ok("新建活动帖子成功");
        }
        return BangResult.error("新建活动帖子失败");
    }

    public void isValidDate(String start,String end,ActiveDO activeDO) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            Date startTime = format.parse(start);
            Date endTime = format.parse(end);
            if(startTime.getTime()<endTime.getTime()){
                activeDO.setActiveStartTime(startTime);
                activeDO.setActiveEndTime(endTime);
            }else {
                log.error("开始日期要小于结束日期，开始:{},结束:{}",startTime,endTime);
                throw new BangException("开始日期要小于结束日期");
            }
        } catch (Exception e) {
            log.error("时间格式错误：{}",e.getMessage());
            throw new BangException("时间错误:"+e.getMessage()+"，请传正确时间,格式: yyyy/MM/dd HH:mm ");
        }
    }
}
