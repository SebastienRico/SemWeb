package com.semweb.Controller;

import com.semweb.DAO.CityDAO;
import com.semweb.DAO.StationDAO;
import com.semweb.Model.Station;
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
    public static List<String> cities;

    public static List<String> getCities() {
        return cities;
    }

    public static List<Station> getStations() {
        return stations;
    }

    public static void setCities(List<String> cities) {
        MainController.cities = cities;
    }

    public static void setStations(List<Station> stations) {
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
        stations = StationDAO.getAllStationByCityName(cityName);
        //JSONController.parseJSONDatas(cityName);
        m.addAttribute("stations", stations);
        m.addAttribute("cityName", cityName);
        return "/stations.html";
    }

    @RequestMapping(value = "/station/{idStation}")
    public String goToStationDetailPage(@PathVariable String idStation, Model m) {
        String url = "";
        Station station = StationDAO.getStationById(idStation);
        if (!station.getCity().equals("Saint-Etienne")) {
            url = "https://api.jcdecaux.com/vls/v3/stations/" + station.getId() + "?contract=" + station.getCity() + "&apiKey=b0d471d68f580414dc830cde44ce32d66def361e";
        }
        m.addAttribute("station", station);
        m.addAttribute("url", url);
        return "/stationDetails.html";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public String search(Model m, @RequestParam(value = "searchString") String searchString) {
        stations = StationDAO.findStation(searchString);
        m.addAttribute("stations", stations);
        return "/stations.html";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/searchStationNearMe")
    public String searchStationNearMe(Model m) {
        Double lat = 45.453254d;
        Double lng = 4.390432d;
        stations = StationDAO.findStationNearMe(lat, lng);
        m.addAttribute("stations", stations);
        return "/stations.html";
    }
}
