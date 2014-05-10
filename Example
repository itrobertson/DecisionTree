package decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Ian Robertson
 */
public class Example<T> implements Iterable<String>{
     //Atribute name and value
    private LinkedHashMap<String, T> attributes;
    //Goal predicate; outcome.
    private T goal;
    
    public Example(List<String> attributeList){
          attributes = new LinkedHashMap<>();
        for(String attribute : attributeList){
            attributes.put(attribute, null);
        }
    }
   
   public Example(T goal){
       this.goal=goal;
       attributes = new LinkedHashMap<>();
   }
   
    public Example(LinkedHashMap<String, T> attributes, T goal){
        
        this.attributes=attributes;
        this.goal=goal;
        
    }
    
    public void addAttribute(String attributeName){
        attributes.put(attributeName, null);
    }
    
    public void addAttributeValue(String attribute, T value){
            attributes.put(attribute, value);
    }
    
    public void addAttributeValue(int index, T value){
       
        Object[] key =  attributes.keySet().toArray();
         attributes.put((String)key[index], value);
          //System.out.println("Example addAttributeValue : "+value);
    }
 
     public T getGoal() {
        return this.goal;
    }
      /*
       * @return Returns Object value of @param attributeName
       */
     public T getValue(String attributeName) {
      return attributes.get(attributeName);
     }
     
     public String getAttribute(String attribute){
         attributes.containsKey(attribute);
         return attribute;
     }
   
     
    public HashMap accessData(){
         return attributes;
    }
    public void setGoal(T goal){
        this.goal=goal;
    }
    public int size(){
        return attributes.size();
    }
    
@Override
    public Iterator<String> iterator() {
        return attributes.keySet().iterator();
    }
}
