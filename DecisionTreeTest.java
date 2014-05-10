package decisiontree;

import java.io.*;
import java.util.*;


public class DecisionTreeTest {
    
      private DecisionTree tree;
      private static Data testData;
      private static Data trainingData;
      private static List<String> attributeList;
      private static  BufferedReader reader;
      private LinkedHashMap commonAttributeValues = new LinkedHashMap();
      private PrintWriter writerInfoGain;
      private PrintWriter writerTree;
      private PrintWriter writerResults;
    
    public DecisionTreeTest(List<String> attributeNamesList,String address,String seperator, boolean outcomeIsFirst,String missing) throws IOException{
         //System.out.println("File: "+address+" i:"+attributeNamesList);
         writerInfoGain = new PrintWriter("information-gain.txt", "UTF-8");
         writerTree = new PrintWriter("decision-tree.txt","UTF-8");
         writerResults = new PrintWriter("results.txt","UTF-8");
          setAttributeList(attributeNamesList);
          uploadFile(address,seperator,outcomeIsFirst,missing);
      }
    public DecisionTreeTest(Data examples, List<String> attributes){
        
        tree = decisionTreeLearning(examples, attributes, null);
  
    }
    
    public DecisionTree decisionTreeLearning(Data examples, List<String> attributes, Data parentExamples){
        
            if(examples.size()==0){
                //System.out.println("Examples size ==0");
                  return new DecisionTree("LeafNode",pluralityValue(parentExamples));
            }else if(sameClassification(examples)){//examples all same
                //System.out.println("Same Classification");
                  return new DecisionTree("LeafNode",examples.getFirst().getGoal());
            }else if(attributes.isEmpty()){
                //System.out.println("Attributes are empty"+ pluralityValue(examples));
                  return new DecisionTree("LeafNode",pluralityValue(examples));
            }else{
           
             String A = importance(attributes, examples);
             //System.out.println("String A: "+A);
             DecisionTree tree = new DecisionTree(A);
               
             for(Object value : examples.getValuesFor(A)){
                 //System.out.println("Find: "+value);
                 Data exampleSubset = examples.find(A, value);
                 attributes.remove(A);
                 DecisionTree subTree = decisionTreeLearning(exampleSubset, attributes, examples);
                 tree.addBranch(value, subTree);
               }       
             return tree;
       }
    }
  
    private String importance(List<String> attributes, Data examples ){
        
               double greatestGain = 0.0;
               String attribute = attributes.get(0);
                       for (String attributeName : attributes) {
                                 double gain = examples.getInformationGain(attributeName);
                                  //System.out.println("Greatest Gain: "+gain+" By attribute: "+attributeName);
                                 writerInfoGain.println();
                                 writerInfoGain.write("Gain: "+gain+" By attribute: "+attributeName);
                                 //writerInfoGain.println();
                               
                                 if (gain > greatestGain) {
                                    greatestGain = gain;
                                    
                                     attribute = attributeName;
                                    }
                       }
                        //System.out.println( "Attribute chozen: "+attribute);
                       writerInfoGain.println();
                       writerInfoGain.write("Attribute chosen: "+attribute+"\n");
                       writerInfoGain.println();
                       writerInfoGain.flush();
                               return attribute;
    }
    
        
    private Object pluralityValue(Data examples){
         ArrayList<Object> results = new ArrayList<Object>();
        for (Example e : examples) {
            //System.out.println("plurity: "+e.accessData());
            results.add( (Object) e.getGoal());
        }
        Object result;
        result = maxKey(results);
        //System.out.println("result: "+results);
        return result;
    }
    
    public <Object> Object maxKey(List<Object> l) {
		HashMap<Object, Integer> hash = new HashMap<Object, Integer>();
		for (Object obj : l) {
			if (hash.containsKey(obj)) {
				hash.put(obj, hash.get(obj).intValue() + 1);
			} else {
				hash.put(obj, 1);
			}
		}

		Object maxkey = hash.keySet().iterator().next();
		for (Object key : hash.keySet()) {
			if (hash.get(key) > hash.get(maxkey)) {
				maxkey = key;
			}
		}
                //System.out.println("max: "+maxkey);
		return maxkey;
	}
    
    private  boolean sameClassification(Data examples){
        Object classification = examples.getFirst().getGoal();
       List<Example> set =  examples.accessData();
        for(Example e : set){
            if(!e.getGoal().equals(classification)){
                return false;
            }
        }
        return true;
    }

    
    public void setAttributeList(List<String> attributes){
        attributeList= new ArrayList(attributes);
       // System.out.println(attributeList);
    }
    public List getAttributelist(){
        return attributeList;
    }
    
    private int countLines(String filename) throws IOException {
    InputStream is = new BufferedInputStream(new FileInputStream(filename));
    try {
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        boolean endsWithoutNewLine = false;
        while ((readChars = is.read(c)) != -1) {
            for (int i = 0; i < readChars; i++) {
                if (c[i] == '\n') {
                    count++;
                }
            }
            endsWithoutNewLine = (c[readChars - 1] != '\n');
        }
        if(endsWithoutNewLine) {
            count++;
        } 
        return count;
    } finally {
        is.close();
    }
}
     
private boolean inRange(int startIndex, int stopIndex, int index){
    	return index>=startIndex && index<stopIndex;
    }
    
    ///////UPLOAD / TRAIN / TEST
public void uploadFile(String address, String separator, boolean outcomeIsFirstElement,String missing) throws IOException {
    //System.out.println("upload(): "+address);
       testData = new Data();
       trainingData = new Data();
     
       Results results = new Results();
       int fileSize = countLines(address);
       int oneTenth = fileSize/10;
       int stopIndex = 0;
       int startIndex = 0;
        
                for(int i =0; i<10;i++){
                	stopIndex +=oneTenth;
                	testData = new Data();
                         trainingData = new Data();
                	 //System.out.println("Index: "+i+" start: "+startIndex+"  stop: "+stopIndex);
                	reader = new BufferedReader(new FileReader(address));
                        String inStream = reader.readLine();
                        
                	for( int index = 0; index<fileSize; index++){
                			 //System.out.println("inside Index: "+i+" Index: "+index+" start: "+startIndex+"  stop: "+stopIndex+" inRange: "+inRange(startIndex,stopIndex,index));
                                        if(inRange(startIndex,stopIndex,index)){ 
                			 testData.add(addLine(inStream, separator, outcomeIsFirstElement));
                                    }else{
                			 trainingData.add(addLine(inStream, separator, outcomeIsFirstElement));  
                                    }
                         inStream = reader.readLine();
                	}
                        
                    //Find and eliminate unknown or missing data, missisng data indicator is signified within second argument
                    tree = train(trainingData,missing);
                  
                    //System.out.println("end inside-for-loop: "+attributeList+" i: "+tree.getAttributeName()+" training:::");
                    
                    tree.getBranches(writerTree);
                    writerTree.flush();
                    Test test = test(tree,testData);
                    results.addTest(test);
                    
                	startIndex = stopIndex;
                }
                 String newline = System.getProperty("line.separator");
         writerResults.write(newline+"ResultSet: "+newline+" ");
         for(Test t : results.getTests()){
             writerResults.write(newline+"\t"+t.toString());
         }
          writerResults.write(newline+newline+"Predictability: "+results.predictability()+"%"+newline);
         writerResults.write(newline+"ResultsData"+newline+results);
         writerResults.flush();
         writerResults.close();
         System.out.println("\nResultSet "+results.getTests()+" \nPredictability: "+results.predictability());
        System.out.println("\nResultsData \n "+results+"   ");
        
                }
        


private static Example addLine(String line, String separator, boolean outcomeIsFirstElement) {
        // set attributes
     //System.out.println("parts[] " + line);
        String[] parts = line.split(separator);
        
        Example<String> newExample = new Example<String>(attributeList);
        if(outcomeIsFirstElement && parts!=null)
        {   
            // add First outcome as Goal value
            newExample.setGoal(parts[0]); 
            for (int i = 0; i < parts.length-1; i++) {
                 //System.out.println("index first: "+(i)+" part:"+parts[i+1]);
                newExample.addAttributeValue(i, parts[i+1].trim());
            }
         }else{
        	 if(parts!=null){
            //Assuming outcome is then LAST element in each file line
            for (int i = 0;i < parts.length-1; i++) {
                //System.out.println("some ::: "+i+" blah :::"+ parts[i].trim());
                newExample.addAttributeValue(i, parts[i].trim());
            }
            // add Last outcome as Goal value
            //System.out.println("index last: "+(parts.length-1)+" part:"+parts[parts.length-1]);
            newExample.setGoal(parts[parts.length-1]);
           
        }}
        //System.out.println("Example data: "+newExample.accessData());
        return newExample;
        
}

public Data resolveData(Data examples, String missing){
   /**
    * This method first searches through each example in the set,
    * then searches through each attribute for the specific missing indicator.
    * If indicator if found it is randomly replaced 
    */ 
  for(Example e : examples.accessData()){
      
        if(e.accessData().containsValue(missing.trim())){
        Iterator it =  e.accessData().entrySet().iterator();
        
        while(it.hasNext()){
            Map.Entry pairs = (Map.Entry)it.next();
             String key = (String) pairs.getKey();
             Object value =  pairs.getValue();
              
                // System.out.println("Missing: "+key+" V: "+value);
             if(value.equals(missing.trim())){
                 HashSet<Object> values = examples.getValuesFor(key);
                 values.remove(missing);
                 //System.out.println("Missing: "+key+" V: "+value);
                 //check then add attribute to hashmap listing most common attribute value
                 if(commonAttributeValues.containsKey(key)){
                     value =  commonAttributeValues.get(key);
                 }else{
                    
                 // Assign most common value of 'a' among other examples with same target value.
                  HashMap<Object, Integer> valueComparison = new HashMap<>();
                   //get example output
                  String output = (String) e.getGoal();
                   //search other examples for most common attribute value with same output
                
                  for (Example ex : examples.accessData()){
                     // System.out.println("main loop: "+ex.getAttribute(key) +" V: "+ex.getValue(key));
                      if(output.equals(ex.getGoal())){
                          Object aValue = ex.getValue(key);
                          //System.out.println("aVal: "+aValue+" misVal: "+ex.getValue(key));
                          if(valueComparison.containsKey(aValue)){
                              int aValueOccurances = valueComparison.get(aValue);
                              aValueOccurances++;
                              valueComparison.put(aValue, aValueOccurances);
                          }else{
                              valueComparison.put(aValue, 1);
                          }
                          
                      value =  mostCommonValue(valueComparison);
                  
                   commonAttributeValues.put(key, value);
                 
                  }}
                  
                   
                  //System.out.println("End: "+e.getAttribute(key) +" V: "+value);
                 }
                 
                 /*HashSet<Object> values = examples.getValuesFor(key);
                 values.remove(missing);
                 //randomly replace missing attribute value with a value from the set of values for that attribute.
                 Random rand = new Random();
                 int randInt = rand.nextInt(values.size());
                 String replacementValue = (String) values.toArray()[randInt];
                 
                //ensure random replacement does not access missing/bad value
                 while(replacementValue == null){
                    randInt = rand.nextInt(values.size());
                    replacementValue = (String) values.toArray()[randInt];
                 }*/
                 //System.out.println(replacementValue);
                  e.addAttributeValue(key, value);
                 //System.out.println(e.getValue(key));
              
              }
        }
        }
    }
    return examples;
}

private Object mostCommonValue(HashMap values){
    
    Iterator it =  values.entrySet().iterator();
    Object mostCommonElement = null ;
    int currentHighestValue = 0;
        while(it.hasNext()){
            Map.Entry pairs = (Map.Entry)it.next();
             Object key = (String) pairs.getKey();
             int value = (int) pairs.getValue();
             //System.out.println("MC: "+1+" V: "+values);
             if(value>currentHighestValue){
                 currentHighestValue = value;
                 mostCommonElement = key;
             }
        }
    return mostCommonElement;
}
private DecisionTree train(Data examples, String missing) {
    
     examples=resolveData(examples,missing);
      //System.out.println("Train() : "+attributeList+"  Next: "+examples.accessData().get(0).getGoal());
      List newList = new ArrayList(attributeList);
       return decisionTreeLearning(examples, newList, null);
    }

private Test test(DecisionTree tree,Data examples) {
        Test test = new Test();
        //System.out.println("examples: "+examples.size());
        for (Example e : examples) {
           // System.out.println("Name: "+e+"e goal: "+e.getGoal()+" predict: "+tree.predict(e));
            String treeValue = (String) tree.predict(e);
            //if(treeValue==null){System.out.println( treeValue);}
            if (treeValue !=null && e.getGoal().equals(treeValue)) {
                test.incrementPos();
            } else {
                test.incrementNeg();
            }
        }
        return test;
    }


public static void main(String[] args) throws IOException {
        
        ArrayList<String> attributeNamesList = new ArrayList<String>();
        String attributeNames = "src\\decisiontree\\income-names.txt";
        //load attribute names from file
        BufferedReader reader = new BufferedReader(new FileReader(attributeNames));
            String inStream = reader.readLine();
            while (inStream!=null) {
                  //System.out.println(inStream);
                attributeNamesList.add(inStream.trim());
                
                inStream = reader.readLine();
            }
        
        
        String address = "src\\decisiontree\\income.csv";
        String seperator = ",";
        String missing = "?";
        //DecisionTreeTest decisionTreeTest = new DecisionTreeTest(data,an); 
        //decisionTreeTest.getTree().getBranches();
        System.out.println("\nDESISION TREE UPLOAD:  \n\n");
        boolean outcomeIsFirst = false;
        DecisionTreeTest DTTest = new DecisionTreeTest(attributeNamesList,address,seperator,outcomeIsFirst,missing);
         //DTTest.setAttributeList(attributeNamesList);
         //DTTest.uploadFile(DTTest,address,seperator,outcomeIsFirst);
       
         
    }
    
}


