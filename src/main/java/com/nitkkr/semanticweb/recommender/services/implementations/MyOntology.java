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


    public void getInformationRelatedToAResource(String resourceLabel){
        OntClass resource = getResourceFromLabel(resourceLabel);
        Iterator iterator;

        System.out.println("SubClass-");
        iterator = resource.listSubClasses();       // the default- resource.listSubClasses(false)
        this.display(iterator);

        System.out.println("Direct SubClass-");
        iterator = resource.listSubClasses(true);
        this.display(iterator);

        System.out.println("SuperClasses-");
        iterator = resource.listSuperClasses();
        this.display(iterator);

        System.out.println("Direct SuperClass-");
        iterator = resource.listSuperClasses(true);
        this.display(iterator);

        System.out.println("Properties-");
        iterator = resource.listDeclaredProperties();
        this.display(iterator);

        System.out.println("Data type properties-");
        iterator = ontModel.listDatatypeProperties();
        this.display(iterator);

        System.out.println("Object properties-");
        iterator = ontModel.listObjectProperties();
        this.display(iterator);

        System.out.println("Namespace");
        iterator = ontModel.listNameSpaces();
        this.display(iterator);

        System.out.println("Instances-");
        iterator = resource.listInstances();
        this.display(iterator);

        System.out.println("DisjointClass-");
        iterator = resource.listDisjointWith();
        this.display(iterator);

        System.out.println("Equivalent Classes-");
        iterator = resource.listEquivalentClasses();
        this.display(iterator);

        System.out.println("Different From-");
        iterator = resource.listDifferentFrom();
        this.display(iterator);

        System.out.println("Same as-");
        iterator = resource.listSameAs();
        this.display(iterator);

        System.out.println("Is defined by-");
        iterator = resource.listIsDefinedBy();
        this.display(iterator);

        System.out.println("See also-");
        iterator = resource.listSeeAlso();
        this.display(iterator);

        System.out.println("version info-");
        iterator = resource.listVersionInfo();
        this.display(iterator);

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

    public void getOntologyRelatedInformation(){
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
    }

    public void getClassOfIndividual(String resourceLabel){
        Individual individual = ontModel.getIndividual(this.getBaseURIFromLabel(resourceLabel)+resourceLabel);
        Resource rdfType = individual.getRDFType();
        System.out.println(rdfType.getLocalName());
        OntClass ontClass = ontModel.getOntClass(rdfType.getURI());
        System.out.println(ontClass);
    }

    public void getAnnotationPropertyInLanguage(){
        Iterator annotationProperties = ontModel.listAnnotationProperties();
        AnnotationProperty annotationProperty = (AnnotationProperty) annotationProperties.next();
        System.out.println(annotationProperty);

        Iterator listProperties = annotationProperty.listProperties();
        StatementImpl iterator = (StatementImpl) listProperties.next();
        System.out.println(iterator);

        Model model = iterator.getModel();
        //listProperties.hasNext()
    }

    /*public Triple getRDFTriples() {
        Model model = ontModel.write(System.out, "N3", null);
        ArrayList<Triple> tripleArrayList = new ArrayList<>();
        Iterator iterator = model.listStatements();
        Triple triple = null;
        while (iterator.hasNext()) {
            StatementImpl statement = (StatementImpl) iterator.next();
            triple = statement.asTriple();
            tripleArrayList.add(triple);
            tripleArrayList.stream().forEach(System.out::println);
        }
        return triple;
    }*/

    public List<String> executeSparql(String sparqlQuery){
        setOntology(Constant.EEKG_RDF);
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution queryExecution  = QueryExecutionFactory.create(query,ontModel);
        List<String> resultList = getResult(queryExecution);
        return resultList;
    }


    public List<String> executeSparql(String productSpec1, String productSpec2){

        setOntology(Constant.EEKG_RDF);

        String sparqlQuery = this.getQuery(productSpec1, productSpec2);
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution queryExecution  = QueryExecutionFactory.create(query,ontModel);
        List<String> resultList = getResult(queryExecution);
        return resultList;
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

    private String getQuery(String productSpec1, String productSpec2) {
       return SPARQLQuery.getQueryFromTextFile("Query1");
    }
}
