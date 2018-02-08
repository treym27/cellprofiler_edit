package source;
import fileutil.CSVUtil;
import fileutil.CommandUtil;
import java.io.IOException;

public class Entry{
    public static void main(String args[]){
    	try {
    		CommandUtil thisCommand = new CommandUtil(
                "C:/Program Files (x86)/CellProfiler/CellProfiler.exe",
                "C:/Users/Steven/Desktop/Research/FARSIGHTTESTS/WORKING/Test_Images_Profiler/Batch/Buyout",
                "C:/Users/Steven/Desktop/Research/CellProfilerWrapper/output",
                "C:/Users/Steven/Desktop/Research/CellProfilerWrapper/Pipelines/Configuration/Config.cppipe");
        Process process = Runtime.getRuntime().exec(thisCommand.getCommand());
        
   		}
   		catch(IOException e) {
   			System.out.println(e);
   		}
    }
}