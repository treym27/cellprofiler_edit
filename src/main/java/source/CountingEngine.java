package source;

import fileutil.CSVUtil;
import fileutil.CommandUtil;
import mylogging.UseLogger;
import mylogging.MyLogger;

import java.io.IOException;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import java.io.File;
import java.io.FilenameFilter;
//this class is used to handle different stacks, given a parent directory

public class CountingEngine{
    
    public CountingEngine(String sourceDirectory, boolean batch){

        UseLogger logger = new UseLogger();
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }

        if(batch){
            
            File f = new File(sourceDirectory);
            String[] dirs = f.list(new FilenameFilter(){
                @Override
                public boolean accept(File current, String name){
                    return new File(current, name).isDirectory();
                }
            });
            //eventually, replace this with a function to get all subfolders
            

            for(String s : dirs) {
                int count = this.analyzeStack(sourceDirectory + "\\" + s);
                logger.log(" " + s + ": " + count);
            }
        }
        else{
            String s = "";
            int i = sourceDirectory.length() - 1;
            while(sourceDirectory.charAt(i) != '/' && sourceDirectory.charAt(i) != '\\' && i >= 0) {
                s = sourceDirectory.charAt(i) + s;
                i--;
            }
            int count = this.analyzeStack(sourceDirectory);
            logger.log(" " + s + ": " + count);
        }

    }

    //returns cell count of the stack
    public int analyzeStack(String sourceDirectory){
        CellProfiler cp = new CellProfiler(
            sourceDirectory
        );
        if( cp.runConfigPipeline()){

            String s = cp.runCountingPipeline();
            CSVUtil c = new CSVUtil(s);
            int count = c.getUniqueValueCount("TrackObjects_Label_30");
            System.out.println("Final results from " + s);
            System.out.println("\tCell Count: " + count + "\n\n\n");
            return count;
        
        }
        return -1;

    }
}