package com.semweb.Model;

import lombok.Data;

@Data
public class Station {
    String id;
    String nom;
    Double latitude;
    Double longitude;
    int capacite;
    int nb_velo_disp;
    boolean indisponible;
    String commentaire;
    Town town;
}
