package decisiontree;


public class Test {
    private int positive;
    private int negative;
    public Test(){
        positive =0;
        negative =0;
    }
    public int getPos(){
        return positive;
    }
    public int getNeg(){
        return negative;
    }
    public void incrementPos(){
        positive++;
    }
    public void incrementNeg(){
        negative++;
    }
    public double ratio(){
        return 1-(double) negative/positive;
    }
    @Override
        public String toString(){
          return "[Positive = "+positive+" Negative = "+negative+" Ratio = "+ratio()+"]";
        }
}
