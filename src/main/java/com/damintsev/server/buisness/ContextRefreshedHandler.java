package com.damintsev.server.buisness;

import com.damintsev.server.dao.DefaultUserDetailsCreator;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Author damintsev
 * 4/29/2014
 */
@Component
public class ContextRefreshedHandler implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = Logger.getLogger(ContextRefreshedHandler.class);

    @Autowired
    private DefaultUserDetailsCreator creator;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.debug("onContextRefreshedEvent");
        creator.createDefaultUsers();
        logger.debug("Default users created successfully");
    }
}
