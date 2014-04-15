package com.damintsev.server.buisness.uilogic.impl;

import com.damintsev.common.uientity.Label;
import com.damintsev.common.uientity.Station;
import com.damintsev.server.buisness.uilogic.UiBusinessLogic;
import com.damintsev.server.dao.StationDao;
import com.damintsev.server.dao.UiItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Damintsev Andrey
 *         27.02.14.
 */
@Component
public class UiBusinessLogicImpl implements UiBusinessLogic {

    @Autowired
    private UiItemDao dao;

    @Autowired
    private StationDao stationDao;

    @Override
    public void deleteLabel(Long labelId) {
        dao.deleteLabel(labelId);
    }

    @Override
    public Label loadLabel(Long id) {
        return dao.loadLabel(id);
    }

    @Override
    public Station getStation(Long id) {
        return stationDao.loadStation(id);
    }

    @Override
    public List<Station> getStationList() {
        return stationDao.getStationList();
    }
}
