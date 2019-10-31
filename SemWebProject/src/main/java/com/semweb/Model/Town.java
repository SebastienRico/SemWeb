package com.semweb.Model;

import java.util.List;
import lombok.Data;

@Data
public class Town {
    String id;
    String nom;
    int departement;
    Double latitude;
    Double longitude;
    List<Station> stations;
}
