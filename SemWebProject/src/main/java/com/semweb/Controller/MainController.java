package com.semweb.Controller;

import com.semweb.DAO.CityDAO;
import com.semweb.DAO.StationDAO;
import com.semweb.Model.Station;
import com.semweb.Model.City;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = "/towns")
    public String goToTownsPage(Model m) {
        List<City> towns = CityDAO.getAllCity();
        m.addAttribute("towns", towns);
        return "/towns.html";
    }
    
    @RequestMapping(value = "/stations")
    public String goToStationPage(Model m){
        List<Station> stations = new ArrayList<>();
        stations = StationDAO.getAllStationByCityName();
        
        m.addAttribute("stations", stations);
        
        return "/stations.html";
    }
}
