package decisiontree;

import java.util.*;

public class Results {
    private LinkedList<Test> testSets;
    
    public Results(){
        //init
        testSets = new LinkedList<Test>();
    }
    
    public void addTest(Test test){
        testSets.add(test);
    }
    public List<Test> getTests(){
        return testSets;
    }
    
    public double predictability(){
        double predictabilityRate =0;
        for(Test t : testSets){
           predictabilityRate +=t.ratio();
        }
        return predictabilityRate*10;
    }
    @Override
    public String toString(){
         String newline = System.getProperty("line.separator");
    	String results="";
    	int totalPos = 0;
    	int totalNeg = 0;
    	for(Test t : testSets){
    		totalPos+=t.getPos();
    		totalNeg+=t.getNeg();
    		results+="["+t.getPos()+" - "+t.getNeg()+"]";
    	}
    	results+=newline+"  Final Count:  Positive = "+totalPos+" Negative = "+totalNeg;
    	return results;
    }
}
