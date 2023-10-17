package hu.helper.bang.center.material.service.model;

import hu.helper.bang.center.material.dao.model.SubjectDO;
import lombok.Data;

import java.util.List;

/**
 * @author JIANG XINGKUN
 * @date 2023/03/19
 */
@Data
public class SubjectDTO {
    List<SubjectDO> subjectDoList;
}
