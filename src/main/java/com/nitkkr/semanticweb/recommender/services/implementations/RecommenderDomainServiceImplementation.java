package com.nitkkr.semanticweb.recommender.services.implementations;

import javax.inject.Inject;
import java.util.List;

public class RecommenderDomainServiceImplementation {

    @Inject
    MyOntology myOntology;

    public List<String> executeSPARQLEndpoint(String spqarqlQuery) {
        return myOntology.executeSparql(spqarqlQuery);
    }

    public List<String> executeRapidInformationExplorer(String keyword) {
        return myOntology.executeRapidInformationExplorer(keyword);
    }

    public List<String> retrieveProduct(String category, String brand, String cPU, String gPU, String oS, String rAM, String screenSize, String screenType, String storage, String price, String  quantity, String seller) {
        return myOntology.executeSparql(category, brand, cPU, gPU, oS, rAM, screenSize, screenType, storage, price, quantity, seller);
    }
}
