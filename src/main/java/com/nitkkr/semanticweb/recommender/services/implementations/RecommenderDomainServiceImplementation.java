package com.nitkkr.semanticweb.recommender.services.implementations;

import com.nitkkr.semanticweb.recommender.constants.SPARQLQuery;

import javax.inject.Inject;
import java.util.List;

public class RecommenderDomainServiceImplementation {

    @Inject
    MyOntology myOntology;

    @Inject
    SPARQLQuery sparqlQuery;

    public List<String> executeSPARQLEndpoint(String spqarqlQuery) {
        return myOntology.executeSparql(spqarqlQuery);
    }

    public List<String> executeRapidInformationExplorer(String keyword) {
        return myOntology.executeRapidInformationExplorer(keyword);
    }

    public List<String> retrieveProduct(String category, String brand, String cPU, String gPU, String oS, String rAM, String screenSize, String screenType, String storage, String price, String  quantity, String seller) {
        String mainQuery = sparqlQuery.formQuery("?Category".equalsIgnoreCase(category)?category:"eeo:"+category,
                "?Brand".equalsIgnoreCase(brand)?brand:"eeo:"+brand,
                "?RAM".equalsIgnoreCase(rAM)?rAM:"eeo:"+rAM,
                "?OS".equalsIgnoreCase(oS)?oS:"eeo:"+oS,
                "?Seller".equalsIgnoreCase(seller)?seller:"eeo:"+seller);

        if(!cPU.equalsIgnoreCase("?CPU")||!gPU.equalsIgnoreCase("?GPU")||!storage.equalsIgnoreCase("?Storage")
        ||!screenSize.equalsIgnoreCase("?ScreenSize")||!screenType.equalsIgnoreCase("?ScreenType")||!quantity.equalsIgnoreCase("?Quantity")
        ||!price.equalsIgnoreCase("?Price")) {

            String filterCondition = "FILTER (";
            String and = " && ";
            String closingBracket = ") . }";

            Boolean pre = false;
            if (!cPU.equalsIgnoreCase("?CPU")) {
                filterCondition += this.addRegexCondition("?CPU", cPU);
                pre = true;
            }
            if (!gPU.equalsIgnoreCase("?GPU")) {
                if (pre == true) {
                    filterCondition += and;
                } else pre = true;
                filterCondition += this.addRegexCondition("?GPU", gPU);
            }
            if (!storage.equalsIgnoreCase("?Storage")) {
                if (pre == true) {
                    filterCondition += and;
                } else pre = true;
                filterCondition += this.addRegexCondition("?Storage", storage);
            }
            if (!screenSize.equalsIgnoreCase("?ScreenSize")) {
                if (pre == true) {
                    filterCondition += and;
                } else pre = true;
                filterCondition += this.addRegexCondition("?ScreenSize", screenSize);
            }
            if (!screenType.equalsIgnoreCase("?ScreenType")) {
                if (pre == true) {
                    filterCondition += and;
                } else pre = true;
                filterCondition += this.addRegexCondition("?ScreenType", screenType);
            }
            if (!quantity.equalsIgnoreCase("?Quantity")) {
                if (pre == true) {
                    filterCondition += and;
                } else pre = true;
                filterCondition += this.addIntegerCondition("?Quantity", quantity);
            }
            if (!price.equalsIgnoreCase("?Price")) {
                if (pre == true) {
                    filterCondition += and;
                } else pre = true;
                filterCondition += this.addIntegerCondition("?Price", price);
            }
            filterCondition += closingBracket;
            mainQuery += filterCondition;
        } else{
            mainQuery += "}";
        }

        return executeSPARQLEndpoint(mainQuery);
    }

    private String addIntegerCondition(String key, String value) {
        return key+" "+value;
    }

    private String addRegexCondition(String key, String value) {
        return "REGEX("+key+", \""+value+"\")";
    }
}
