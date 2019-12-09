package com.semweb.Controller;

import com.semweb.Model.Chrono;
import com.semweb.utilities.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        //JenaFusekiConnexion.runFusekiServer();
        JenaFusekiConnexion.connectToFuseki();
        //JenaFusekiConnexion.closeConnexionToFuseki();
        Chrono chrono = new Chrono();
        chrono.start();
        //ConfigDataSources.getCitiesName();
    }

}
