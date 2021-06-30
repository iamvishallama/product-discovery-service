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
    public List<String> executeRecommendProduct(@RequestParam(value = "Category", defaultValue = "?Category") String category,
                                                @RequestParam(value = "Brand", defaultValue = "?Brand") String brand,
                                                @RequestParam(value = "CPU", defaultValue = "?CPU") String cPU,
                                                @RequestParam(value = "GPU", defaultValue = "?GPU") String gPU,
                                                @RequestParam(value = "OperatingSystem", defaultValue = "?OS") String oS,
                                                @RequestParam(value = "RAM", defaultValue = "?RAM") String rAM,
                                                @RequestParam(value = "ScreenSize", defaultValue = "?ScreenSize") String screenSize,
                                                @RequestParam(value = "ScreenType", defaultValue = "?ScreenType") String screenType,
                                                @RequestParam(value = "Storage", defaultValue = "?Storage") String storage,
                                                @RequestParam(value = "PriceInEuros", defaultValue = "?Price") String price,
                                                @RequestParam(value = "Seller", defaultValue = "?Seller") String seller,
                                                @RequestParam(value = "Quantity", defaultValue = "?Quantity") String quantity){
        return recommenderApplicationServiceImplementation.retrieveProduct(category, brand, cPU, gPU, oS, rAM, screenSize, screenType, storage, price, quantity, seller);
    }

    @RequestMapping(value = Constant.SPARQL_ENDPOINT, method = RequestMethod.POST)
    public List<String> executeSPARQLEndpoint(@RequestBody String sparqlQuery){
        return recommenderApplicationServiceImplementation.executeSPARQLEndpoint(sparqlQuery);
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
