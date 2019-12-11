package com.semweb.Controller;

import static com.semweb.Controller.MainController.cities;
import com.semweb.DAO.CityDAO;
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
        MainController.cities = CityDAO.getAllCity();
        JSONController.parseJSONDatas();
        //JenaFusekiConnexion.closeConnexionToFuseki();
    }

}
