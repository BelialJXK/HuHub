package hu.helper.bang.center.material.service;

import hu.helper.bang.center.material.controller.request.MaterialCreateRequest;
import hu.helper.bang.center.material.controller.request.SubjectCreateRequest;
import hu.helper.bang.center.material.service.model.MaterialDTO;
import hu.helper.bang.center.material.service.model.SubjectDTO;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/19
 */
public interface MaterialService {
    /**
     * 获取资料帖子详情
     * @param postId 帖子id
     * @return 资料返回类
     */
    MaterialDTO getMaterial(Long postId);

    /**
     * 新建资料帖子
     * @param request 学习资料请求类
     * @return 成功数量
     */
    int  createMaterial(MaterialCreateRequest request);

    /**
     * 获取学校信息
     * @return 学校数据
     */
    SubjectDTO getSubject();

    /**
     * 新建科目
     * @param subject 学校信息类
     * @return 成功数量
     */
    int createSubject(SubjectCreateRequest subject);
}
