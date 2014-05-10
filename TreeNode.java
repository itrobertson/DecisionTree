package decisiontree;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TreeNode<T> 
{
   //edgeValue is Edge descriptor i.e. Yes, No, Full, Some
  private T edgeValue;
  //Outcome has a value when node contains an answer (Yes or No).
  private T outcome;
  //attributeName is the name of each attribute in the data sample.
  private String attributeName;
  private PrintWriter writer;
  private List<TreeNode> branches = new ArrayList<>();
  private TreeNode branch;

  public TreeNode(String attributeName) {
    this.attributeName=attributeName;
  } 
    
   public TreeNode(String leaf, T outcome){
     //this is a leaf Node
       attributeName=leaf;
       this.outcome=outcome;
  }
   
   public TreeNode findValue(String attributeName,T attributeValue){
       for(TreeNode node : branches){
           //System.out.println("NodeName: "+this.getAttributeName()+" ExampleName: "+attributeName+" NodeValue: "+node.getValue()+" ExampleValue: "+attributeValue+" Node: "+node.getOutcome());
           if(this.attributeName.equals(attributeName) && node.getValue().equals(attributeValue)){
               //System.out.println("NodeName: "+this.getAttributeName()+" ExampleName: "+attributeName+" NodeValue: "+node.getValue()+" ExampleValue: "+attributeValue);
               return node;
           }
       }
       return null;//Value could not be found so return null
      // throw new RuntimeException("Could not find value: " + attributeValue);
       
   }
   public TreeNode predict(Example e){
             String attributeName = this.getAttributeName();
             String eAttribute = e.getAttribute(attributeName);
             T attributeValue = (T) e.getValue(attributeName);
            // System.out.println("Node: "+attributeName+" Value: " +"  Example: "+eAttribute+" Value:");
              TreeNode tn =  findValue(eAttribute,attributeValue);
              if(tn==null){
                  return null;
              }
                //if outcome is not null it is a leaf node
               if(tn.getOutcome()!=null){
                   return tn;
               }else{//outcome is null and tn is not null so continue down tree deeper
                   return tn.predict(e);
               }
                // throw new RuntimeException("No match for value: " + attributeValue);
            }
   
   public boolean containsValue(T attributeValue){
       for(TreeNode node : branches){
           //System.out.println("treenode: "+node.getValue()+" new: "+attributeValue);
           return node.getValue().equals(attributeValue);
       }
       return false;
          
   }
             
    public TreeNode getNext(int index){
        branch=getBranches().get(index);
           return branch != null ? branch : getNext(index++);
        }
  /*public TreeNode(T newValue, DecisionTree<T> branch) 
  {
    value = newValue;
    this.branch=branch;
   branches.add(branch);
         
  }*/
  
  public TreeNode addBranch(T branchValue, TreeNode<T> child){
      branches.add(child);
      int childIndex = branches.indexOf(child);
      branches.get(childIndex).setValue(branchValue);
      return this;
    }
  public void setValue(T newValue){
      edgeValue=newValue;
  }
  //public DecisionTree getBranch(){
    //  return branch;
 // }
  
  public String getAttributeName(){
      return attributeName;
  }

  public T getValue(){
      return edgeValue;
  }
  public T getOutcome(){
      return outcome;
  }
  public List<TreeNode> getBranches(){
      return branches;
  }
  public void setPrintWriter(PrintWriter pr){
      writer=pr;
  }

  public void printTree(TreeNode<T> node){
      String newline = System.getProperty("line.separator");
      LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
      //String result = "";
      //OPTIONAL: if statement, - omit leaf parent nodes from printing
      if(node.getOutcome()==null){
      writer.write((newline+newline)+"Parent: "+node.getAttributeName()+" [Edge: "+node.getValue()+"]\n \r"+newline);
      writer.write(" [ "+newline);
      //writer.write(result);
      for(TreeNode d : node.branches){
            // result+="\t";
         if(d.getOutcome()!=null){
               writer.write("\tLeaf: "+d.getValue()+" => "+d.getOutcome()+newline);
               //writer.write(result);
         }else{
               writer.write("\tAttributeNode:" +d.getAttributeName()+" (Edge=>"+d.getValue()+").   "+newline);
               queue.addLast(d);
           }
      }
       writer.write(" ]\n"+newline);
      for(TreeNode n : queue){
          printTree(n);
      }
     
      //writer.write(result);
      writer.flush();
        }//end optional
      //return result;
  }


  

}  
