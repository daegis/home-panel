package cn.aegisa.cms.home.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-04-09 15:42
 */
@Data
public class GithubUser implements OAuth2User, Serializable {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public String getName() {
        return this.login;
    }

    private Long id;
    private String email;
    private String avatar_url;
    private String login;
}
