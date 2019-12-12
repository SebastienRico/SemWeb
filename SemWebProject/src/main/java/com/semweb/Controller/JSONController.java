package com.semweb.Controller;

import com.semweb.DAO.StationDAO;
import com.semweb.Model.City;
import com.semweb.Model.Station;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class JSONController {

    private static Boolean stationExist = false;
    private static Integer index = null;

    private static final String SAINT_ETIENNE_DATA_URL = "https://saint-etienne-gbfs.klervi.net/gbfs/en/station_information.json";
    private static final String SAINT_ETIENNE_FILE_NAME = "src/main/resources/jsonFiles/saintEtienne.json";
    private static final String SAINT_ETIENNE_CITY_NAME = "Saint-Etienne";

    public static void parseJSONDatas(String cityName) {
        if (cityName.equals(SAINT_ETIENNE_CITY_NAME)) {
            getJSONFilesFromURL(SAINT_ETIENNE_DATA_URL, SAINT_ETIENNE_FILE_NAME);
            parseCity(SAINT_ETIENNE_FILE_NAME);
        }
    }

    public static void getJSONFilesFromURL(String FILE_URL, String FILE_NAME) {
        InputStream in = null;
        try {
            in = new URL(FILE_URL).openStream();
            File file = new File(FILE_NAME);
            if (file.createNewFile()) {
                System.out.println("File " + FILE_NAME + " is created!");
            } else {
                System.out.println("File " + FILE_NAME + " already exists.");
            }
            Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (MalformedURLException ex) {
            Logger.getLogger(JSONController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSONController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(JSONController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void parseCity(String FILE_NAME) {
        JSONParser jsonParser = new JSONParser();
        File file = new File(FILE_NAME);
        try (FileReader reader = new FileReader(file.getAbsolutePath())) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            parseSaintEtienneObject(jsonObject);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSONController.class.getName()).log(Level.SEVERE, "File .json not found", ex);
        } catch (IOException ex) {
            Logger.getLogger(JSONController.class.getName()).log(Level.SEVERE, "IOException .json", ex);
        } catch (ParseException ex) {
            Logger.getLogger(JSONController.class.getName()).log(Level.SEVERE, "Error in parsing .json", ex);
        }
    }

    private static City getCity(String cityName) {
        List<City> cities = MainController.getCities();
        City c = null;
        for (int j = 0; j < cities.size(); j++) {
            if (cities.get(j).getName().equals(cityName)) {
                c = cities.get(j);
            }
        }
        return c;
    }

    private static Station getStation(City city, String stationId) {
        List<Station> stations = StationDAO.getAllStationByCityName(city.getName());
        MainController.setStations(stations);
        Station station = null;
        stationExist = false;
        for (int k = 0; k < stations.size(); k++) {
            if (stations.get(k).getCity().equals(city) && stations.get(k).getId().equals(stationId)) {
                stationExist = true;
                index = k;
                station = stations.get(k);
            }
        }
        return station;
    }

    private static void parseSaintEtienneObject(JSONObject saintEtienneObject) {
        String lastUpdate = saintEtienneObject.get("last_updated").toString();
        JSONObject data = (JSONObject) saintEtienneObject.get("data");
        JSONArray stations = (JSONArray) data.get("stations");
        for (int i = 0; i < stations.size(); i++) {
            JSONObject station = (JSONObject) stations.get(i);
            String stationId = station.getAsString("station_id");

            City saintEtienne = getCity(SAINT_ETIENNE_CITY_NAME);
            Station stationSaintEtienne = getStation(saintEtienne, stationId);

            if (!stationExist) {
                stationSaintEtienne = new Station();
                stationSaintEtienne.setCity(saintEtienne);
                stationSaintEtienne.setId(stationId);
            }
            stationSaintEtienne.setLastUpdate(lastUpdate);
            stationSaintEtienne.setName(station.getAsString("name"));
            if (!station.getAsString("lat").equals("") && !station.getAsString("lon").equals("")) {
                stationSaintEtienne.setLatitude(Double.parseDouble(station.getAsString("lat")));
                stationSaintEtienne.setLongitude(Double.parseDouble(station.getAsString("lon")));
            }
            if (!station.getAsString("capacity").equals("")) {
                stationSaintEtienne.setBikeStand(Integer.parseInt(station.getAsString("capacity")));
            }

            if (stationExist) {
                MainController.getStations().set(index, stationSaintEtienne);
            } else {
                MainController.getStations().add(stationSaintEtienne);
            }
        }
    }

}
