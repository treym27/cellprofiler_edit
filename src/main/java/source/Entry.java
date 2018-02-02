package source;
import fileutil.CSVUtil;
import fileutil.CommandUtil;
import java.io.IOException;

public class Entry{
    public static void main(String args[]){
    	try {
    		CommandUtil thisCommand = new CommandUtil("C:/Program Files (x86)/CellProfiler/CellProfiler.exe", "C:/Users/Skyler/Documents/TestImages/TestImages/Batch/Buyout", "C:/Users/Skyler/Documents/CellProfilerWrapper/CellProfilerWrapper", "C:/Users/Skyler/Documents/CellProfilerWrapper/CellProfilerWrapper/Pipelines/Configuration/Config.cppipe");
        Process process = Runtime.getRuntime().exec(thisCommand.getCommand());
        
   		}
   		catch(IOException e) {
   			System.out.println(e);
   		}
    }
}