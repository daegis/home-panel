package cn.aegisa.cms.home.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-04-10 11:52
 */
@Data
@ConfigurationProperties(prefix = "panel.oauth")
public class PanelOAuthProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private String authorizeUrl;
    private String accessTokenUrl;
    private String userInfoUrl;
}
