package com.nitkkr.semanticweb.recommender.services.implementations;

import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.util.List;

@Service
public class RecommenderApplicationServiceImplementation {

    @Inject
    RecommenderDomainServiceImplementation recommenderDomainServiceImplementation;

    public List<String> retrieveProduct(String ram, String processor) {
        return recommenderDomainServiceImplementation.retrieveProduct(ram, processor);
    }

    public List<String> executeSPARQLEndpoint(String sparqlQuery) {
        return recommenderDomainServiceImplementation.executeSPARQLEndpoint(sparqlQuery) ;
    }

    public List<String> executeRapidInformationExplorer(String keyword) {
        return recommenderDomainServiceImplementation.executeRapidInformationExplorer(keyword);
    }
}
