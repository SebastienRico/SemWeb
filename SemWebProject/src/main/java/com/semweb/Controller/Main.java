package com.semweb.Controller;

import java.net.MalformedURLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    
    public static void main(String[] args) throws MalformedURLException {
        SpringApplication.run(Main.class, args);
        //JenaFusekiConnexion.runFusekiServer();
        JenaFusekiConnexion.connectToFuseki();
        //JenaFusekiConnexion.closeConnexionToFuseki();
    }

}
