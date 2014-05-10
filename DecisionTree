package decisiontree;

import java.io.PrintWriter;
import java.util.*;


public class DecisionTree<E> {

     private PrintWriter writerTree;
     private TreeNode<E> root;
    //@result used for printing DecisionTree
     private String result = "";
  

        public DecisionTree(){
            //init
        }
        public DecisionTree(String attributeName){
         root = new TreeNode<>(attributeName);
        }
       
        
        public DecisionTree(String leafNode, E value){
            root = new TreeNode<>(leafNode,value);
        }
        
        /**
         * 
         * @return E and String getters are used by TreeNode 
         * as Branches data structure is of type <DecisionTree>
         *
         */
        public String getAttributeName(){
            return root.getAttributeName();
        }
        public E getValue(){
            return root.getValue();
        }
        public E getOutcome(){
            return root.getOutcome();
        }
        public void setValue(E edgeValue){
            root.setValue(edgeValue);
        }
        
        public void addBranch(E value, DecisionTree<E> tree)
        {
           root.addBranch(value,tree.getTree());
        }
        
        
        public TreeNode<E> getTree(){
            return root;
        }
        
         public E predict(Example e) {
             //System.out.println("E: "+e.accessData()+" "+e.getGoal()+" Predict: "+root.predict(e).getOutcome());
            E outcome = (E) root.predict(e);
            if(outcome!=null){
           return (E) root.predict(e).getOutcome();
            }else{
                return null;
            }
          }
         
         
        /**
         * 
         * getBranches() can be called from outside DecisionTree in order to 
         * initiate recursion, as well as internally for recursion.
         */
        public void getBranches(PrintWriter writerTree){
            this.writerTree=writerTree;
            //System.out.println(root.printTree(writerTree));
            getBranches(root);
        }
        
        public void getBranches(TreeNode branchSet){
            
         writerTree.println();
         writerTree.write("///////////////////////////////////////START NEW TREE");
         writerTree.println();
         root.setPrintWriter(writerTree);
         root.printTree(root);
                 
        }
        
    @Override
        public String toString(){
          return root.getAttributeName();
        }
        
       
        
}
