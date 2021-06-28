package com.nitkkr.semanticweb.recommender.services.implementations;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecommenderServiceImplementation {

    MyOntology myOntology;

    public RecommenderServiceImplementation(){
        myOntology = new MyOntology();
    }

    public List<String> retrieveProducts(String ram, String processor) {
        return myOntology.executeSparql(ram, processor);
    }

}
