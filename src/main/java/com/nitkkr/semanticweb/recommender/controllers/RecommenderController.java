package com.nitkkr.semanticweb.recommender.controllers;

import com.nitkkr.semanticweb.recommender.constants.Constant;
import com.nitkkr.semanticweb.recommender.services.implementations.RecommenderApplicationServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.List;

@RestController
public class RecommenderController {

    @Autowired
    RecommenderApplicationServiceImplementation recommenderApplicationServiceImplementation;

    @RequestMapping(value = Constant.RECOMMEND_PRODUCT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> executeRecommendProduct(@RequestParam(value = "ram", defaultValue = "") String ram,
                                   @RequestParam(value = "processor", defaultValue = "") String processor){
        return recommenderApplicationServiceImplementation.retrieveProduct(ram, processor);
    }

    @RequestMapping(value = Constant.SPARQL_ENDPOINT, method = RequestMethod.POST)
    public List<String> executeSPARQLEndpoint(@RequestBody String spqarqlQuery){
        return recommenderApplicationServiceImplementation.executeSPARQLEndpoint(spqarqlQuery);
    }

    @RequestMapping(value = Constant.RAPID_INFORMATION_EXPLORER, method = RequestMethod.GET)
    public List<String> executeRapidInformationExplorer(@RequestParam(value = "keyword", defaultValue = "") String keyword){
        return recommenderApplicationServiceImplementation.executeRapidInformationExplorer(keyword);
    }

    @RequestMapping (value = Constant.UPLOAD_PRODUCT_CATALOG, method = RequestMethod.POST)
    public List<String> uploadAndLoadModel(@RequestParam("file") MultipartFile file){
        return Arrays.asList("Product Catalog has been uploaded!");
    }
}
