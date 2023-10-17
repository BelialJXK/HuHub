package hu.helper.bang.center.anon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lin
 * @date 2023/03/21
 */
@Component
public class AnonPrefixConfig {
    @Value("${anon.prefix}")
    private String prefix;

    public String getPrefix() {
        return prefix;
    }
}
