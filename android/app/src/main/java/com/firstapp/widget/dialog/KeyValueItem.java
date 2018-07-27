package com.firstapp.widget.dialog;

import java.io.Serializable;

/**
 * Description:
 * Created by song on 2018/5/7.
 * email：bjay20080613@qq.com
 */

public class KeyValueItem implements Serializable {
    private String key;
    private String value;

    public KeyValueItem() {
    }

    public KeyValueItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
