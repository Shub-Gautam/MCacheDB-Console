package com.mcachedb.mcachedbconsole.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeyValuesMap {

    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("Key")
    @Expose
    private String key;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}