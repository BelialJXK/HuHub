package hu.helper.bang.center.material.dao;


import hu.helper.bang.center.material.controller.request.MaterialCreateRequest;
import hu.helper.bang.center.material.controller.request.SubjectCreateRequest;
import hu.helper.bang.center.material.dao.model.MaterialDO;
import hu.helper.bang.center.material.dao.model.SubjectDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/19
 */
@Mapper
public interface MaterialMapper {
    String TABLE_INFO = " bang_center_post_info_study_material ";
    String FIELD_INFO = "  post_id,gmt_create,gmt_modified,university,major,subject,file_url ";

    /**
     * 新增学习资料从表记录
     *
     * @param request 学习资料请求类
     * @return 新增成功数量
     */
    @Insert("insert into" + TABLE_INFO + "(" + FIELD_INFO + ") "
            + "values (#{postId}, now(), now(), #{university}, #{major},  #{subject}, #{fileUrl})")
    int createMaterial(MaterialCreateRequest request);

    /**
     * 获取资料帖子详情
     *
     * @param postId 帖子id
     * @return 学习资料返回类
     */
    @Select(" select p.post_id,p.title,p.content,p.type,p.gmt_modified,m.file_url,u.nickname,u.avatar_url" +
            " from bang_center_post_info p" +
            " left join bang_center_post_info_study_material m on m.post_id=p.post_id" +
            " left join bang_center_user_info u on p.user_id=u.id " +
            " where p.post_id=#{postId}")
    MaterialDO getMaterial(Long postId);

    /**
     * 获取学校信息
     *
     * @return 学校数据
     */
    @Select("select * from bang_center_post_university")
    List<SubjectDO> getSubject();

    /**
     * 新建科目
     *
     * @param subject 学校信息类
     * @return 成功数量
     */
    @Insert("insert into bang_center_post_university (gmt_create, gmt_modified,university,major,subject) "
            + "values (now(),now(), #{university}, #{major},  #{subject})")
    int createSubject(SubjectCreateRequest subject);

    /**
     * 检查科目信息
     *
     * @param subject 学校信息请求体
     * @return 学校数据
     */
    @Select("select count(1) from bang_center_post_university where university=#{university} and major=#{major}" +
            " and subject=#{subject}")
    int subjectCheck(SubjectCreateRequest subject);
}
