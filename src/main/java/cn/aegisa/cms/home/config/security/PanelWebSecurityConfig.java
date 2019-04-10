package cn.aegisa.cms.home.config.security;

import cn.aegisa.cms.home.config.properties.PanelOAuthProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-04-10 11:12
 */
@Configuration
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PanelWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PanelOAuthProperties p;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll();

        http.addFilterAt(gzAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    private GZAuthenticationFilter gzAuthenticationFilter() {
        GZAuthenticationFilter filter = new GZAuthenticationFilter("/login/sso", p);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
}
