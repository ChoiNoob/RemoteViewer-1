package com.damintsev.server.buisness.uilogic.impl;

import com.damintsev.common.uientity.Label;
import com.damintsev.server.buisness.uilogic.UiBusinessLogic;
import com.damintsev.server.dao.UiItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Damintsev Andrey
 *         27.02.14.
 */
@Component
public class UiBusinessLogicImpl implements UiBusinessLogic {

    @Autowired
    private UiItemDao dao;

    @Override
    public Boolean deleteLabel(Long labelId) {
        return null;//todo implement!!!
    }

    @Override
    public Label loadLabel(Long id) {
        return dao.loadLabel(id);
    }
}
