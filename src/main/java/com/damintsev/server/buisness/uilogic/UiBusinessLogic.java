package com.damintsev.server.buisness.uilogic;

import com.damintsev.common.uientity.Label;

/**
 * @author Damintsev Andrey
 *         27.02.14.
 */
public interface UiBusinessLogic {

    Boolean deleteLabel(Long labelId);

    Label loadLabel(Long id);
}
