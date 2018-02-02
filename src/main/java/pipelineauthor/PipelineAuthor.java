package pipelineauthor;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.ArrayList;

public class PipelineAuthor{
    private ArrayList<String> orig;
    private ArrayList<Replacement> replacements;

    public PipelineAuthor(){
        Scanner s = null;
        this.orig = new ArrayList<String>();
        this.replacements = new ArrayList<Replacement>();
        try{
            s = new Scanner(new File("src/resources/Template.cppipe"));
            while (s.hasNext()){
                orig.add(s.nextLine());
            }
            s.close();
        }
        catch(FileNotFoundException e){
            
        }
    }

    public void addReplacementRule(Replacement r){
        this.replacements.add(r);
    }


    public String executeReplacements(String original){

        for(Replacement r : this.replacements){
            original = original.replace("@" + r.templateObjectName, r.templateObjectValue);
        }

        return original;
    }

    public String buildPipeline(String outputDirectory){

        return buildPipeline(outputDirectory, "output.cppipe");
    }

    public String buildPipeline(String outputDirectory, String outputPipeline){

        ArrayList<String> newest = new ArrayList<String>();

        for(String str : orig){
            newest.add(executeReplacements(str));
        }

        String pipelineFileName = outputDirectory + outputPipeline;
        try{
            FileWriter writer = new FileWriter(pipelineFileName); 
            for(String str: newest) {
              writer.write(str + "\n");
            }
            writer.close();

        }
        catch(IOException ioe){
            return "";
        }
        return pipelineFileName;

    }
}