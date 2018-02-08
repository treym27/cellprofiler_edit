package fileutil;
import java.io.*;
import java.util.*;
import java.nio.file.Files;

public class CommandUtil {
	private String programPath;
	private String imagesPath;
	private String outPath;
	private String pipePath;

	public CommandUtil(String programPath, String imagesPath, String outPath, String pipePath) {
		this.programPath = programPath;
		this.imagesPath = condenseDirectory(imagesPath);
		this.outPath = outPath;
		this.pipePath = pipePath;
	}

	public String getCommand() {
		return this.programPath + " -c -i " + this.imagesPath + " -o " + this.outPath + " -p " + this.pipePath;
	}
	
    public String condenseDirectory(String dirPath) {
    	System.out.println("Loading config directory");
    	try {
	    	File folder = new File(dirPath);
	    	File[] fileArr = folder.listFiles();
	    	ArrayList<File> zList = new ArrayList<File>();
	    	for(int i = 0; i < fileArr.length; i++) {
	    		if(fileArr[i].getName().contains("_Z")) {
	    			zList.add(fileArr[i]);
	    		}
	    	}

	    	int start = 0;
	    	int end = 0;
	    	if(zList.size()%2 == 0) {
	    		start = zList.size()/2 - 9;
	    		end = zList.size()/2 + 10;
	    	}
	    	else {
	    		start = zList.size()/2 - 10;
	    		end = zList.size()/2 + 11;
	    	}

	    	for(int i = start; i < end; i++) {
	    		File file = zList.get(i);
	    		File newDir = new File(dirPath + "Temp\\");
	    		boolean success = newDir.mkdir();
	    		Files.copy(file.toPath(), (new File(dirPath + "Temp\\" + zList.get(i).getName())).toPath());
	    		//System.out.println(zList.get(i).getName());
	    	}
	    	return dirPath + "Temp\\";
    	}
    	catch(IOException e) {
    		System.out.println(e);
    		return "0";
    	}
    }
}