package com.nitkkr.semanticweb.recommender.services.implementations;

import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.util.List;

@Service
public class RecommenderApplicationServiceImplementation {

    @Inject
    RecommenderDomainServiceImplementation recommenderDomainServiceImplementation;

    public List<String> retrieveProduct(String category, String brand, String cPU, String gPU, String oS, String rAM, String screenSize, String screenType, String storage, String price, String  quantity, String seller) {
        return recommenderDomainServiceImplementation.retrieveProduct(category, brand, cPU, gPU, oS, rAM, screenSize, screenType, storage, price, quantity, seller);
    }

    public List<String> executeSPARQLEndpoint(String sparqlQuery) {
        return recommenderDomainServiceImplementation.executeSPARQLEndpoint(sparqlQuery) ;
    }

    public List<String> executeRapidInformationExplorer(String keyword) {
        return recommenderDomainServiceImplementation.executeRapidInformationExplorer(keyword);
    }
}
