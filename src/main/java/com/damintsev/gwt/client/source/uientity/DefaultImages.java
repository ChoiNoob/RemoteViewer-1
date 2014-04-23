package com.damintsev.gwt.client.source.uientity;

/**
 * @author Damintsev Andrey
 *         24.01.14.
 */
public enum  DefaultImages {
    STATION(1L), TASK(2L), LABEL(3L);

    private Long value;

    private DefaultImages(Long defaults) {
            this.value = defaults;
    }

    public Long getValue() {
        return value;
    }
}
