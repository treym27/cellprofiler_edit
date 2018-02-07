package source;
import fileutil.CSVUtil;
import fileutil.CommandUtil;
import java.io.IOException;
import pipelineauthor.PipelineFactory;

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
            Process process = Runtime.getRuntime().exec(thisCommand.getCommand());
            while(process.isAlive()){
                //wait until its done
            }
            return true;
        
        }
        catch(IOException e) {
            System.out.println(e);
            return false;
        }
      

    }

    public void runCountingPipeline(){
        //create the new countling pipeline
        String configCSV = this.outputMetaDataFolder + "/MacroCells.csv";
        System.out.println(PipelineFactory.constructPipeline(configCSV, this.outputMetaDataFolder));

    }


    public String findExecutable(){
        return "C:/Program Files (x86)/CellProfiler/CellProfiler.exe";
    }
}