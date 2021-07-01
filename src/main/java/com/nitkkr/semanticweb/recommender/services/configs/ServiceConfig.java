package com.nitkkr.semanticweb.recommender.services.configs;

import com.nitkkr.semanticweb.recommender.constants.SPARQLQuery;
import com.nitkkr.semanticweb.recommender.services.implementations.MyOntology;
import com.nitkkr.semanticweb.recommender.services.implementations.RecommenderDomainServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    RecommenderDomainServiceImplementation recommenderDomainServiceImplementation(){
        return new RecommenderDomainServiceImplementation();
    }

    @Bean
    MyOntology myOntology(){
        return new MyOntology();
    }

    @Bean
    SPARQLQuery sparqlQuery(){
        return new SPARQLQuery();
    }
}
