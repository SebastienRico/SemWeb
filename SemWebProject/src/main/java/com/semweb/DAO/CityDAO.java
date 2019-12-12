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

    public static List<City> getAllCity() {
        List<City> cities = new ArrayList<>();
        RDFConnection conn = RDFConnectionFactory.connect("http://localhost:3030/SemWebProject");
        QueryExecution qExec = JenaFusekiConnexion.getConnexion().query("PREFIX city: <http://fr.dbpedia.org/page/> PREFIX wdp: <https://www.wikidata.org/wiki/Property:> SELECT DISTINCT ?o WHERE{ ?s wdp:P131 ?o }");
        ResultSet rs = qExec.execSelect();
        while (rs.hasNext()) {
            City city = new City();
            QuerySolution qs = rs.next();
            String cityname = qs.get("o").toString();
            String[] splitcityname = cityname.split("\\/");
            city.setName(splitcityname[4]);
            cities.add(city);
        }
        qExec.close();
        conn.close();
        return cities;
    }
}
