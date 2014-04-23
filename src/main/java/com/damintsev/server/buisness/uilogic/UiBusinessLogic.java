package com.damintsev.server.buisness.uilogic;

import com.damintsev.gwt.client.source.uientity.Label;
import com.damintsev.gwt.client.source.uientity.Station;

import java.util.List;

/**
 * @author Damintsev Andrey
 *         27.02.14.
 */
public interface UiBusinessLogic {

    void deleteLabel(Long labelId);

    Label loadLabel(Long id);

    Station getStation(Long id);

    List<Station> getStationList();
}
