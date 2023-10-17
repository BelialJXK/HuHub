package hu.helper.bang.center.commpost.controller.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author lin
 * @date 2023/03/17
 */
@Data
@ApiModel
@JsonIgnoreProperties(ignoreUnknown=true)
public class PostInfoCreateRequest {
    @NotNull
    Long userId;
    @NotNull
    String title;
    @NotNull
    String content;
    @NotNull
    String type;
    Map<String, Object> photos;

}
