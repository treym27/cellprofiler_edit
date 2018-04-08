package source;



public class CellProfilerResults{
    private String stackName;
    private int blueCount;
    private int redCount;

    public CellProfilerResults(String stackName){
        this.stackName = stackName;
        this.blueCount = -1;
        this.redCount = -1;
    }

    public String getName(){
        return this.stackName;
    }

    public int getBlueCount(){
        return this.blueCount;
    }
    public void setBlueCount(int counts){
        this.blueCount = counts;
    }




    public int getRedCount(){
        return this.redCount;
    }
    public void setRedCount(int counts){
        this.redCount = counts;
    }
}