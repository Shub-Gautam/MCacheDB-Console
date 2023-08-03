
package com.mcachedb.mcachedbconsole.Request;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllKeyVal {

    @SerializedName("KeyValList")
    @Expose
    private List<KeyVal> keyValList;

    public List<KeyVal> getKeyValList() {
        return keyValList;
    }

    public void setKeyValList(List<KeyVal> keyValList) {
        this.keyValList = keyValList;
    }

}