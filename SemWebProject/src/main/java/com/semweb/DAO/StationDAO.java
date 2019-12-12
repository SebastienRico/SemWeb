package com.semweb.DAO;

import com.semweb.Controller.JenaFusekiConnexion;
import com.semweb.Controller.MainController;
import com.semweb.Model.Station;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

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
                    .query("PREFIX wdp: <https://www.wikidata.org/wiki/Property:> PREFIX city: <http://fr.dbpedia.org/page/>"
                            + "SELECT ?s ?p ?o "
                            + "WHERE { "
                            + "?s ?p ?o ."
                            + "?s wdp:P131 city:" + cityName + " . "
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
                    case CITY:
                        String cityname = qs.get("o").toString();
                        String[] splitcityname = cityname.split("\\/");
                        station.getCity().setName(splitcityname[4]);
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

    public static Station getRealTimeStationById(String idStation) {
        Station thestation = MainController.stations.stream()
                .filter(station -> idStation.equals(station.getId()))
                .findAny()
                .orElse(null);
        return thestation;
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
