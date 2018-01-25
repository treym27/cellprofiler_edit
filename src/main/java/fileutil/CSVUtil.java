package fileutil;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CSVUtil{
    private String fileName; 
    private Iterable<CSVRecord> records;
    public CSVUtil(String fileName){
        this.fileName = fileName;
        loadCSV();

    }


    public boolean loadCSV(){
        return loadCSV(this.fileName);
    }


    public boolean loadCSV(String fileName){
        try(
            Reader r = Files.newBufferedReader(Paths.get(fileName));
            CSVParser c = new CSVParser(r, CSVFormat.DEFAULT);
        ){
            this.records = c.getRecords();
            if(this.records != null)
                return true;
        }
        catch(IOException ioe){
            System.out.println(ioe);
        }
        return false;
    }



    public int retOne(){
        return 1;
    }
}