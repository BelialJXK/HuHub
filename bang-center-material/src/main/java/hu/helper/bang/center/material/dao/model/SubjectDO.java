package hu.helper.bang.center.material.dao.model;

import lombok.Data;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/19
 */
@Data
public class SubjectDO {
    /**
     * 学校
     */
    private String university;
    /**
     * 专业
     */
    private String major;
    /**
     * 科目
     */
    private String subject;
}
