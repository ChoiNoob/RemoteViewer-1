package com.damintsev.utils;

import com.sencha.gxt.data.shared.loader.ListLoadResult;

import java.util.List;

/**
 * Created by adamintsev
 * Date: 14.08.13 16:02
 */
public class ListLoadResultImpl<T> implements ListLoadResult<T> {

    private List<T> data;

    public void setData(List<T> data) {
       this.data = data;
    }

    public List<T> getData() {
        return data;
    }
}
