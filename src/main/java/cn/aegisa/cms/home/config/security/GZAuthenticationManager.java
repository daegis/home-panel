package cn.aegisa.cms.home.config.security;

import cn.aegisa.cms.home.config.properties.PanelOAuthProperties;
import cn.aegisa.cms.home.vo.GithubUser;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-04-10 11:07
 */
@Component
@Slf4j
public class GZAuthenticationManager implements AuthenticationManager {

    @Autowired
    private PanelOAuthProperties p;

    private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String accessToken = auth.getName();
        String userInfoUrl = p.getUserInfoUrl();
        userInfoUrl = String.format(userInfoUrl, accessToken);
        String userJson = HttpUtil.get(userInfoUrl);
        GithubUser githubUser = JSON.parseObject(userJson, GithubUser.class);
        log.info("login user:{}", JSON.toJSONString(githubUser));
        return new UsernamePasswordAuthenticationToken(githubUser, null, AUTHORITIES);
    }
}
