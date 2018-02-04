package conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import security.ApiAuthenticationFilter;
import security.handlers.*;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "conf")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // permit, deny acces to specific url-s
        http.authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/logout").permitAll()
                .antMatchers("/api/units2/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/users/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/**").hasRole("ADMIN");
        // other configurations

        http.logout().logoutUrl("/api/logout")
                .logoutSuccessHandler(new ApiLogoutSuccessHandler());

        http.addFilterAfter(restLoginFilter("/api/login"),
                LogoutFilter.class);

        http.exceptionHandling().authenticationEntryPoint(new ApiEntryPoint());
        http.exceptionHandling().accessDeniedHandler(new ApiAccessDeniedHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure user and password info

        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder());

    }

    public Filter restLoginFilter(String url) throws Exception {
        ApiAuthenticationFilter filter = new ApiAuthenticationFilter(url);
        filter.setAuthenticationManager(authenticationManager());

        // add success and failure handlers
        filter.setAuthenticationSuccessHandler(new ApiAuthSuccessHandler());
        filter.setAuthenticationFailureHandler(new ApiAuthFailureHandler());

        return filter;
    }
}
