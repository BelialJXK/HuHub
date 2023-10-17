package hu.helper.bang.center.commpost.controller;

import hu.helper.bang.center.common.result.BangResult;
import hu.helper.bang.center.commpost.service.UtilsService;
import hu.helper.bang.center.commpost.service.VoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 *
 * @author lin
 * @date 2023/03/11
 */
@Api(value = "点赞控制器", tags = {"点赞相关接口"})
@RestController
@Slf4j
@RequestMapping(value = "/vote")
public class VoteController {
    @Resource
    private VoteService voteService;
    @Resource
    private UtilsService utilsService;
    @ApiOperation("测试点赞API前后端接口是否正常")
    @GetMapping("")
    public BangResult test(){
        return BangResult.ok("vote api success");
    }
    @ApiOperation("判断特定用户是否点赞了特定帖子")
    @GetMapping("/judge")
    public BangResult judgeUserVote(Long userId, Long postId){
        if(userId == null || postId == null ){
            return BangResult.error("Neither user id or post id can be null");
        }
        return BangResult.ok("判断用户是否点赞了某个帖子成功").put("is_like",voteService.judgeVote(userId,postId));

    }
    @ApiOperation("切换特定用户针对特定帖子的点赞状态")
    @PostMapping("/changeVote")
    public BangResult changeVote(Long userId, Long postId) {
        if (userId == null || postId == null) {
            return BangResult.error("Neither user id or post id can be null");
        }
        if (voteService.judgeVote(userId, postId)) {
            voteService.deleteVote(userId, postId);
            return BangResult.ok("用户针对此贴的点赞已取消").put("user_id", userId).put("post_id", postId);
        } else {
            if (voteService.userVoteForPost(userId, postId) >= 0) {
                if (utilsService.callNotifyByVote(userId, postId) >= 0) {
                    return BangResult.ok("用户点赞此帖成功").put("user_id", userId).put("post_id", postId);
                } else {
                    return BangResult.ok("用户点赞此帖成功,但通知创建失败,原因可能为post info表内没有对应post信息").put("user_id", userId).put("post_id", postId);
                }
            }
        }
        return BangResult.error("内部系统错误");
    }
    @ApiOperation("获取特定帖子的点赞总数")
    @GetMapping("/number")
    public BangResult getNumberOfVotes(Long postId){
        if(postId == null){
            return  BangResult.error("未给出postId");
        }
        int count = voteService.getNumberOfVotes(postId);
        if(count==-1){
            return BangResult.error("未找到指定的帖子");
        }else {
            return BangResult.ok("指定帖子的点赞总数获取成功").put("count",count);
        }
    }

//    @GetMapping("/createVoteModule")
//    public BangResult createVoteModule(Long postId){
//        voteService.createVoteModule(postId);
//        return BangResult.ok();
//    }

}
