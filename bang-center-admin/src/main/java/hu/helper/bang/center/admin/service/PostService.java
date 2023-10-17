package hu.helper.bang.center.admin.service;
import hu.helper.bang.center.admin.dao.model.PostInfo;

import java.util.List;

/**
 * @author WCY
 * @date 2023/04/21
 */

public interface PostService {
    /**
     * 根据 ID 查找 PostInfo 对象。
     *
     * @param id 帖子的唯一标识符
     * @return 如果找到则返回 PostInfo 对象，否则返回 null
     */
    PostInfo findPostById(Long id);
    /**
     * 更新现有的 PostInfo 对象。
     *
     * @param post 要更新的 PostInfo 对象
     * @return 如果更新成功则返回 true，否则返回 false
     */
    boolean updatePost(PostInfo post);
    /**
     * 从数据源获取所有 PostInfo 对象。
     *
     * @return 所有 PostInfo 对象的列表
     */
    List<PostInfo> findAllPosts();
}

