package com.damintsev.common.beans;

/**
 * @author Damintsev Andrey
 *         24.01.14.
 */
public enum  DefaultImages {
    STATION(1L), TASK(2L);

    private Long value;
    private DefaultImages(Long defaults) {
            this.value = defaults;
    }

    public Long getValue() {
        return value;
    }
}
