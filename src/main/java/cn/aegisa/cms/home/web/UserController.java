package cn.aegisa.cms.home.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-04-09 17:21
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/get")
    @ResponseBody
    public Object getUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);
        return principal;
    }
}
