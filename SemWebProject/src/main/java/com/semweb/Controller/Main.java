package com.semweb.Controller;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        connectToFuseki();
    }

    public static void connectToFuseki() {
        try (RDFConnection conn = RDFConnectionFactory.connect("http://localhost:3030/SemWebProjectTest")) {
            QueryExecution qExec = conn.query("SELECT DISTINCT ?s WHERE{ ?s ?p ?o }");
            ResultSet rs = qExec.execSelect();
            while (rs.hasNext()) {
                QuerySolution qs = rs.next();
                Resource subject = qs.getResource("s");
                System.out.println("Subject: " + subject);
            }
            qExec.close();
            conn.close();
        }
    }
}
