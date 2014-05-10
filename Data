package decisiontree;

import java.util.*;
/**
 * 
 * @author Ian Robertson
 */
public class Data implements Iterable<Example>{
    
    public ArrayList<Example> examples;

    public Data(){
        examples = new ArrayList<>();
    }
    
    public void add(Example e) {
        examples.add(e);
    }
    
    public int size() {
        return examples.size();
    }
  /**
     * Return each distinct value for this attribute in the example set
     */
    public <T> HashSet<T> getValuesFor(String attributeName) {
        HashSet<T> values = new HashSet<>();
        T value = null;
        for (Example e : this.examples) {
            value= (T)e.getValue(attributeName);
            if(value!=null){
                values.add(value);
            }
        }
        //System.out.println("Test: "+attributeName+" val: "+values);
        return values;
    }
    
    
    public double getInformationGain(String attributeName) {
        
        HashMap<Object, Data> attributeValueMap = this.split(attributeName);
            //System.out.println("Test: "+attributeValueMap);
        double totalSize = this.examples.size();
        double remainder = 0.0;
        for (Object attributeValue : attributeValueMap.keySet()) {
            double matchingValueSize = attributeValueMap.get(attributeValue).size();
            double outputEntropy = attributeValueMap.get(attributeValue).getEntropy();
            remainder += (matchingValueSize / totalSize) * outputEntropy;
                //System.out.println("AttributeName: "+attributeName+" Enthropy: "+outputEntropy+" MatchingValueSize: "+matchingValueSize+" Remainder: "+remainder);
        }
        //System.out.println("remainder: "+remainder+" att: "+attributeName);
        return this.getEntropy() - remainder;
    }
    
    /**
     * 
     * @param <T> Value of the Attribute. For determining if there is a correlation between Attribute Value
     * and the outcome value.
     * @return Returns HashMap of All values for each Attribute
     */
    public <T> HashMap<T, Data> split(String attributeName) {
        HashMap<T, Data> results = new HashMap<T, Data>();
        for (Example e : this.examples) {
            
            T attributeValue = (T) e.getValue(attributeName);
            //System.out.println(e.getValue(attributeName));
            if (results.containsKey(attributeValue)) {
                results.get(attributeValue).add(e);
            } else {
                Data data = new Data();
                data.add(e);
                results.put(attributeValue, data);
            }
        }
     
        return results;
    }
    
    
    public Example getFirst(){
        return examples.get(0);
    }
    
     public Data find(String aKey, Object aValue) {
        Data data = new Data();
        for (Example e : examples) {
            Object ob = e.accessData().get(aKey);
            //System.out.println("aValue: "+ob);
            if (ob!=null && ob.equals(aValue)) {
                data.add(e);
            }
        }
        return data;
    }
     
     
    public ArrayList<Example> accessData(){
         return examples;
    }
   
    
    public double getEntropy(){
        HashMap<Object, Integer> distribution = new HashMap<>();
        // count number of values v_k for variable V, page 704
        for (Example e : examples) {
            Object value = e.getGoal();
            if (distribution.containsKey(value)) {
                distribution.put(value, distribution.get(value) + 1);
            } else {
                distribution.put(value, 1);
            }
        }
        // normalize probability distribution, page 493
        double[] normalizedDistribution = new double[distribution.keySet().size()];
        Iterator<Integer> iter = distribution.values().iterator();
        for (int i = 0; i < normalizedDistribution.length; i++) {
            normalizedDistribution[i] = iter.next();
        }
        normalizedDistribution = normalize(normalizedDistribution);
        // calculate entropy H(V), page 704		
        double total = 0.0;
        for (double d : normalizedDistribution) {
                 total += d * (Math.log(d) / Math.log(2));
        }
        return -1.0 * total;
    }

    
    //References pages 493 and 704
   public static double[] normalize(double[] probDist) {
		int len = probDist.length;
		double total = 0.0;
		for (double d : probDist) {
			total = total + d;
		}

		double[] normalized = new double[len];
		if (total != 0) {
			for (int i = 0; i < len; i++) {
				normalized[i] = probDist[i] / total;
			}
		}

		return normalized;
	}
   
   
   
   
   
    
    @Override
    public Iterator<Example> iterator() {
        return examples.iterator();
    }

}
