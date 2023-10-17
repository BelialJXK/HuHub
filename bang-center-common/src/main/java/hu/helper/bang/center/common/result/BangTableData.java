package hu.helper.bang.center.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Author : Luo Siwei
 * @Date : 2023/3/9 10:30
 * @Description : 分页数据实体类
 */
@Data
@ToString
@NoArgsConstructor
public class BangTableData<T> {
    /**
     * 表格数据
     */
    private List<T> tableData;

    /**
     * 分页信息
     */
    private BangPaging paging;

    public BangTableData(List<T> tableData, BangPaging paging) {
        this.tableData = tableData;
        this.paging = paging;
    }
}
