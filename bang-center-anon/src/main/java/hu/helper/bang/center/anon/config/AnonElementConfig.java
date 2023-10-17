package hu.helper.bang.center.anon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author lin
 */
@Configuration
public class AnonElementConfig {

    @Autowired
    private AnonPrefixConfig anonPrefixConfig;

    @Autowired
    private AnonElementManager anonElementManager;

    @PostConstruct
    public void init() {
        anonElementManager.setPrefix(anonPrefixConfig.getPrefix());
    }
}