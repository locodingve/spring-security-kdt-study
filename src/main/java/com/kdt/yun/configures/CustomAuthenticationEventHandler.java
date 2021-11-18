package com.kdt.yun.configures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by yunyun on 2021/11/18.
 */

@Component
public class CustomAuthenticationEventHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Async
    @EventListener
    public void handlerAuthenticationSuccessEvent(AuthenticationSuccessEvent event){
        Authentication authentication = event.getAuthentication();
        log.info("Successful authentication result: {}", authentication.getPrincipal());
    }

    @EventListener
    public void handlerAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event){
        Exception e = event.getException();
        Authentication authentication = event.getAuthentication();
        log.info("Unsuccessful authentication result: {}", authentication, e);
    }
}
