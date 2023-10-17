package hu.helper.bang.center.commpost.service;

import hu.helper.bang.center.commpost.controller.request.PostInfoCreateRequest;
import hu.helper.bang.center.commpost.dao.model.PostInfoDo;

import java.util.Date;
import java.util.List;

/**
 * @author lin
 * @date 2023/03/17
 */
public interface PostService {
    /**
     * 登记post信息到post info表，如果不是normal post，创建新post时需要调用此接口
     * @param postInfoCreateRequest  hu.helper.bang.center.commpost.controller.request.PostInfoCreateRequest
     * @return int
     */
    Long registerPostInfo (PostInfoCreateRequest postInfoCreateRequest);

    /**
     * 获得指定时间戳前最新的未删除的number数量的post info，如果未给出时间戳，则返回最新的未删除的number数量的post info
     *
     * @param number 想要获取的post数量
     * @param timestamp 想要获取的指定时间点
     * @return {@link List}<{@link PostInfoDo}>
     */
    List<PostInfoDo> getPostInfo(int number, Date timestamp);
}
