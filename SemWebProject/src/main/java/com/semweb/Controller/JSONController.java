package com.semweb.Controller;

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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class JSONController {

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
                case "lyon":
                    parseLyonObject(jsonObject);
                    break;
                case "saintEtienne":
                    parseSaintEtienneObject(jsonObject);
                    break;
                case "rennes":
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

    private static void parseLyonObject(JSONObject lyonObject) {
        /*String lastUpdate = lyonObject.get("last_updated").toString();
        JSONObject data = (JSONObject) lyonObject.get("data");
        JSONArray stations = (JSONArray) data.get("stations");
        for (int i = 0; i < stations.size(); i++) {
            JSONObject station = (JSONObject) stations.get(i);
            String stationId = station.get("station_id").toString();
            String name = station.get("name").toString();
            Double lat = Double.parseDouble(station.getAsString("lat").toString());
            Double lng = Double.parseDouble(station.getAsString("lon").toString());
            Integer bikeStand = Integer.parseInt(station.getAsString("capacity").toString());
            // Chercher la station si elle existe pour la mettre à jour sinon la créer
            // Insérer la nouvelle station ou la mettre à jour dans Fuseki
        }*/
    }
    
    private static void parseRennesObject(JSONObject rennesObject) {
        /*String lastUpdate = lyonObject.get("last_updated").toString();
        JSONObject data = (JSONObject) lyonObject.get("data");
        JSONArray stations = (JSONArray) data.get("stations");
        for (int i = 0; i < stations.size(); i++) {
            JSONObject station = (JSONObject) stations.get(i);
            String stationId = station.get("station_id").toString();
            String name = station.get("name").toString();
            Double lat = Double.parseDouble(station.getAsString("lat").toString());
            Double lng = Double.parseDouble(station.getAsString("lon").toString());
            Integer bikeStand = Integer.parseInt(station.getAsString("capacity").toString());
            // Chercher la station si elle existe pour la mettre à jour sinon la créer
            // Insérer la nouvelle station ou la mettre à jour dans Fuseki
        }*/
    }

    private static void parseSaintEtienneObject(JSONObject saintEtienneObject) {
        String lastUpdate = saintEtienneObject.get("last_updated").toString();
        JSONObject data = (JSONObject) saintEtienneObject.get("data");
        JSONArray stations = (JSONArray) data.get("stations");
        for (int i = 0; i < stations.size(); i++) {
            JSONObject station = (JSONObject) stations.get(i);
            String stationId = station.get("station_id").toString();
            String name = station.get("name").toString();
            Double lat = Double.parseDouble(station.getAsString("lat").toString());
            Double lng = Double.parseDouble(station.getAsString("lon").toString());
            Integer bikeStand = Integer.parseInt(station.getAsString("capacity").toString());
            // Chercher la station si elle existe pour la mettre à jour sinon la créer
            // Insérer la nouvelle station ou la mettre à jour dans Fuseki
        }
    }

}
