package fileutil;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;

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
    //note these numbers are 1 indexed to conform with 
    //excel numbering scheme
    public double getValue(int row, int column){
        row--;
        column--;
        int i = -1;
        for(CSVRecord cr : this.records){
            i++;
            String s = cr.get(column);
            System.out.println(s);
            if(i == row && StringUtils.isNumeric(s)){
                
                return Double.parseDouble(s);
            }
        }

        return -1;
    }
}