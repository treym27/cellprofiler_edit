package source;
import fileutil.CSVUtil;
import fileutil.CommandUtil;
import java.io.IOException;
import pipelineauthor.PipelineFactory;
import java.lang.Thread;
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
        System.out.println("Starting Config Process");
        try {
            CommandUtil thisCommand = new CommandUtil(
                this.findExecutable(), 
                this.inputImageFolder, 
                this.outputMetaDataFolder + "/config", 
                "C:/Users/Steven/Desktop/Research/CellProfilerWrapper/Pipelines/Configuration/Config.cppipe"
                );
            
            String command = thisCommand.getCommand();
            
            Process process = Runtime.getRuntime().exec(command);
            consumeBuffer(process);
            process.waitFor();
            System.out.println("Finished Config Process:Success");
            thisCommand.deleteTemp(this.inputImageFolder + "Temp");
            thisCommand = null;
    
            return true;
        
        }
        catch(Exception e) {
            System.out.println("Finished Config Process: Failed");
            return false;
        }
      

    }

    //returns filtered csv
    public String runCountingPipeline(){
        //create the new countling pipeline
        System.out.println("Starting Counting Process");
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
            System.out.println(this.outputMetaDataFolder + "/FilterObjects.csv");

            File filteredCSV = new File(this.outputMetaDataFolder + "/FilterObjects.csv");

            System.out.println("Finished Counting Process");
            thisCommand.deleteTemp(this.inputImageFolder + "Temp");
            if(filteredCSV.exists())
                return this.outputMetaDataFolder + "/FilterObjects.csv";

        }
        catch(Exception e){
            return "WRONG";
        }
        
        return "";
        

    }

    public void consumeBuffer(Process process){
        try{

            new Thread() {
                public void run() {
                    InputStream stream = process.getInputStream();
                    try { while (stream.read() >= 0) {} } catch (Exception e) {}
                }
            }.start();

            new Thread() {
                public void run() {
                    InputStream stream = process.getErrorStream();
                    try { while (stream.read() >= 0) {} } catch (Exception e) {}
                }
            }.start();
        } catch(Exception e) {
            System.out.println(e);
        }
    }


    public String findExecutable(){
        return "C:/Program Files (x86)/CellProfiler/CellProfiler.exe";
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