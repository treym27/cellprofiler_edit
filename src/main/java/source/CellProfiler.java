package source;
import fileutil.CSVUtil;
import fileutil.CommandUtil;
import java.io.IOException;
import pipelineauthor.PipelineFactory;
import java.lang.Thread;

public class CellProfiler{
    private String inputImageFolder;
    private String outputMetaDataFolder;

    public CellProfiler(String inputImageFolder, String outputMetaDataFolder){
        this.inputImageFolder = inputImageFolder;
        this.outputMetaDataFolder = outputMetaDataFolder;
    }


    public boolean runConfigPipeline(){
        try {
            CommandUtil thisCommand = new CommandUtil(
                this.findExecutable(), 
                this.inputImageFolder, 
                this.outputMetaDataFolder, 
                "C:/Users/Steven/Desktop/Research/CellProfilerWrapper/Pipelines/Configuration/Config.cppipe"
                );
            System.out.println("Starting Config Process");
            new Thread(){
                public void run(){
                    try{
                    Process process = Runtime.getRuntime().exec(thisCommand.getCommand());
                    }
                    catch(IOException ioe){
                        
                    }
                    //process.waitFor();
                    System.out.println("Finished Config Process");
                }
            }.run();
            return false;
        
        }
        catch(Exception e) {
            System.out.println(e);
            return false;
        }
      

    }

    public void runCountingPipeline(){
        //create the new countling pipeline
        String configCSV = this.outputMetaDataFolder + "/MacroCells.csv";
        String finalPipeline = PipelineFactory.constructPipeline(configCSV, this.outputMetaDataFolder);
        try{
            System.out.println(finalPipeline);
            CommandUtil thisCommand = new CommandUtil(
                this.findExecutable(), 
                this.inputImageFolder, 
                this.outputMetaDataFolder, 
                finalPipeline
                );
            System.out.println("Starting Counting Process");
            Process process = Runtime.getRuntime().exec(thisCommand.getCommand());
            process.waitFor();
            System.out.println("Finished Counting Process");
        
        }
        catch(Exception e){

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