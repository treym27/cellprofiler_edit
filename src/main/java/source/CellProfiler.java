package source;
import fileutil.CSVUtil;
import fileutil.CommandUtil;
import mylogging.UseLogger;
import mylogging.MyLogger;
import java.io.IOException;
import pipelineauthor.PipelineFactory;
import java.lang.Thread;
import java.lang.ProcessBuilder;
import java.io.InputStream;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.ArrayList;

public class CellProfiler{
    private String inputImageFolder;
    private String outputMetaDataFolder;

    public CellProfiler(String inputImageFolder){
        this.inputImageFolder = inputImageFolder;
        this.outputMetaDataFolder = inputImageFolder + "/" + getDate();
    }

    public String getDate(){
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
      return dateString;

    }


    public boolean runConfigPipeline(){
        UseLogger logger = new UseLogger();
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
        try {
            logger.log("Starting Config process.");
            logger.enterSection();
            CommandUtil thisCommand = new CommandUtil(
                this.findExecutable(), 
                this.inputImageFolder, 
                this.outputMetaDataFolder + "/config", 
                "Pipelines/Configuration/Config.cppipe"
                );
            
            
            Process process = Runtime.getRuntime().exec(thisCommand.getCommand());
            consumeBuffer(process);
            process.waitFor();

            
            thisCommand.deleteTemp(this.inputImageFolder + "Temp");

            thisCommand = null;
            logger.exitSection();
            logger.log("Finished Config Process: Success");
            return true;
        
        }
        catch(Exception e) {
            System.out.println("Finished Config Process: Failed");
            e.printStackTrace();
            return false;
        }
      

    }

    //returns filtered csv
    public String runCountingPipeline(){
        //create the new countling pipeline
        UseLogger logger = new UseLogger();
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
        logger.log("Starting Counting Process");
        logger.enterSection();
        String configCSV = this.outputMetaDataFolder + "/config/MacroCells.csv";
        String finalPipeline = PipelineFactory.constructPipeline(configCSV, this.outputMetaDataFolder);
        
        try{
            CommandUtil thisCommand = new CommandUtil(
                this.findExecutable(), 
                this.inputImageFolder, 
                this.outputMetaDataFolder, 
                finalPipeline
                );
            
            Process process = Runtime.getRuntime().exec(thisCommand.getCommand());
            consumeBuffer(process);
            process.waitFor();
            

            File filteredCSV = new File(this.outputMetaDataFolder + "/FilterObjects.csv");
            
            thisCommand.deleteTemp(this.inputImageFolder + "Temp");
            if(filteredCSV.exists()){
                logger.exitSection();
                logger.log("Finished Counting Process: Success");
                return this.outputMetaDataFolder + "/FilterObjects.csv";
            }
            else{
                logger.exitSection();
                logger.log("Finished Counting Process: Failed to create final count csv");
            }

        }
        catch(Exception e){
            logger.exitSection();
            logger.log("Finished Counting Process: Failed exception occured.");
            return "WRONG";
        }
        
        return "";
        

    }

    public void consumeBuffer(Process process){
        try{

            new Thread() {
                public void run() {
                    InputStream stream = process.getInputStream();
                    try { 
                        char c = '0';
                        int i = -1;
                        while ((i = (int)stream.read()) != -1) {
                            System.out.print((char)i);
                        } 
                    } catch (Exception e) {}
                }
            }.start();

            new Thread() {
                public void run() {
                    char c = '0';
                    int i = -1;
                    InputStream stream = process.getErrorStream();
                    try {
                        while ((i = (int)stream.read()) != -1) {
                            System.out.print((char)i);
                        } 
                    } catch (Exception e) {}
                }
            }.start();
        } catch(Exception e) {
            System.out.println(e);
        }
    }


    public String findExecutable(){
        //due to a new windows update in managing java processes
        //Cellprofiler.exe cant be installed in C:/Program Files
        //I would suggest installing it on desktop
        //change the below to reflect new destination
        return "C:/Users/User/Desktop/executable/CellProfiler.exe";
  }

    // | / - \ | / - \
    // 
    private static void loadingPrintout(String header, int state){
        System.out.print("\033[H\033[2J");//clear console
        System.out.print(header);

        switch(state){
            case 0:
                System.out.println("|");
                break;
            case 1:
                System.out.println("/");
                break;
            case 2:
                System.out.println("-");
                break;
            case 3:
                System.out.println("\\");
                break;
        }

    }
}