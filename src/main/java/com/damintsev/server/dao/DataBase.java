package com.damintsev.server.dao;

import com.damintsev.common.beans.Station;
import com.damintsev.server.entity.Image;
import org.springframework.beans.factory.annotation.Qualifier;
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

    Image getImage(Long id);
}
