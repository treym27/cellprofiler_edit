package pipelineauthor;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.ArrayList;

import fileutil.CSVUtil;


public class PipelineFactory{
    public static String constructPipeline(String configExcelFileName, String outputDirectory){
        //read in config file
        System.out.println("configExcelFileName: " + configExcelFileName);
        File f  = new File(configExcelFileName);
        System.out.println(f.exists() ? "File exists" : "File doesn't exist");
        CSVUtil c = new CSVUtil(configExcelFileName);
        System.out.println("Calculating averages");
        
        double typicalMin =  0.8 * c.getColumnAverage("AreaShape_MinFeretDiameter");
        double typicalMax =  1.2 * c.getColumnAverage("AreaShape_MaxFeretDiameter");
        
        System.out.println("(" + typicalMin + "," + typicalMax + ")");


        PipelineAuthor pa = new PipelineAuthor();
        pa.addReplacementRule(new Replacement("@minman", typicalMin + "," + typicalMax));
        String output = pa.buildPipeline(outputDirectory);
        System.out.println("Output Final Pipeline: " + output);
        return output;
    }

}