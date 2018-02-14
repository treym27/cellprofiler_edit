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
		return this.programPath + " -c -i " + this.imagesPath + " -o " + this.outPath + " -p " + this.pipePath + " -L DEBUG";
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
	    		File f = new File(dirPath + "Temp\\" + zList.get(i).getName());
	    		if(!f.exists())
	    			Files.copy(file.toPath(), (new File(dirPath + "Temp\\" + zList.get(i).getName())).toPath());
	    		InputStream is = null;
			    OutputStream os = null;
			    try {
			        is = new FileInputStream(file);
			        os = new FileOutputStream(new File(dirPath + "Temp\\" + zList.get(i).getName()));
			        byte[] buffer = new byte[1024];
			        int length;
			        while ((length = is.read(buffer)) > 0) {
			            os.write(buffer, 0, length);
			        }
			    } finally {
			        is.close();
			        os.close();
			    }

			}
			return dirPath + "Temp\\";
    	}
    	catch(IOException e) {
    		System.out.println(e);
    		return "0";
    	}
    }
    public void deleteTemp(String tempName) {
    	File folder = new File(tempName);
	    File[] fileArr = folder.listFiles();
	    for(int i = 0; i < fileArr.length; i++) {
	    	fileArr[i].delete();
	    }
	    folder.delete();
    }
}