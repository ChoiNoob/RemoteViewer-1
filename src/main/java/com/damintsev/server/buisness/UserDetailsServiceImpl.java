package com.damintsev.server.buisness;

import com.damintsev.server.entity.UserDetailsImpl;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.Timer;

/**
 * Author damintsev
 * 4/25/2014
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
        logger.debug("loadUserByUsername=" + username);
        Query query = em.createQuery("SELECT u FROM UserDetailsImpl u WHERE  u.username like :username");
        query.setParameter("username", username);
        try {
            return (UserDetailsImpl) query.getSingleResult();
        } catch (NoResultException e) {
            logger.debug("User with name {} not found" + username);
            throw new UsernameNotFoundException("User with name " + username + "not found");
        }
    }

    @Transactional
    public void createDefaultUsers() {
        UserDetails userDetails = new UserDetailsImpl("admin", "admin@31994!", "ROLE_USER");
        em.merge(userDetails);
        userDetails = new UserDetailsImpl("administrator", "administrator@31994!", "ROLE_USER,ROLE ADMIN");
        em.merge(userDetails);
        em.flush();
    }
}
