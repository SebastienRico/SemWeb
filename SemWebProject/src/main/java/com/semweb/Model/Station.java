package com.semweb.Model;

import lombok.Data;

@Data
public class Station {
    String id;
    String name;
    Integer availableBikeStand;
    Integer availableBike;
    Integer bikeStand;
    Town town;
    String lastUpdate;
    StatusStation status;
    Double latitude;
    Double longitude;
    String address;
    
    public Station(){
        town = new Town();
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("id : " + id)
                .append("name : " + name);
        return s.toString();
    }
}
