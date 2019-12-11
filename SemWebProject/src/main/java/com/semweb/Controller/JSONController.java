package com.semweb.Controller;

import com.semweb.DAO.StationDAO;
import com.semweb.Model.City;
import com.semweb.Model.Station;
import com.semweb.Model.StatusStation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class JSONController {
    
    private static Boolean stationExist = false;
    private static Integer index = null;

    static final String LYON_DATA_URL = "https://download.data.grandlyon.com/wfs/rdata?SERVICE=WFS&VERSION=1.1.0&outputformat=GEOJSON&request=GetFeature&typename=jcd_jcdecaux.jcdvelov&SRSNAME=urn:ogc:def:crs:EPSG::4171";
    static final String LYON_FILE_NAME = "src/main/resources/jsonFiles/lyon.json";
    static final String SAINT_ETIENNE_DATA_URL = "https://saint-etienne-gbfs.klervi.net/gbfs/en/station_information.json";
    static final String SAINT_ETIENNE_FILE_NAME = "src/main/resources/jsonFiles/saintEtienne.json";
    static final String RENNES_DATA_URL = "https://data.rennesmetropole.fr/api/records/1.0/search/?dataset=etat-des-stations-le-velo-star-en-temps-reel";
    static final String RENNES_FILE_NAME = "src/main/resources/jsonFiles/rennes.json";

    public static void parseJSONDatas() {
        Map<String, String> citiesMap = new HashMap<>();
        citiesMap.put(LYON_DATA_URL, LYON_FILE_NAME);
        citiesMap.put(SAINT_ETIENNE_DATA_URL, SAINT_ETIENNE_FILE_NAME);
        citiesMap.put(RENNES_DATA_URL, RENNES_FILE_NAME);
        citiesMap.forEach((cityDataUrl, cityFileName) -> {
            getJSONFilesFromURL(cityDataUrl, cityFileName);
            String[] tmpCity = cityFileName.split("/");
            String city = tmpCity[tmpCity.length - 1];
            System.out.println("city : " + city);
            parseCity(cityFileName, city);
        });
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

    public static void parseCity(String FILE_NAME, String city) {
        JSONParser jsonParser = new JSONParser();
        File file = new File(FILE_NAME);
        try (FileReader reader = new FileReader(file.getAbsolutePath())) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            switch (city) {
                case "lyon.json":
                    parseLyonObject(jsonObject);
                    break;
                case "saintEtienne.json":
                    parseSaintEtienneObject(jsonObject);
                    break;
                case "rennes.json":
                    parseRennesObject(jsonObject);
                    break;
                default:
                    System.out.println("Aucune ville n'est paramétrée pour cet URL");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSONController.class.getName()).log(Level.SEVERE, "File rennnes.json not found", ex);
        } catch (IOException ex) {
            Logger.getLogger(JSONController.class.getName()).log(Level.SEVERE, "IOException rennes.json", ex);
        } catch (ParseException ex) {
            Logger.getLogger(JSONController.class.getName()).log(Level.SEVERE, "Error in parsing rennes.json", ex);
        }
    }
    
    private static City getCity(String cityName){
        List<City> cities = MainController.getCities();
            City c = null;
            for (int j = 0; j < cities.size(); j++) {
                if (cities.get(j).getName().equals(cityName)) {
                    c = cities.get(j);
                }
            };
        return c;
    }
    
    private static Station getStation(City city, String stationId){
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
            };
            return station;
    }

    private static void parseLyonObject(JSONObject lyonObject) {
        stationExist = false;
        JSONArray features = (JSONArray) lyonObject.get("features");
        for (int i = 0; i < features.size(); i++) {
            JSONObject tmpProperty = (JSONObject) features.get(i);
            JSONObject property = (JSONObject) tmpProperty.get("properties");
            String stationId = property.get("number").toString();
            
            City lyon = getCity("Lyon");
            Station station = getStation(lyon, stationId);
            
            if(!stationExist){
                station = new Station();
                station.setCity(lyon);
                station.setId(stationId);
            }
            String status = property.getAsString("status");
            switch (status) {
                case "OPEN":
                    station.setStatus(StatusStation.OPEN);
                    break;
                default:
                    station.setStatus(StatusStation.CLOSED);
            }
            station.setName(property.getAsString("name"));
            station.setAddress(property.getAsString("address"));
            if (!property.getAsString("lat").equals("") && !property.getAsString("lng").equals("")) {
                station.setLatitude(Double.parseDouble(property.getAsString("lat")));
                station.setLongitude(Double.parseDouble(property.getAsString("lng")));
            }
            if (!property.getAsString("bike_stands").equals("")) {
                station.setBikeStand(Integer.parseInt(property.getAsString("bike_stands")));
            }
            if (!property.getAsString("available_bike_stands").equals("")) {
                station.setAvailableBikeStand(Integer.parseInt(property.getAsString("available_bike_stands")));
            }
            if (!property.getAsString("available_bikes").equals("")) {
                station.setAvailableBike(Integer.parseInt(property.getAsString("available_bikes")));
            }
            station.setLastUpdate(property.getAsString("last_update"));

            if(stationExist){
                MainController.getStations().set(index, station);
            } else {
                MainController.getStations().add(station);
            }
        }
    }

    private static void parseRennesObject(JSONObject rennesObject) {
        stationExist = false;
        JSONArray records = (JSONArray) rennesObject.get("records");
        for (int i = 0; i < records.size(); i++) {
            JSONObject record = (JSONObject) records.get(i);
            JSONObject fields = (JSONObject) record.get("fields");
            String stationId = fields.getAsString("idstation");

            City rennes = getCity("Rennes");
            Station station = getStation(rennes, stationId);
            
            if(!stationExist){
                station = new Station();
                station.setCity(rennes);
                station.setId(stationId);
            }
            String status = fields.getAsString("etat");
            switch (status) {
                case "En fonctionnement":
                    station.setStatus(StatusStation.OPEN);
                    break;
                default:
                    station.setStatus(StatusStation.CLOSED);
            }
            station.setLastUpdate(fields.get("lastupdate").toString());
            if (!fields.getAsString("nombrevelosdisponibles").equals("")) {
                station.setAvailableBike(Integer.parseInt(fields.getAsString("nombrevelosdisponibles")));
            }
            if (!fields.getAsString("nombreemplacementsdisponibles").equals("")) {
                station.setAvailableBikeStand(Integer.parseInt(fields.getAsString("nombreemplacementsdisponibles")));
            }
            station.setName(fields.getAsString("nom"));
            if (!fields.getAsString("nombreemplacementsactuels").equals("")) {
                station.setBikeStand(Integer.parseInt(fields.getAsString("nombreemplacementsactuels")));
            }
            JSONArray coordonnees = (JSONArray) fields.get("coordonnees");
            station.setLatitude(Double.parseDouble(coordonnees.get(0).toString()));
            station.setLongitude(Double.parseDouble(coordonnees.get(1).toString()));

            if(stationExist){
                MainController.getStations().set(index, station);
            } else {
                MainController.getStations().add(station);
            }
        }
    }

    private static void parseSaintEtienneObject(JSONObject saintEtienneObject) {
        String lastUpdate = saintEtienneObject.get("last_updated").toString();
        JSONObject data = (JSONObject) saintEtienneObject.get("data");
        JSONArray stations = (JSONArray) data.get("stations");
        for (int i = 0; i < stations.size(); i++) {
            JSONObject station = (JSONObject) stations.get(i);
            String stationId = station.getAsString("station_id");
            
            City saintEtienne = getCity("Saint-Etienne");
            Station stationSaintEtienne = getStation(saintEtienne, stationId);
            
            if(!stationExist){
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

            if(stationExist){
                MainController.getStations().set(index, stationSaintEtienne);
            } else {
                MainController.getStations().add(stationSaintEtienne);
            }
        }
    }

}
