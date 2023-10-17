package hu.helper.bang.center.anon.config;

import hu.helper.bang.center.anon.enums.AnonElement;
import org.springframework.stereotype.Component;

/**
 * @author lin
 * @date 2023/03/21
 */
@Component
public class AnonElementManager {
    private String prefix;

    public void setPrefix(String prefix) {
        if (this.prefix == null) {
            this.prefix = prefix;
            updatePhotoUrls();
        }
    }

    private void updatePhotoUrls() {
        for (AnonElement element : AnonElement.values()) {
            element.updatePhotoUrl(prefix);
        }
    }
}
