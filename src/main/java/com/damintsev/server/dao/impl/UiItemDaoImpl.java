package com.damintsev.server.dao.impl;

import com.damintsev.common.uientity.Label;
import com.damintsev.server.dao.UiItemDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Damintsev Andrey
 *         27.02.14.
 */
@Component
public class UiItemDaoImpl implements UiItemDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Label loadLabel(Long id) {
        Query query = em.createQuery("SELECT l FROM Label l WHERE l.id = :id");
        query.setParameter("id", id);
        return (Label) query.getSingleResult();
    }

    @Override
    @Transactional
    public Long saveLabel(Label label) {
        label = em.merge(label);
        return label.getId();
    }

    @Override
    @Transactional
    public void deleteLabel(Long id) {
        Label label = em.getReference(Label.class, id);
        em.remove(label);
    }
}
