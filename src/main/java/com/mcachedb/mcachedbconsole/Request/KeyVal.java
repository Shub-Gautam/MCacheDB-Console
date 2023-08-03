package com.mcachedb.mcachedbconsole.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeyVal {

    @SerializedName("rowId")
    @Expose
    private String rowId;
    @SerializedName("keyValuesMap")
    @Expose
    private KeyValuesMap keyValuesMap;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public KeyValuesMap getKeyValuesMap() {
        return keyValuesMap;
    }

    public void setKeyValuesMap(KeyValuesMap keyValuesMap) {
        this.keyValuesMap = keyValuesMap;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}