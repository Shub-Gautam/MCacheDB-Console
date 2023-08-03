package com.mcachedb.mcachedbconsole.Request;

import java.util.List;
//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBucketList {

    @SerializedName("BucketList")
    @Expose
    private List<String> bucketList;

    public List<String> getBucketList() {
        return bucketList;
    }

    public void setBucketList(List<String> bucketList) {
        this.bucketList = bucketList;
    }

}