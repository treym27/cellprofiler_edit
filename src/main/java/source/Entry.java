package source;
import fileutil.CSVUtil;
import fileutil.CommandUtil;
import java.io.IOException;

public class Entry{
    public static void main(String args[]){
    	CellProfiler cp = new CellProfiler(
            "C:/Users/Steven/Desktop/Research/FARSIGHTTESTS/WORKING/Test_Images_Profiler/Batch/Buyout", 
            "C:/Users/Steven/Desktop/Research/CellProfilerWrapper/output"
        );
        cp.runConfigPipeline();
        cp.runCountingPipeline();
    }
}