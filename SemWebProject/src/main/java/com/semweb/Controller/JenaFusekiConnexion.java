package com.semweb.Controller;

import java.io.IOException;
import java.util.logging.Logger;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import com.semweb.utilities.Util;
import java.util.logging.Level;

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

    static void runFusekiServer() {
        String pathToFusekiServerBatFile = Util.getProperty("pathToFusekiServerBatFile");
        System.out.println("pathToFusekiServerBatFile : " + pathToFusekiServerBatFile);
        try {
            Runtime.
                    getRuntime().
                    exec("cmd " + pathToFusekiServerBatFile + " start \"\" fuseki-server.bat");
        } catch (IOException ex) {
            logger.info("Impossible to run fuserki server bat file");
        }
    }

}
