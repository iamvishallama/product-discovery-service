package com.nitkkr.semanticweb.recommender.constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SPARQLQuery {
    /*  Query1: Products with RAM = 8GB, having price not more than 800 Euros,
        available with seller having at least 1 quantity.
    */
    public static String query1 = getQueryFromTextFile("Query1");

    //Query2: Get all products details
    public static String query2 = getQueryFromTextFile("Query2");

    //Query3: Get all products whose price = 1099 Euros
    public static String query3 = getQueryFromTextFile("Query3");

    // Query4: Get all products having seller's count > 1
    public static String query4 = getQueryFromTextFile("Query4");

    //Query5: get all the product i5 CPU configuration and Intel GPU
    public static String query5 = getQueryFromTextFile("Query5");

    public SPARQLQuery() {
    }

    public static String getQueryFromTextFile(String query) {
        String content = null;
        try {
            content = new String(Files.readString(Paths.get("src/main/resources/SPARQLQuery/"+query)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String getKeywordSearchQuery(String keyword) {
        String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
                "PREFIX gr: <http://purl.org/goodrelations/v1#>\n" +
                "PREFIX eeo: <http://rdf-dump/eeo/0.1/>\n" +
                "PREFIX schema: <https://schema.org/>\n" +
                "SELECT DISTINCT ?Subject ?Property ?Object  WHERE\n" +
                "{" +
                "{ ?Subject ?Property ?Object. FILTER (REGEX(?Object , \""+keyword+"\")) } OPTIONAL\n" +
                "{ ?Subject ?Property ?Object. FILTER (regex(?Property , \""+keyword+"\")) } OPTIONAL \n" +
                "{ ?Subject ?Property ?Object. FILTER (regex(?Subject , \""+keyword+"\")) } \n" +
                "}";
        return query;
    }
}
