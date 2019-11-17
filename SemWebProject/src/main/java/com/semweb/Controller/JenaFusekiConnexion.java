package com.semweb.Controller;

import java.util.logging.Logger;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

public class JenaFusekiConnexion {

    private static Logger logger = Logger.getLogger("JenaFusekiConnexion");

    private static String DATABASE_URL = "http://localhost:3030/SemWebProject";
    private static RDFConnection conn;

    public static void connectToFuseki() {
        conn = RDFConnectionFactory.connect(DATABASE_URL);
        if (conn.isClosed()) {
            logger.info("App not connected to Fuseki database");
        } else {
            logger.info("App connected to fuseki database");
        }
    }

    public static void closeConnexionToFuseki() {
        conn.close();
        logger.info("App disconnected to fuseki database");
    }

    public static RDFConnection getConnextion() {
        return conn;
    }

}
