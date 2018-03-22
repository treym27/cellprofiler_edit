package pipelineauthor;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.ArrayList;

import fileutil.CSVUtil;
import mylogging.UseLogger;
import mylogging.MyLogger;

public class PipelineFactory{

    public static double minModifier = 1;
    public static double maxModifier = 1;
    public static String constructPipeline(String configExcelFileName, String outputDirectory){
        UseLogger logger = new UseLogger();
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
        logger.log("Constructing final pipeline.");
        logger.enterSection();
            //read in config file
            //System.out.println("configExcelFileName: " + configExcelFileName);
            File f  = new File(configExcelFileName);
            //System.out.println(f.exists() ? "File exists" : "File doesn't exist");
            if(!f.exists())
                logger.log("ERROR: config output files not found.");
            CSVUtil c = new CSVUtil(configExcelFileName);
            logger.log(" Calculating averages");
            logger.enterSection();
                double typicalMin =  c.getColumnAverage("AreaShape_MinFeretDiameter");
                double typicalMax =  c.getColumnAverage("AreaShape_MaxFeretDiameter");

                logger.log("Actual:   (" + typicalMin + "," + typicalMax + ")");
                typicalMin *= PipelineFactory.minModifier;
                typicalMax *= PipelineFactory.maxModifier;
                logger.log("Adjusted: (" + typicalMin + "," + typicalMax + ")");
                int min = (int)typicalMin;
                int max = (int)typicalMax;

                PipelineAuthor pa = new PipelineAuthor();
                pa.addReplacementRule(new Replacement("@minmax", min + "," + max));
            logger.exitSection();
            String output = pa.buildPipeline(outputDirectory);

        logger.exitSection();
        logger.log("Final pipeline construction completed.");
        return output;
        
    }



}