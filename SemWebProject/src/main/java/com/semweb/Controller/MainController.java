package com.semweb.Controller;

import com.semweb.DAO.CityDAO;
import com.semweb.DAO.StationDAO;
import com.semweb.Model.Station;
import com.semweb.Model.City;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    
    public static List<Station> stations;
    public static List<City> cities;
    
    public static List<City> getCities(){
        return cities;
    }
    
    public static List<Station> getStations(){
        return stations;
    }
    
    public static void setCities(List<City> cities){
        MainController.cities = cities;
    }
    
    public static void setStations(List<Station> stations){
        MainController.stations = stations;
    }

    @RequestMapping(value = "/")
    public String goToTownsPage(Model m) {
        cities = CityDAO.getAllCity();
        m.addAttribute("cities", cities);
        return "/cities.html";
    }

    @RequestMapping(value = "/stations/{cityName}")
    public String goToStationPage(@PathVariable String cityName, Model m) {
        List<Station> stationss = new ArrayList<>();
        stationss= StationDAO.getAllStationByCityName(cityName);
        m.addAttribute("stations", stationss);
        m.addAttribute("cityName", cityName);

        return "/stations.html";
    }

    @RequestMapping(value = "/station/{idStation}")
    public String goToStationDetailPage(@PathVariable String idStation, Model m) {
        Station station = StationDAO.getStationById(idStation);
        m.addAttribute("station", station);
        return "/stationDetails.html";
    }
    
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public String search(Model m, @RequestParam(value = "searchString") String searchString) {
        //J'ai mis une nnouvelle liste pour voir si il y avait conflit et réécriture sur la liste stations statique
        List<Station> stationss = new ArrayList<>();
        stationss = StationDAO.findStation(searchString);
        m.addAttribute("stations", stationss);
        return "/stations.html";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/searchStationNearMe")
    public String searchStationNearMe(Model m) {
        Double lat = 45.453254d;
        Double lng = 4.390432d;
        List<Station> stations = new ArrayList<>();
        stations = StationDAO.findStationNearMe(lat, lng);
        m.addAttribute("stations", stations);
        return "/stations.html";
    }
}
