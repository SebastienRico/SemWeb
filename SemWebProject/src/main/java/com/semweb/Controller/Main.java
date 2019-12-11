package com.semweb.Controller;

import com.semweb.Model.Chrono;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    
    public static void main(String[] args) throws MalformedURLException {
        SpringApplication.run(Main.class, args);
        //JenaFusekiConnexion.runFusekiServer();
        JenaFusekiConnexion.connectToFuseki();
        //Chrono chrono = new Chrono();
        //chrono.start();
        JSONController.parseJSONDatas();
        //JenaFusekiConnexion.closeConnexionToFuseki();
    }

}
