package com.mcachedb.mcachedbconsole.Request;
// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class GetDBsList {
        @JsonProperty("MyArray")
        public ArrayList<String> myArray;

    public GetDBsList(ArrayList<String> myArray) {
        this.myArray = myArray;
    }

    public ArrayList<String> getMyArray() {
        return myArray;
    }

    public void setMyArray(ArrayList<String> myArray) {
        this.myArray = myArray;
    }
}
