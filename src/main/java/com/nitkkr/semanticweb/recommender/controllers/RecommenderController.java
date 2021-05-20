package com.nitkkr.semanticweb.recommender.controllers;

import com.nitkkr.semanticweb.recommender.constants.Constant;
import com.nitkkr.semanticweb.recommender.services.implementations.RecommenderServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecommenderController {

    @Autowired
    RecommenderServiceImplementation recommenderServiceImplementation;

    @RequestMapping(value = Constant.SERVICE_BASE_URL + "/recommendProduct", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getTriple (@RequestParam(value = "ram", defaultValue = "") String ram,
                                   @RequestParam(value = "processor", defaultValue = "") String processor){
        return recommenderServiceImplementation.retrieveProducts(ram, processor);
    }
}
