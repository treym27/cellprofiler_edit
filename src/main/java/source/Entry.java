package source;
import fileutil.CSVUtil;
import fileutil.CommandUtil;
import java.io.IOException;
import java.lang.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Entry{
    public static void main(String args[]){
      Date cur = new Date();
      SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy_HH-mm-ss");
      String dateString = cur.toString();
      try{
        dateString = format.format(cur);
        
      }
      catch(Exception e){
        System.out.println(e.toString());
        dateString = "";
      }
      System.out.println(dateString);
      if(dateString.equals(""))
        return;
      long start_time = System.nanoTime();
      String sourceDirectory = "C:/Users/Steven/Desktop/Research/FARSIGHTTESTS/WORKING/Test_Images_Profiler/Batch/Buyout";
    	CellProfiler cp = new CellProfiler(
            sourceDirectory, 
            sourceDirectory + "/" + dateString
        );
      if( cp.runConfigPipeline()){

          cp.runCountingPipeline();
      }
      long elapse_time = System.nanoTime() - start_time;
      System.out.println("elapse_time in seconds: " + elapse_time * 1e-9);

    }

}