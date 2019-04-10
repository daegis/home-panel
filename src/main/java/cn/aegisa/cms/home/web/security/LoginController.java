package cn.aegisa.cms.home.web.security;

import cn.aegisa.cms.home.config.properties.PanelOAuthProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-04-10 11:36
 */
@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private PanelOAuthProperties p;

    @RequestMapping
    public void toLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.print("cookie.getName() = " + cookie.getName());
                System.out.println("|cookie.getValue() = " + cookie.getValue());
            }
        }
        String uuid = UUID.randomUUID().toString();
        session.setAttribute("state", uuid);
        String authorizeUrl = p.getAuthorizeUrl();
        String redirectPage = String.format(authorizeUrl, p.getClientId(), p.getRedirectUrl(), uuid);
        response.sendRedirect(redirectPage);
    }

    @RequestMapping("/aaa")
    @ResponseBody
    public String toLoginPage1() {
        return "1231231login/forward";
    }
}
