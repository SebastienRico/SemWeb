package com.semweb.DAO;

import com.semweb.Controller.JenaFusekiConnexion;
import com.semweb.Model.City;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

public class CityDAO {
    
    private static final String CITY = "http://example.org/";
    
    public static List<City> getAllCity() {
        List<City> cities = new ArrayList<>();
        RDFConnection conn = RDFConnectionFactory.connect("http://localhost:3030/TestBike");
            QueryExecution qExec = JenaFusekiConnexion.getConnexion().query("PREFIX ex: <"+CITY+"> SELECT DISTINCT ?o WHERE{ ?s ex:city ?o }");
            ResultSet rs = qExec.execSelect();
            while (rs.hasNext()) {
                City city = new City();
                QuerySolution qs = rs.next();
                city.setName(qs.get("o").toString());
                cities.add(city);
            }
            qExec.close();
            conn.close() ;
        return cities;
    }
}
