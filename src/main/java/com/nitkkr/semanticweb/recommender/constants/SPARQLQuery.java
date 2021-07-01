package com.nitkkr.semanticweb.recommender.constants;

import javax.management.Query;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

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

    //Query5: get all the product i5 CPU configuration and Intel GPU
    public static String query6 = getQueryFromTextFile("Query6");


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
      return String.format(query6, keyword, keyword, keyword);
    }

    public static String formQuery(String category, String brand, String rAM, String oS, String seller) {
        return String.format(getQueryFromTextFile("Query0"), category, brand, rAM, oS, seller);
    }
}
