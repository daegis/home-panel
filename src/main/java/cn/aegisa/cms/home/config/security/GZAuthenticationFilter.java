package cn.aegisa.cms.home.config.security;

import cn.aegisa.cms.home.config.properties.PanelOAuthProperties;
import cn.hutool.http.Header;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-04-10 10:51
 */
@Slf4j
public class GZAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private PanelOAuthProperties p;

    GZAuthenticationFilter(String defaultFilterProcessesUrl, PanelOAuthProperties properties) {
        super(defaultFilterProcessesUrl);
        this.p = properties;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CookieTheftException("无效cookie");
        }
        Object sessionState = session.getAttribute("state");
        if (!Objects.equals(state, sessionState)) {
            throw new SessionAuthenticationException("非法请求");
        }
        String accessTokenUrl = p.getAccessTokenUrl();
        accessTokenUrl = String.format(accessTokenUrl,
                p.getClientId(),
                p.getClientSecret(),
                code,
                p.getRedirectUrl(),
                state);
        try {
            HttpResponse resp = HttpUtil.createPost(accessTokenUrl).header(Header.ACCEPT, "application/json").execute();
            String post = resp.body();
            JSONObject jsonObject = JSON.parseObject(post);
            String accessToken = jsonObject.getString("access_token");
            if (!StringUtils.isEmpty(accessToken)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(accessToken, "");
                return this.getAuthenticationManager().authenticate(token);
            }
        } catch (Exception e) {
            throw new SessionAuthenticationException("非法请求");
        }
        return null;
    }
}
