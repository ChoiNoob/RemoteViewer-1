package com.damintsev.server.dao.impl;

import com.damintsev.gwt.client.source.uientity.Station;
import com.damintsev.server.dao.StationDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Damintsev Andrey
 *         15.04.2014.
 */
@Repository
public class StationDaoImpl implements StationDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Station loadStation(Long id) {
        Query query = em.createQuery("SELECT s FROM Station s WHERE s.id = :id");
        query.setParameter("id", id);
        return (Station) query.getSingleResult();
    }

    @Override
    @Transactional
    public Long saveStation(Station station) {
        station = em.merge(station);
        return station.getId();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Station> getStationList() {
        Query query = em.createQuery("SELECT s FROM Station s");
        return (List<Station>) query.getResultList();
    }
}
