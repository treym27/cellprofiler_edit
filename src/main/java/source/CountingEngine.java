package source;

import fileutil.CSVUtil;
import fileutil.CommandUtil;
import java.io.IOException;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
//this class is used to handle different stacks, given a parent directory

public class CountingEngine{
    ArrayList<String> dirs;
    public CountingEngine(String sourceDirectory){
       
        this.dirs = new ArrayList<String>();


        //eventually, replace this with a function to get all subfolders
        dirs.add(sourceDirectory);

        for(String s : this.dirs)
            this.analyzeStack(s);

    }


    public void analyzeStack(String sourceDirectory){
        CellProfiler cp = new CellProfiler(
            sourceDirectory
        );
        if( cp.runConfigPipeline()){

            String s = cp.runCountingPipeline();
            System.out.println("Final results from " + s);
        }
    }
}