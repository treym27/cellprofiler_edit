package pipelineauthor;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.ArrayList;

import fileutil.CSVUtil;


public class PipelineFactory{
    public static String constructPipeline(String configExcelFileName){
        String finalPipelineFileName = "";
        //read in config file
        CSVUtil c = new CSVUtil(configExcelFileName);
        System.out.println("Calculating averages");
        
        double typicalMin =  c.getColumnAverage("AreaShape_MinFeretDiameter");
        double typicalMax =  c.getColumnAverage("AreaShape_MaxFeretDiameter");
        
        System.out.println("(" + typicalMin + "," + typicalMax + ")");


        PipelineAuthor pa = new PipelineAuthor();
        pa.addReplacementRule(new Replacement("@minman", typicalMin + "," + typicalMax));
        pa.buildPipeline("");

        return finalPipelineFileName;
    }

}