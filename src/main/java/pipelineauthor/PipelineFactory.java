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
        //System.out.println("configExcelFileName: " + configExcelFileName);
        File f  = new File(configExcelFileName);
        //System.out.println(f.exists() ? "File exists" : "File doesn't exist");
        CSVUtil c = new CSVUtil(configExcelFileName);
        System.out.println("Calculating averages");
        
        double typicalMin =  c.getColumnAverage("AreaShape_MinFeretDiameter");
        double typicalMax =  c.getColumnAverage("AreaShape_MaxFeretDiameter");
        System.out.println("\tActual:   (" + typicalMin + "," + typicalMax + ")");
        typicalMin *= 0.8;
        typicalMax *= 1.2;
        System.out.println("\tAdjusted: (" + typicalMin + "," + typicalMax + ")");
        int min = (int)typicalMin;
        int max = (int)typicalMax;
        PipelineAuthor pa = new PipelineAuthor();
        pa.addReplacementRule(new Replacement("@minmax", min + "," + max));

        String output = pa.buildPipeline(outputDirectory);
        return output;
    }

}