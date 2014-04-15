package com.damintsev.server.dao;

import com.damintsev.common.uientity.Label;

/**
 * @author Damintsev Andrey
 *         27.02.14.
 */
public interface UiItemDao {

    Label loadLabel(Long id);

    Long saveLabel(Label label);

    void deleteLabel(Long id);
}
