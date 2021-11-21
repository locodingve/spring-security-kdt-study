package com.kdt.yun.configures;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Created by yunyun on 2021/11/11.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    public WebSecurityConfigure(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                    "SELECT " +
                        "login_id, passwd, true " +
                    "FROM " +
                        "USERS " +
                    "WHERE " +
                        "login_id = ?"
                )
                .groupAuthoritiesByUsername(
                    "SELECT " +
                        "u.login_id, g.name, p.name " +
                    "FROM " +
                        "users u " +
                            "JOIN groups g ON u.group_id = g.id " +
                            "LEFT JOIN group_permission gp ON g.id = gp.group_id " +
                            "JOIN permissions p ON p.id = gp.permission_id " +
                    "WHERE " +
                        "u.login_id = ?"
                )
                .getUserDetailsService().setEnableAuthorities(false)
        ;
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/assets/**", "/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                    .antMatchers("/me").hasAnyRole("USER","ADMIN")
                    .antMatchers("/admin").access("isFullyAuthenticated() and hasRole('ADMIN')") // remember-me 허용 안함
                    .anyRequest().permitAll()
                    .and()
                .formLogin()
                    .defaultSuccessUrl("/")
                    .permitAll()
                    .and()
                .httpBasic()
                    .and()
                .rememberMe()
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(5 * 60)
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true) // 기본 값이 true 이다.
                    .clearAuthentication(true) // 기본 값이 true 이다.
                    .and()
                .requiresChannel()
                    .anyRequest().requiresSecure()
                    .and()
                .sessionManagement()
                    .sessionFixation().changeSessionId()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .invalidSessionUrl("/")
                    .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .and()
                    .and()
        ;
    }

}
