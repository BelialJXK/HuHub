package hu.helper.bang.center.user.dao.model;

import lombok.*;

import java.util.Date;

/**
 * @Author : Luo Siwei
 * @Date : 2023/2/5 16:16
 * @Description :
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestDo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 测试值
     */
    private String testVarchar;
}
