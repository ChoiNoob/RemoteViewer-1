package com.damintsev.server.dao;

import com.damintsev.gwt.client.source.uientity.Station;

import java.util.List;

/**
 * @author Damintsev Andrey
 *         15.04.2014.
 */
public interface StationDao {

    List<Station> getStationList();

    Station loadStation(Long id);

    Long saveStation(Station station);
}
