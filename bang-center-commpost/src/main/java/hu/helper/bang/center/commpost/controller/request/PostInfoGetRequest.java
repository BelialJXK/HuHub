package hu.helper.bang.center.commpost.controller.request;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author lin
 * @date 2023/03/18
 */
@Data
@ApiModel
public class PostInfoGetRequest {
    /**
     * 你想要获得的post info数量
     */
    @NotNull
    int number;

    /**
     * 时间戳，如果未给出则会返回最新的未删除number数量的post info，如果给出了，则返回这个时间前后最新的未删除的number数量的post info
     */
    Date timestamp;
}
