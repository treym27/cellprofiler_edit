package source;
import fileutil.CSVUtil;
import fileutil.FileCopyUtil;
import java.io.IOException;

public class Entry{
    public static void main(String args[]){
    	try {
    		FileCopyUtil getImagePath = new FileCopyUtil("C:/Users/Skyler/Documents/TestImages/TestImages/Batch/Buyout");
    		String imagePath = getImagePath.condenseDirectory();
        Process process = Runtime.getRuntime().exec("\"C:/Program Files (x86)/CellProfiler/CellProfiler.exe\" -c -r -i " + imagePath + " -o C:/Users/Skyler/Documents -p C:/Users/Skyler/Documents/Version3/Config.cppipe");

   		}
   		catch(IOException e) {
   			System.out.println(e);
   		}
    }
}