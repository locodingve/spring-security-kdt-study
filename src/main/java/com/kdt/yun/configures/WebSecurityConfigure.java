package com.kdt.yun.configures;

import org.apache.commons.io.file.NoopPathVisitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by yunyun on 2021/11/11.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}user123")
                .roles("USER");
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}admin123")
                .roles("ADMIN");
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                    .antMatchers("/me").hasAnyRole("USER","ADMIN")
                    .anyRequest().permitAll()
                    .and()
                .formLogin()
                    .defaultSuccessUrl("/")
                    .permitAll()
                    .and()
                .rememberMe()
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(5 * 60)
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true) // 기본 값이 true 이다.
                    .clearAuthentication(true);  // 기본 값이 true 이다.
    }

}
