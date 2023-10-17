package hu.helper.bang.center.admin.service.impl;

import hu.helper.bang.center.admin.dao.PostInfoMapper;
import hu.helper.bang.center.admin.dao.model.PostInfo;
import hu.helper.bang.center.admin.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author WCY
 * @date 2023/04/21
 */

@Service("adminPostServiceImpl")
@Slf4j
public class PostServiceImpl implements PostService{
    @Autowired
    private PostInfoMapper postInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostInfo findPostById(Long id) {
        return postInfoMapper.findPostById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePost(PostInfo post) {
        postInfoMapper.updatePost(post);
        return true;
    }

    @Override
    public List<PostInfo> findAllPosts() {
        return postInfoMapper.findAllPosts();
    }


}
