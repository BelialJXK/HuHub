package hu.helper.bang.center.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author : Luo Siwei
 * @Date : 2023/3/9 10:29
 * @Description : 分页实体类
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BangPaging {

    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 总数量
     */
    private Integer totalCount;

    /**
     * 单页数量
     */
    private Integer pageSize;
}
