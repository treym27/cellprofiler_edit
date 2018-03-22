package source;

import fileutil.CSVUtil;
import fileutil.CommandUtil;
import mylogging.UseLogger;
import mylogging.MyLogger;
import pipelineauthor.PipelineFactory;

import java.io.IOException;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import java.io.File;
import java.io.FilenameFilter;
//this class is used to handle different stacks, given a parent directory

public class CountingEngine{
    ArrayList<String> setNames;
    ArrayList<Integer> setCounts;
    
    public CountingEngine(String sourceDirectory, boolean batch){
        this.setNames = new ArrayList<String>();
        this.setCounts = new ArrayList<Integer>();

        UseLogger logger = new UseLogger();
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
        this.setupConfig();
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
                logger.log("Starting " + s + " Stack");
                logger.enterSection();
                int count = this.analyzeStack(sourceDirectory + "\\" + s);
                    if(count > -1) {
                        this.setNames.add(s);
                        this.setCounts.add(count);
                        logger.log("Cell Count: " + count);
                    }
                    else
                        logger.log("Cell count failed.");
                logger.exitSection();
                logger.log(s + " stack completed.");
            }
        }
        else{
            String s = "";
            int i = sourceDirectory.length() - 1;
            while(sourceDirectory.charAt(i) != '/' && sourceDirectory.charAt(i) != '\\' && i >= 0) {
                s = sourceDirectory.charAt(i) + s;
                i--;
            }
            logger.log("Starting " + s + " Stack");
            logger.enterSection();
                int count = this.analyzeStack(sourceDirectory);
                if(count > -1) {
                    this.setNames.add(s);
                    this.setCounts.add(count);
                    logger.log("Cell count: " + count);
                }
                else
                    logger.log("Cell count failed.");
            logger.exitSection();
            logger.log(s + " stack completed.");

        }

        this.generateReport();

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
            
            return count;
        
        }
        return -1;

    }

    private void generateReport(){
        UseLogger logger = new UseLogger();
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
        logger.log("");
        logger.log("");
        logger.log("Final Successful Counts: ");
        logger.enterSection();
            for(int i = 0; i < this.setNames.size(); i++){
                logger.log(this.setNames.get(i) + " : " + this.setCounts.get(i));
            }

        logger.exitSection();
    }


    public void setupConfig(){
        UseLogger logger = new UseLogger();
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
        //if reset is true, override default values
        boolean reset = JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(
        null, 
        "Would you like to use default config values?",
        "Cell Profiler Wrapper",
        JOptionPane.YES_NO_OPTION);
        if(reset){

            double min =  Double.parseDouble(JOptionPane.showInputDialog(null," Enter min modifier: "));
            double max =  Double.parseDouble(JOptionPane.showInputDialog(null," Enter min modifier: "));
            if(min >= 0 && max >= 0){
                PipelineFactory.minModifier = min;
                PipelineFactory.maxModifier = max;
                logger.log("Overwrite of default config settings successful.");
                
            }
            else{
                PipelineFactory.minModifier = 1;
                PipelineFactory.maxModifier = 1;
                logger.log("Overwrite failed. Using default config values. ");
            }

        }
        else{
            PipelineFactory.minModifier = 1;
            PipelineFactory.maxModifier = 1;
            logger.log("Using default config values. ");
        }
        logger.enterSection();
            logger.log("MinModifier: " + PipelineFactory.minModifier + " \tMaxModifier: " + PipelineFactory.maxModifier);
        logger.exitSection();
        
    }
}