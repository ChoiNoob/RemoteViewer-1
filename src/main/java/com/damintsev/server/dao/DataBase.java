package com.damintsev.server.dao;

import com.damintsev.common.beans.Station;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: adamintsev
 * Date: 21.01.14
 * //todo написать комментарии
 */
@Repository
@Qualifier("DataBaseImpl")
public interface DataBase {

    List<Station> getStationList();
}
