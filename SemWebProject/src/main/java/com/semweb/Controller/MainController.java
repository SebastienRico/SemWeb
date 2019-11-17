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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = "/cities")
    public String goToTownsPage(Model m) {
        List<City> cities = CityDAO.getAllCity();
        m.addAttribute("cities", cities);
        return "/cities.html";
    }
    
    @RequestMapping(value = "/stations/{cityName}")
    public String goToStationPage(@PathVariable String cityName, Model m){
        List<Station> stations = new ArrayList<>();
        stations = StationDAO.getAllStationByCityName(cityName);
        m.addAttribute("stations", stations);
        return "/stations.html";
    }
}
