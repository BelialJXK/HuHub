package hu.helper.bang.center.admin.dao.model;

import lombok.Data;

import java.util.List;

/**
 * @author WCY
 * @date 2023/04/30
 */
@Data
public class RoleInfo {
    private Long id;
    private String roleName;
    private List<String> permissions;
}
