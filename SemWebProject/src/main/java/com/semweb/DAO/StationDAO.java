package com.semweb.DAO;

import com.semweb.Controller.JenaFusekiConnexion;
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
    private static final String COORDINATES = "http://www.w3.org/2003/01/geo/wgs84_pos#coordinates";
    private static final String CITY = "http://example.org/commune";
    private static final String AVAILABLE_BIKE_STANDS = "http://example.org/available_bike_stands";
    private static final String AVAILABLE_BIKES = "http://example.org/available_bikes";
    private static final String BIKE_STANDS = "http://example.org/bike_stands";
    private static final String LAST_UPDATE = "http://example.org/last_update";
    private static final String STATUS = "http://example.org/status";
    

    public static List<Station> getAllStationByCityName(String cityName) {
        List<Station> stations = new ArrayList<>();
        if (!JenaFusekiConnexion.getConnextion().isClosed()) {
            QueryExecution qExec = JenaFusekiConnexion.getConnextion().query("SELECT DISTINCT ?s ?p ?o WHERE{ ?s ?p ?o. ?s " + CITY + ":city \"" + cityName + "\" }");
            ResultSet rs = qExec.execSelect();
            while (rs.hasNext()) {
                Station station = new Station();
                QuerySolution qs = rs.next();
                station.setId(qs.get("s").toString());
                switch(qs.get("p").toString()){
                    case LABEL:
                        station.setName(qs.get("o").toString());
                        break;
                    case LATITUDE:
                        if(!qs.get("o").toString().contains("^^")){
                            station.setLatitude(Double.parseDouble(qs.get("o").toString()));
                        }
                        break;
                    case LONGITUDE:
                        if(!qs.get("o").toString().contains("^^")){
                            station.setLongitude(Double.parseDouble(qs.get("o").toString()));
                        }
                        break;
                    case COORDINATES:
                        String[] coordinates = qs.get("o").toString().split(",");
                        station.setLatitude(Double.parseDouble(coordinates[0]));
                        station.setLongitude(Double.parseDouble(coordinates[1]));
                    case CITY:
                        station.getCity().setName(qs.get("o").toString());
                        break;
                    default:
                        break;
                }
                stations.add(station);
            }
            qExec.close();
        }
        return stations;
    }
}
