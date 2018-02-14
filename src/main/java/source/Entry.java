package source;
import fileutil.CSVUtil;
import fileutil.CommandUtil;
import java.io.IOException;
import java.lang.*;

public class Entry{
    public static void main(String args[]){
      long start_time = System.nanoTime();
    	CellProfiler cp = new CellProfiler(
            "C:/Users/Steven/Desktop/Research/FARSIGHTTESTS/WORKING/Test_Images_Profiler/Batch/Buyout", 
            "C:/Users/Steven/Desktop/Research/CellProfilerWrapper/output"
        );
      if( cp.runConfigPipeline()){

          cp.runCountingPipeline();
      }
      long elapse_time = System.nanoTime() - start_time;
      System.out.println("elapse_time in seconds: " + elapse_time * 1e-9);
    }

}