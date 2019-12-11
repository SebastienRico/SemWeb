package com.semweb.DAO;

import com.semweb.Controller.JenaFusekiConnexion;
import com.semweb.Model.Station;
import com.semweb.Model.StatusStation;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;

public class StationDAO {

    private static final String LABEL = "http://www.w3.org/2000/01/rdf-schema#label";
    private static final String ADDRESS = "https://www.wikidata.org/wiki/Property:P669";
    private static final String LATITUDE = "http://www.w3.org/2003/01/geo/wgs84_pos#lat";
    private static final String LONGITUDE = "http://www.w3.org/2003/01/geo/wgs84_pos#lng";
    private static final String CITY = "http://example.org/city";
    private static final String EXEMPLE = "http://example.org/";
    private static final String BIKE_STANDS = "http://example.org/bike_stands";
    private static final String LAST_UPDATE = "http://example.org/last_update";

    public static List<Station> getAllStationByCityName(String cityName) {
        Boolean isStation = false;
        Station station = null;
        String stationId = "";
        List<Station> stations = new ArrayList<>();
        if (!JenaFusekiConnexion.getConnexion().isClosed()) {
            QueryExecution qExec = JenaFusekiConnexion
                    .getConnexion()
                    .query("PREFIX ex: <" + EXEMPLE + ">"
                            + "SELECT ?s ?p ?o "
                            + "WHERE { "
                            + "?s ?p ?o ."
                            + "?s ex:city \"" + cityName + "\" . "
                            + "}");
            ResultSet rs = qExec.execSelect();
            while (rs.hasNext()) {
                QuerySolution qs = rs.next();
                if (isStation && !stationId.equals(qs.get("s").toString())) {
                    stations.add(station);
                    isStation = false;
                }
                if (!isStation) {
                    station = new Station();
                    stationId = qs.get("s").toString();
                    String[] idstation = qs.get("s").toString().split("_");
                    station.setId(idstation[1]);
                    isStation = true;
                }
                switch (qs.get("p").toString()) {
                    case LABEL:
                        station.setName(qs.get("o").toString());
                        break;
                    case ADDRESS:
                        station.setAddress(qs.get("o").toString());
                        break;
                    //case AVAILABLE_BIKE_STANDS:
                        /*if (!"".equals(qs.get("o").toString())) {
                            station.setAvailableBikeStand(Integer.parseInt(qs.get("o").toString()));
                        }*/
                        //break;
                    //case AVAILABLE_BIKES:
                        /*if (!"".equals(qs.get("o").toString())) {
                            station.setAvailableBike(Integer.parseInt(qs.get("o").toString()));
                        }*/
                        //break;
                    case BIKE_STANDS:
                        /*if (!"".equals(qs.get("o").toString())) {
                            station.setBikeStand(Integer.parseInt(qs.get("o").toString()));
                        }*/
                        break;
                    case LAST_UPDATE:
                        station.setLastUpdate(qs.get("o").toString());
                        break;
                    /*case STATUS:
                        if (qs.get("o").toString().equals("open")) {
                            station.setStatus(StatusStation.OPEN);
                        } else {
                            station.setStatus(StatusStation.CLOSED);
                        }

                        break;*/
                    case LATITUDE:
                        if (!qs.get("o").toString().contains("^^")) {
                            station.setLatitude(Double.parseDouble(qs.get("o").toString()));
                        }
                        break;
                    case LONGITUDE:
                        if (!qs.get("o").toString().contains("^^")) {
                            station.setLongitude(Double.parseDouble(qs.get("o").toString()));
                        }
                        break;
                    /*case COORDINATES:
                        String[] coordinates = qs.get("o").toString().split(",");
                        station.setLatitude(Double.parseDouble(coordinates[0]));
                        station.setLongitude(Double.parseDouble(coordinates[1]));*/
                    case CITY:
                        station.getCity().setName(qs.get("o").toString());
                        break;
                    default:
                        break;
                }
            }
            stations.add(station);
            qExec.close();

            //Afficher les stations dans la console
            /*for (Station onestation : stations) {
                System.out.println(onestation.toString() + " dans " + onestation.getCity().getName());
            }*/
        }
        return stations;
    }

    public static Station getStationById(String idStation) {
        Station station = new Station();
        String stationId = idStation;
        if (idStation.contains("ex")) {
            String[] splitid = idStation.split("_");
            stationId = splitid[1];
        }
        station.setId(idStation);
        if (!JenaFusekiConnexion.getConnexion().isClosed()) {
            QueryExecution qExec = JenaFusekiConnexion
                    .getConnexion()
                    .query("PREFIX ex: <" + EXEMPLE + ">"
                            + "SELECT ?p ?o "
                            + "WHERE { "
                            + "<ex:station_" + stationId + "> ?p ?o . "
                            + "}");
            ResultSet rs = qExec.execSelect();
            while (rs.hasNext()) {
                QuerySolution qs = rs.next();
                switch (qs.get("p").toString()) {
                    case LABEL:
                        station.setName(qs.get("o").toString());
                        break;
                    case ADDRESS:
                        station.setAddress(qs.get("o").toString());
                        break;
                    case BIKE_STANDS:
                        String bikeStand = qs.get("o").toString();
                        if (!"".equals(qs.get("o").toString())) {
                            if (qs.get("o").toString().contains("^^")) {
                                String[] splitbikestand = bikeStand.split("\\^");
                                bikeStand = splitbikestand[0];
                            }
                            station.setBikeStand(Integer.parseInt(bikeStand));
                        }
                        break;
                    case LAST_UPDATE:
                        station.setLastUpdate(qs.get("o").toString());
                        break;
                    case LATITUDE:
                        String latString = qs.get("o").toString();
                        if (latString.contains("^^")) {
                            String[] splitlat = latString.split("\\^");
                            latString = splitlat[0];
                        }
                        station.setLatitude(Double.parseDouble(latString));
                        break;
                    case LONGITUDE:
                        String lngString = qs.get("o").toString();
                        if (lngString.contains("^^")) {
                            String[] splitlng = lngString.split("\\^");
                            lngString = splitlng[0];
                        }
                        station.setLongitude(Double.parseDouble(lngString));
                        break;

                    case CITY:
                        station.getCity().setName(qs.get("o").toString());
                        break;
                    default:
                        break;
                }
            }
            qExec.close();
        }
        return station;
    }

    public static List<Station> findStation(String searchString) {
        List<Station> stations = new ArrayList<>();
        if (!JenaFusekiConnexion.getConnexion().isClosed()) {
            QueryExecution qExec = JenaFusekiConnexion
                    .getConnexion()
                    .query("SELECT DISTINCT ?s "
                            + "WHERE { "
                            + "?s ?p ?o . "
                            + "FILTER (regex(lcase(?o), lcase(\"" + searchString + "\")))"
                            + "}");
            ResultSet rs = qExec.execSelect();
            while (rs.hasNext()) {
                QuerySolution qs = rs.next();
                Station station = getStationById(qs.get("s").toString());
                stations.add(station);
            }
            qExec.close();
        }
        return stations;
    }

    public static List<Station> findStationNearMe(Double lat, Double lng) {
        List<Station> stations = new ArrayList<>();
        if (!JenaFusekiConnexion.getConnexion().isClosed()) {
            QueryExecution qExec = JenaFusekiConnexion
                    .getConnexion()
                    .query("PREFIX ex: <http://example.org/>"
                            + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
                            + "PREFIX math: <http://www.w3.org/2005/xpath-functions/math#>"
                            + "SELECT DISTINCT ?s "
                            + "WHERE {	"
                            + "?s geo:lat ?lat ."
                            + "?s geo:lng ?lg .  "
                            + "  BIND (math:acos(math:cos(" + lat + ")*math:cos(?lat)* math:cos(" + lng + " - ?lg) + math:sin(" + lat + ")*math:sin(?lat)) AS ?distance)}"
                            + "ORDER BY (?distance)"
                            + "LIMIT 10");
            ResultSet rs = qExec.execSelect();
            while (rs.hasNext()) {
                QuerySolution qs = rs.next();
                Station station = getStationById(qs.get("s").toString());
                stations.add(station);
            }
            qExec.close();
        }
        return stations;
    }
}
