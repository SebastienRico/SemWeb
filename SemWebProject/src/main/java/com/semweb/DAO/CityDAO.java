package com.semweb.DAO;

import com.semweb.Controller.JenaFusekiConnexion;
import com.semweb.Model.City;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class CityDAO {
    
    private static final String CITY = "http://example.org/commune";
    
    public static List<City> getAllCity() {
        List<City> cities = new ArrayList<>();
        if (!JenaFusekiConnexion.getConnextion().isClosed()) {
            QueryExecution qExec = JenaFusekiConnexion.getConnextion().query("SELECT DISTINCT ?o WHERE{ ?s " + CITY + ":city ?o }");
            ResultSet rs = qExec.execSelect();
            while (rs.hasNext()) {
                City city = new City();
                QuerySolution qs = rs.next();
                city.setName(qs.get("o").toString());
                cities.add(city);
            }
            qExec.close();
        }
        return cities;
    }
}
