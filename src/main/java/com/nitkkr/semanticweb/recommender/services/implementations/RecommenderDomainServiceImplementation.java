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
        return null;
    }

    public List<String> retrieveProduct(String ram, String processor) {
        return myOntology.executeSparql(ram, processor);
    }
}
