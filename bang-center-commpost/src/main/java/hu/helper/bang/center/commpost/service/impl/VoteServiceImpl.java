package hu.helper.bang.center.commpost.service.impl;

import hu.helper.bang.center.commpost.dao.VoteMapper;
import hu.helper.bang.center.commpost.dao.model.InteractionDo;
import hu.helper.bang.center.commpost.dao.model.VoteDo;
import hu.helper.bang.center.commpost.service.VoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 *
 * @author lin
 * @date 2023/03/11
 */
@Service
public class VoteServiceImpl implements VoteService {
    @Resource
    VoteMapper voteMapper;
    @Override
    public Boolean judgeVote(Long userId,Long postId){
        List<VoteDo> voteDoList = voteMapper.allPostUserVoted(userId);
        return voteDoList.stream().anyMatch(item-> item.getPostId().equals(postId));
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteVote(Long userId,Long postId){
        return voteMapper.deleteFromVote(userId,postId) +
                    voteMapper.deleteFromInteraction(postId);

    }
    @Override
    public int userVoteForPost(Long userId, Long postId){
        return voteMapper.addToInteraction(postId) +
                    voteMapper.addToVote(userId,postId);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createVoteModule(Long postId){
        return voteMapper.newInteractionItem(postId);
    }

    @Override
    public int getNumberOfVotes(Long postId){
        InteractionDo interactionDo = voteMapper.getInteractionItem(postId);
        if(interactionDo == null){
            return -1;
        }else{
            return interactionDo.getCount();
        }
    }
}
