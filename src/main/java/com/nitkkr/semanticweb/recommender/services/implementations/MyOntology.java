package com.nitkkr.semanticweb.recommender.services.implementations;

import com.nitkkr.semanticweb.recommender.constants.Constant;
import com.nitkkr.semanticweb.recommender.constants.SPARQLQuery;
import org.apache.jena.ontology.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.impl.StatementImpl;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyOntology {

    static OntModel ontModel;

    public void setOntology(String owlFilePath) {
        ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
        this.loadOntology(owlFilePath);
    }

    private void loadOntology(String owlFilePath) {
        try {
            File file = new File(Constant.ONTOLOGY_DIRECTORY_PATH + owlFilePath);
            FileReader fileReader = new FileReader(file);
            ontModel.read(fileReader, null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getAllResource() {
        System.out.println("Resource-");
        Iterator classIterator = ontModel.listClasses();
        while(classIterator.hasNext()){
            OntClass ontClass = (OntClass) classIterator.next();
            String uri = ontClass.getURI();
            if(null!= uri){
                System.out.println("URI: "+uri+", Name: "+ontClass.getLocalName());
            }
        }
        System.out.println("__________________________________________________________");
    }

/*    //  Not applicable in everyplace
    public String getOntologyURI(){
        String ontologyURI = null;
        Iterator iterator = ontModel.listOntologies();
        if(iterator.hasNext()){
            Ontology ontology = (Ontology) iterator.next();
            ontologyURI = ontology.getURI();
            System.out.println("URI: "+ontologyURI);
        }
        System.out.println("__________________________________________________________");
        return ontologyURI;
    }*/

    public void getImportedClass(){
        System.out.println("Imported class-");
        ModelMaker modelMaker = ontModel.getImportModelMaker();
        Iterator iterator = modelMaker.listModels();
        while (iterator.hasNext()){
            System.out.println("Imported classes: "+ iterator.next());
        }
        System.out.println("__________________________________________________________");
    }

    private void display(Iterator iterator) {
        if(null!=iterator){
            while (iterator.hasNext()){
                System.out.println(iterator.next());
            }
        }else {
            System.out.println("Empty");
        }
        System.out.println("__________________________________________________________");
    }

    /*public void getGraphDetails(){
        Graph graph = ontModel.getGraph();

//        System.out.println("Number of prefixes: "+graph.getPrefixMapping().numPrefixes());
        System.out.println("Namespace Prefix map: "+ graph.getPrefixMapping().getNsPrefixMap());
//        System.out.println("Graph Size: "+graph.size());
//
//        System.out.println("Capabilities- "+ graph.getCapabilities());
//        System.out.println("Triples-" );
//        this.display(graph.find());
    }*/

    public void isObjectOrDataTypeProperty(String resourceLabel){
        OntClass ontClass = getResourceFromLabel(resourceLabel);
        Resource resource = ontModel.getResource(resourceLabel);

        System.out.println("Get source uri: "+resource.getURI());
        System.out.println("Local Name- "+resource.getLocalName());
        System.out.println("Namespace : " +resource.getNameSpace());
        StmtIterator iterator = resource.listProperties();
        display(iterator);
        System.out.println();
        //check for object or datatype property
    }

    private OntClass getResourceFromLabel(String resourceLabel) {
        return ontModel.getOntClass(this.getBaseURIFromLabel(resourceLabel)+resourceLabel);
    }

    public void getInstancesOfClass(){
        Iterator classes = ontModel.listClasses();
        while (classes.hasNext()){
            OntClass ontClass = (OntClass) classes.next();
            System.out.println("Class: "+ontClass);
            Iterator individuals = ontClass.listInstances();
            this.display(individuals);
        }
    }

    public void getIndividualsOfAProperty(String resourceLabel){
        OntClass ontClass = getResourceFromLabel(resourceLabel);
        Iterator individuals = ontClass.listInstances();
        while (individuals.hasNext()){
            Individual individual = (Individual) individuals.next();
            System.out.println(individual);
            this.displayPropertiesOfAnIndividual(individual);
        }
    }

    private void displayPropertiesOfAnIndividual(Individual individual) {
        Iterator properties = individual.listProperties();
        while (properties.hasNext()){
            StatementImpl property = (StatementImpl) properties.next();

            System.out.println(property);
            System.out.println(property.asTriple());
//
            RDFNode value = individual.getPropertyValue((Property) property);
//         System.out.println("Property: "+((Property) property).getLocalName()+"\nRDFNode value: "+value);
//            this.displayValuesOfAProperty(individual, property);
        }
    }

    private void displayValuesOfAProperty(Individual individual, Property property) {
        Iterator values = individual.listPropertyValues(property);
        while (values.hasNext()){
            RDFNode node = (RDFNode) values.next();
            System.out.println(node);
        }
    }

    public String getBaseURIFromLabel(String resourceLabel){
        String uri = null;
        Map<String, String> nsMap = ontModel.getNsPrefixMap();
        for(String ns : nsMap.values()){
            OntClass ontClass = ontModel.getOntClass(ns+resourceLabel);
            Individual individual = ontModel.getIndividual(ns+resourceLabel);

            if(null!=ontClass || null!=individual){
                uri = ns;
                break;
            }
        }
        return uri;
    }

    /*public void getOntologyRelatedInformation(){
        System.out.println("Imported Ontology: "+ontModel.listImportedOntologyURIs());

        System.out.println("List Ontologies-");
        display(ontModel.listOntologies());

        System.out.println("List Namespaces-");
        display(ontModel.listNameSpaces());

        System.out.println("List Classes-");
        display(ontModel.listClasses());

        System.out.println("List Objects -");
        display(ontModel.listObjects());

        System.out.println("List Individuals-");
        display(ontModel.listIndividuals());

        System.out.println("List Object Properties-");
        display(ontModel.listObjectProperties());

        System.out.println("List Annotation Property-");
        display(ontModel.listAnnotationProperties());

        System.out.println("List Data type Properties-");
        display(ontModel.listDatatypeProperties());

        System.out.println("List Data Ranges-");
        display(ontModel.listDataRanges());

        System.out.println("List Functional Properties-");
        display(ontModel.listFunctionalProperties());

        System.out.println("List Inverse Functional Properties-");
        display(ontModel.listInverseFunctionalProperties());

        System.out.println("List sub models-");
        display(ontModel.listSubModels());

        System.out.println("List Hierarchy Root Classes-");
        display(ontModel.listHierarchyRootClasses());
    }*/

    public List<String> executeSparql(String sparqlQuery){
        setOntology(Constant.EEKG_RDF);
        return this.executeAndGetResult(sparqlQuery);
    }

    public List<String> executeSparql(String category, String brand, String rAM, String oS, String seller){
        setOntology(Constant.EEKG_RDF);
        String sparqlQuery = this.getQuery(category, brand, rAM, oS, seller);
        return this.executeAndGetResult(sparqlQuery);
    }

    private List<String> executeAndGetResult(String sparqlQuery) {
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution queryExecution  = QueryExecutionFactory.create(query,ontModel);
        return getResult(queryExecution);
    }

    private List<String> getResult(QueryExecution qexec) {
        List<String> resultList = new ArrayList<>();
        try {
            Iterator<QuerySolution> results = qexec.execSelect() ;
            //ResultSet results = qexec.execSelect();

            Boolean flag = results.hasNext();
            if (!flag) {
                resultList.add("Product currently not available");
                resultList.add("Please select any other specification");
            }

            while(flag)
            {
                QuerySolution soln = results.next();
                Literal name = soln.getLiteral("x");
                resultList.add(soln.toString());
                System.out.println(soln);

                flag = results.hasNext();
            }

        }finally
        {
            qexec.close();
        }
        return resultList;
    }

    private String getQuery(String category, String brand, String rAM, String oS, String seller) {
        String makeQuery = SPARQLQuery.formQuery(category, brand, rAM, oS, seller);
        return SPARQLQuery.getQueryFromTextFile("Query1");
    }

    public List<String> executeRapidInformationExplorer(String keyword) {
        setOntology(Constant.EEKG_RDF);
        String sparqlQuery = SPARQLQuery.getKeywordSearchQuery(keyword);
        return this.executeAndGetResult(sparqlQuery);
    }
}
