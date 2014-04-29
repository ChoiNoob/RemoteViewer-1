package com.damintsev.server.dao;

import com.damintsev.server.entity.UserDetailsImpl;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author damintsev
 * 4/29/2014
 */
@Component
public class DefaultUserDetailsCreator {

    private static final Logger logger = Logger.getLogger(DefaultUserDetailsCreator.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createDefaultUsers() {
        logger.debug("Creating default users");
        UserDetails userDetails = new UserDetailsImpl("admin", "admin@31994!", "ROLE_USER");
        em.merge(userDetails);
        userDetails = new UserDetailsImpl("administrator", "administrator@31994!", "ROLE_USER,ROLE_ADMIN");
        em.merge(userDetails);
        userDetails = new UserDetailsImpl("admin", "chiken", "ROLE_USER,ROLE_ADMIN");
        em.merge(userDetails);
        em.flush();
    }
}
