package cn.aegisa.cms.home.config;

import cn.aegisa.cms.home.config.properties.PanelOAuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-04-10 11:53
 */
@Configuration
@EnableConfigurationProperties({
        PanelOAuthProperties.class
})
public class PropertiesConfig {
}
