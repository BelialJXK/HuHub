package hu.helper.bang.center.commpost.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.helper.bang.center.common.result.BangResult;
import hu.helper.bang.center.commpost.controller.request.PostInfoCreateRequest;
import hu.helper.bang.center.commpost.controller.request.PostInfoGetRequest;
import hu.helper.bang.center.commpost.dao.model.PostInfoDo;
import hu.helper.bang.center.commpost.enums.ModuleErrorCode;
import hu.helper.bang.center.commpost.service.PostService;
import hu.helper.bang.center.commpost.service.VoteService;
import hu.helper.bang.center.commpost.utils.UtilsTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lin
 * @date 2023/03/17
 */
@Api(value = "普通帖子和帖子通用模块控制器", tags = {"帖子通用接口"})
@RestController
@Slf4j
@RequestMapping(value = "/post")
public class PostController {
    @Resource
    PostService postService;
    @Resource
    VoteService voteService;
    @GetMapping("")
    public BangResult test(){
        return BangResult.ok("Common post model api success");
    }

    @ApiOperation(value = "新建普通帖子")
    @PostMapping("/createPost")
    public BangResult createNormalPost(@Valid @RequestBody Map<String, Object> requestMap){
        String typeValue = requestMap.get("type").toString();
        if(!UtilsTools.judgeType(typeValue)){
            return BangResult.error("错误的type类型,请查看type类型-String对照表");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        PostInfoCreateRequest postInfoCreateRequest = objectMapper.convertValue(requestMap,PostInfoCreateRequest.class);
        if(postInfoCreateRequest.getPhotos()!=null) {
            Map<String, Object> photoMaps = new HashMap<>();
            for (Map.Entry<String, Object> entry : postInfoCreateRequest.getPhotos().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                photoMaps.put(key, value);
            }
            postInfoCreateRequest.setPhotos(photoMaps);
        }
        Long result = postService.registerPostInfo(postInfoCreateRequest);
        if( result == ModuleErrorCode.CREATE_POST_IFO_ERROR.getCode()){
            return BangResult.error(ModuleErrorCode.CREATE_POST_IFO_ERROR.getDesc());
        }else if(result== ModuleErrorCode.VOTE_MODULE_ERROR.getCode()){
            return BangResult.error(ModuleErrorCode.VOTE_MODULE_ERROR.getDesc());
        }
        return BangResult.ok("新建普通帖子成功");

    }

    @ApiOperation(value = "获得帖子")
    @PostMapping("/getPost")
    public BangResult getPosts(@Valid @RequestBody PostInfoGetRequest postInfoGetRequest){
       List<PostInfoDo> postInfoDoList = postService
               .getPostInfo(postInfoGetRequest.getNumber(),postInfoGetRequest.getTimestamp());
        if(postInfoGetRequest.getTimestamp() == null) {
           return BangResult.ok("帖子获取成功").put("number", postInfoGetRequest.getNumber())
                   .put("timestamp","数据库当前时间")
                    .put("posts",postInfoDoList);
       }else {
            return BangResult.ok("帖子获取成功").put("number", postInfoGetRequest.getNumber())
                    .put("timestamp",postInfoGetRequest.getTimestamp())
                    .put("posts",postInfoDoList);
        }
    }


}
