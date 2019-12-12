package com.semweb.Model;

import lombok.Data;

@Data
public class Station {

    String id;
    String name;
    Integer availableBikeStand;
    Integer availableBike;
    Integer bikeStand;
    String city;
    String lastUpdate;
    StatusStation status;
    Double latitude;
    Double longitude;
    String address;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("id : " + id)
                .append("name : " + name)
                .append(" availableBikeStand : " + availableBikeStand)
                .append(" availableBike : " + availableBike)
                .append(" bikeStand : " + bikeStand)
                .append(" lastUpdate : " + lastUpdate)
                .append(" status : " + status)
                .append(" latitude : " + latitude)
                .append(" longitude : " + longitude)
                .append(" address : " + address);
        return s.toString();
    }
}
