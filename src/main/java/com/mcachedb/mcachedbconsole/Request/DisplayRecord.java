package com.mcachedb.mcachedbconsole.Request;

public class DisplayRecord {
    String Id;
    String Key;
    String Value ;
    String CreatedAt ;

    @Override
    public String toString() {
        return "DisplayRecord{" +
                "Id='" + Id + '\'' +
                ", Key='" + Key + '\'' +
                ", Value='" + Value + '\'' +
                ", CreatedAt='" + CreatedAt + '\'' +
                '}';
    }

    public String getId() {
        return Id;
    }

    public String getKey() {
        return Key;
    }

    public String getValue() {
        return Value;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public DisplayRecord(String id, String key, String value, String createdAt) {
        Id = id;
        Key = key;
        Value = value;
        CreatedAt = createdAt;
    }


}
