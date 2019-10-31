package com.semweb.Controller;

import org.springframework.stereotype.Controller;
//import org.apache.jena.vocabulary.

@Controller
public class JenaFusekiConnexion {

    /*public void connectToFuseki() {
        RDFConnection conn = RDFConnectionFactory.connect("localhost:3030/SemWebProjectTest/sparql", null, null);
        //conn.load("data.ttl");
        QueryExecution qExec = conn.query("SELECT DISTINCT ?s { ?s ?p ?o }");
        ResultSet rs = qExec.execSelect();
        while (rs.hasNext()) {
            QuerySolution qs = rs.next();
            Resource subject = qs.getResource("s");
            System.out.println("Subject: " + subject);
        }
        qExec.close();
        conn.close();
    }*/

 /*@RequestMapping("/request")
    public static String connectToFuseki2() {
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create()
                .destination("http://localhost:3030/SemWebProjectTest/sparql");
        Query query = QueryFactory.create("SELECT * { BIND('Hello'as ?text) }");
        // In this variation, a connection is built each time.
        try (RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            conn.queryResultSet(query, ResultSetFormatter::out);
        }
        return "toto.html";
    }
    String serviceURI = "http://localhost:3030//SemWebProjectTest";
    DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(serviceURI);

    String geo = "http://www.w3.org/2003/01/geo/wgs84_pos#";
    String ex = "http://www.example.com/";

    String xsd = "http://www.w3.org/2001/XMLSchema#";
    String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
    String wd = "http://www.wikidata.org/entity/";

    
    
    org.apache.jena.rdf.model.Model stationModel = ModelFactory.createDefaultModel();
    stationModel.setNsPrefix("ex", ex);
    stationModel.setNsPrefix("geo", geo);
    stationModel.setNsPrefix("xsd", xsd);
    stationModel.setNsPrefix("rdfs",rdfs);
    stationModel.setNsPrefix("wd", wd);
    
    Property lat = stationModel.createProperty(geo + "lat");
    Property lon = stationModel.createProperty(geo + "lon");

    Resource r;
    Property spatial = stationModel.createProperty(geo + "SpatialThing");
    Property stationVelo = stationModel.createProperty(wd + "Q61663696"); //bicyle sharing station//bicyle sharing station//bicyle sharing station//bicyle sharing station//bicyle sharing station//bicyle sharing station//bicyle sharing station//bicyle sharing station

    /*try {
        //On recupere le contenu de l'URL du JSON des data, l'infos sur les stations
        JSONObject json = null;
        try {
            json = new JSONObject(readUrl("https://saint-etienne-gbfs.klervi.net/gbfs/en/station_information.json"));
        } catch (Exception ex) {
            Logger.getLogger(JenaFusekiConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        int last_updated = (Integer) json.get("last_updated");

        //on recupere le noeud "data"
        JSONObject data = json.getJSONObject("data");

        //on recupere la liste des etats des stations
        JSONArray stations = data.getJSONArray("stations");

        //System.out.println(data.length());
        System.out.println("Il y a " + stations.length() + "stations");
        Town town = new Town();
        town.setNom("Saint-Etienne");

        List<Station> listeStation = new ArrayList<Station>();
        //List<MyType> myList = new ArrayList<>();

        Station station;

        //objet tempoaire de traitement pour acceder aux infos
        JSONObject traitement;
        for (int i = 0; i < stations.length(); i++) {
            station = new Station();
            traitement = stations.getJSONObject(i);
            station.setId((String) traitement.get("station_id"));
            station.setNom((String) traitement.get("name"));
            station.setLatitude((Double) traitement.get("lat"));
            station.setLongitude((Double) traitement.get("lon"));
            station.setCapacite((Integer) traitement.get("capacity"));

            listeStation.add(station);
        }

        town.setStations(listeStation);

        for (int i = 0; i < listeStation.size(); i++) {
            System.out.println("STATION <<<>>>  \n"
                    + "ID : " + listeStation.get(i).getId() + "\n"
                    + "NOM : " + listeStation.get(i).getNom());

            r = stationModel.createResource(ex + listeStation.get(i).getId());
            r.addProperty(lat, listeStation.get(i).getLatitude().toString(), XSDDatatype.XSDdecimal);
            r.addProperty(RDFS.label, listeStation.get(i).getNom(), "fr");
            r.addProperty(lon, listeStation.get(i).getLongitude().toString(), XSDDatatype.XSDdecimal);
            r.addProperty(RDF.type, stationVelo);
            r.addProperty(RDF.type, spatial);

            // r.addProperty()
        }
        System.out.println("LAST UPDATED >>> " + last_updated);

        stationModel.write(System.out, "Turtle");

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("C:\\Users\\Sebastien\\Documents\\Fac\\M2\\SemWeb\\OUT\\out.ttl"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JenaFusekiConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        stationModel.write(out, "Turtle");

    } catch (JSONException e) {
        e.printStackTrace();
    } catch (IOException ex) {
        Logger.getLogger(JenaFusekiConnexion.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(JenaFusekiConnexion.class.getName()).log(Level.SEVERE, null, ex);
    }*/

}
